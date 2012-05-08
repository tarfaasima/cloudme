package org.cloudme.wrestle;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;

import org.apache.commons.beanutils.PropertyUtils;
import org.cloudme.wrestle.annotation.Param;
import org.cloudme.wrestle.convert.BooleanConverter;
import org.cloudme.wrestle.convert.Converter;
import org.cloudme.wrestle.convert.DateConverter;
import org.cloudme.wrestle.convert.DoubleConverter;
import org.cloudme.wrestle.convert.FloatConverter;
import org.cloudme.wrestle.convert.IntegerConverter;
import org.cloudme.wrestle.convert.LongConverter;
import org.cloudme.wrestle.convert.StringConverter;

@Data
@AllArgsConstructor
class RequestHandler {
    private ActionHandler actionHandler;
    private Method method;
    private String urlMapping;
    private static final Map<Class<?>, Converter<?>> converters = new HashMap<Class<?>, Converter<?>>();

    static {
        converters.put(int.class, new IntegerConverter());
        converters.put(long.class, new LongConverter());
        converters.put(boolean.class, new BooleanConverter());
        converters.put(float.class, new FloatConverter());
        converters.put(double.class, new DoubleConverter());
        converters.put(Integer.class, new IntegerConverter());
        converters.put(Long.class, new LongConverter());
        converters.put(Boolean.class, new BooleanConverter());
        converters.put(Float.class, new FloatConverter());
        converters.put(Double.class, new DoubleConverter());
        converters.put(Date.class, new DateConverter());
        converters.put(String.class, new StringConverter());
    }

    public boolean matches(HttpServletRequest req) {
        return req.getServletPath().startsWith(urlMapping);
    }

    @SneakyThrows
    public Object execute(HttpServletRequest req, HttpServletResponse resp) {
        return method.invoke(actionHandler, convertToParams(req));
    }

    private String[] asPathArgs(String path) {
        if (path.length() == urlMapping.length()) {
            return new String[0];
        }
        else {
            return path.substring(urlMapping.length() + 1).split("/");
        }
    }

    @SneakyThrows
    private Object[] convertToParams(HttpServletRequest req) {
        val pathArgs = asPathArgs(req.getServletPath());
        val paramTypes = method.getParameterTypes();
        val paramAnnotations = method.getParameterAnnotations();
        val args = new Object[paramTypes.length];
        int pathArgsIndex = 0;
        for (int i = 0; i < args.length; i++) {
            String name = getParamName(paramAnnotations[i]);
            if (name != null) {
                args[i] = mapParameter(name, paramTypes[i], req);
            }
            else {
                if (pathArgsIndex < pathArgs.length) {
                    args[i] = convert(paramTypes[i], pathArgs[pathArgsIndex++]);
                }
            }
        }
        return args;
    }

    private String getParamName(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Param) {
                Param param = (Param) annotation;
                return param.name();
            }
        }
        return null;
    }

    private Object convert(Class<?> type, String obj) {
        Converter<?> converter = converters.get(type);
        if (converter == null) {
            return null;
        }
        return converter.convert(obj);
    }

    @SuppressWarnings( "unchecked" )
    private Object mapParameter(String mapping, Class<?> parameterType, HttpServletRequest req)
            throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String parameter = getParameter(req, mapping);
        if (parameter != null) {
            return convert(parameterType, parameter);
        }
        mapping += ".";
        Map<String, String[]> parameterMap = req == null ? Collections.EMPTY_MAP : req.getParameterMap();
        Object arg = parameterType.newInstance();
        for (Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(mapping)) {
                String name = key.substring(mapping.length());
                Class<?> type = PropertyUtils.getPropertyType(arg, name);
                String value = entry.getValue()[0];
                PropertyUtils.setProperty(arg, name, convert(type, value));
            }
        }
        return arg;
    }

    private String getParameter(HttpServletRequest req, String name) {
        String parameter = req.getParameter(name);
        if (parameter == null) {
            String[] parameterValues = req.getParameterValues(name);
            if (parameterValues != null && parameterValues.length > 0) {
                parameter = parameterValues[0];
            }
        }
        return parameter;
    }
}
