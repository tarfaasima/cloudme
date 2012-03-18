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

import org.apache.commons.beanutils.BeanUtils;
import org.cloudme.wrestle.annotation.Mapping;
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

    public boolean matches(String path) {
        return path.startsWith(urlMapping);
    }

    @SneakyThrows
    public Object execute(String path, HttpServletRequest req, HttpServletResponse resp) {
        val pathArgs = path.substring(urlMapping.length() + 1).split("/");
        return method.invoke(actionHandler, convert(pathArgs, req));
    }

    @SneakyThrows
    private Object[] convert(String[] pathArgs, HttpServletRequest req) {
        val parameterTypes = method.getParameterTypes();
        val parameterAnnotations = method.getParameterAnnotations();
        val args = new Object[parameterTypes.length];
        int pathArgsIndex = 0;
        for (int i = 0; i < args.length; i++) {
            String mapping = getMapping(parameterAnnotations[i]);
            if (mapping != null) {
                args[i] = mapParameter(mapping, parameterTypes[i], req);
            }
            else {
                if (pathArgsIndex < pathArgs.length) {
                    args[i] = converters.get(parameterTypes[i]).convert(pathArgs[pathArgsIndex++]);
                }
            }
        }
        return args;
    }

    private Object mapParameter(String mapping, Class<?> parameterType, HttpServletRequest req)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {
        mapping += ".";
        @SuppressWarnings( "unchecked" )
        Map<String, String[]> parameterMap = req == null ? Collections.EMPTY_MAP : req.getParameterMap();
        Object arg = parameterType.newInstance();
        for (Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(mapping)) {
                String name = key.substring(mapping.length());
                BeanUtils.setProperty(arg, name, entry.getValue()[0]);
            }
        }
        return arg;
    }

    private String getMapping(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Mapping) {
                return ((Mapping) annotation).value();
            }
        }
        return null;
    }
}
