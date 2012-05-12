package org.cloudme.wrestle;

import java.lang.annotation.Annotation;
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

/**
 * Handles a request by delegating to an {@link ActionHandler}. This class maps
 * request parameters, URL segments etc. to method parameters befor calling the
 * action method of the {@link ActionHandler}.
 * 
 * @author Moritz Petersen
 */
@Data
@AllArgsConstructor
class RequestHandler {
    /**
     * The {@link ActionHandler} of this class. Requests will be delegated to
     * it.
     */
    private ActionHandler actionHandler;
    /**
     * The {@link Method} of the {@link #actionHandler} that is called.
     */
    private Method method;
    /**
     * The URL path segment mapped to this handler.
     */
    private String urlMapping;
    /**
     * Converters, used for parameter type conversion.
     */
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

    /**
     * Tests if this class matches the path of the given
     * {@link HttpServletRequest}.
     * 
     * @param req
     *            The request that is tested if it matches.
     * @return <code>true</code> if it matches, otherwise <code>false</code>.
     */
    boolean matches(HttpServletRequest req) {
        return getPath(req).startsWith(urlMapping);
    }

    /**
     * Returns the URL path of the request.
     * 
     * @param req
     *            The request.
     * @return The URL path.
     */
    private String getPath(HttpServletRequest req) {
        return req.getRequestURI();
    }

    /**
     * Executes the action {@link #method} of the {@link #actionHandler}. Before
     * it is called, the request parameters and URL segments are converted to
     * method parameters.
     * 
     * @param req
     *            The request.
     * @param resp
     *            The response.
     * @return The return value of the {@link #method}.
     */
    @SneakyThrows
    Object execute(HttpServletRequest req, HttpServletResponse resp) {
        return method.invoke(actionHandler, convertToParams(req));
    }

    /**
     * Converts the URL path to a {@link String} array, by splitting "/". If the
     * path is shorter than the mapping, an zero-length array is returned.
     * 
     * @param path
     *            The current path.
     * @return A {@link String} array. Never <code>null</code>.
     */
    private String[] asPathArgs(String path) {
        if (path.length() <= urlMapping.length()) {
            return new String[0];
        }
        else {
            return path.substring(urlMapping.length() + 1).split("/");
        }
    }

    /**
     * Creates typesafe method parameters from the given
     * {@link HttpServletRequest} with the following rule:
     * <ol>
     * <li>If the method parameter is annotated with the {@link Param}
     * annotation, then return the request parameter of the given name (see
     * {@link #mapParameter(String, Class, HttpServletRequest)}).
     * <li>Otherwise convert the path segment to the method parameter type (see
     * {@link #convert(Class, String)}).
     * <li>Otherwise the method parameter is <code>null</code>.
     * </ol>
     * 
     * @param req
     *            The request.
     * @return The array of method parameters.
     */
    @SneakyThrows
    private Object[] convertToParams(HttpServletRequest req) {
        val paramTypes = method.getParameterTypes();
        val paramAnnotations = method.getParameterAnnotations();
        val params = new Object[paramTypes.length];
        val pathArgs = asPathArgs(getPath(req));
        for (int i = 0, pathArgsIndex = 0; i < params.length; i++) {
            String name = getParamName(paramAnnotations[i]);
            if (name != null) {
                params[i] = mapParameter(name, paramTypes[i], req);
            }
            else {
                if (pathArgsIndex < pathArgs.length) {
                    params[i] = convert(paramTypes[i], pathArgs[pathArgsIndex++]);
                }
            }
        }
        return params;
    }

    /**
     * Returns the name of the parameter using the {@link Param} annotation.
     * 
     * @param annotations
     *            All parameter annotation.
     * @return The name or <code>null</code> if the {@link Param} annotation is
     *         not set.
     */
    private String getParamName(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Param) {
                Param param = (Param) annotation;
                return param.name();
            }
        }
        return null;
    }

    /**
     * Converts the {@link String} to the given type.
     * 
     * @param type
     *            The target type.
     * @param str
     *            The {@link String} value.
     * @return The converted value.
     */
    private Object convert(Class<?> type, String str) {
        Converter<?> converter = converters.get(type);
        if (converter == null) {
            return null;
        }
        return converter.convert(str);
    }

    /**
     * Maps the request parameter of the given <code>paramName</code> to the
     * given <code>paramType</code>, using the rule:
     * <ol>
     * <li>Find a request parameter with the exact name. Convert this parameter.
     * <li>If no request parameter with the name exists, find request
     * parameters, that start with <code>paramName.</code> and map those
     * parameters to the type properties. Example:
     * <ul>
     * <li><code>paramName</code> is "person".
     * <li><code>paramType</code> is my.example.Person.
     * <li>Then a new instance of <code>my.example.Person person</code> is
     * created.
     * <li>A request parameter <code>person.name</code> is converted and mapped
     * to <code>person.setName()</code>.
     * </ul>
     * </ol>
     * 
     * @param paramName
     *            The request parameter name.
     * @param paramType
     *            The method parameter type.
     * @param req
     *            The {@link HttpServletRequest}.
     * @return The converted, mapped value.
     */
    @SuppressWarnings( "unchecked" )
    @SneakyThrows
    private Object mapParameter(String paramName, Class<?> paramType, HttpServletRequest req) {
        String parameter = getParameter(req, paramName);
        if (parameter != null) {
            return convert(paramType, parameter);
        }
        paramName += ".";
        Map<String, String[]> parameterMap = req == null ? Collections.EMPTY_MAP : req.getParameterMap();
        Object arg = paramType.newInstance();
        for (Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(paramName)) {
                String name = key.substring(paramName.length());
                Class<?> type = PropertyUtils.getPropertyType(arg, name);
                String value = entry.getValue()[0];
                PropertyUtils.setProperty(arg, name, convert(type, value));
            }
        }
        return arg;
    }

    /**
     * Returns a parameter with the exact name.
     * 
     * @param req
     *            The {@link HttpServletRequest}.
     * @param name
     *            The name of the request parameter.
     * @return The parameter value.
     */
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
