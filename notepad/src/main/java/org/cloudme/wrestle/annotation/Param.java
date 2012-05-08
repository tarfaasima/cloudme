package org.cloudme.wrestle.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines properties for method parameters.
 * 
 * @author Moritz Petersen
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.PARAMETER )
public @interface Param {
    /**
     * The name of the parameter in the {@link HttpServletRequest}. Required for
     * {@link Post} methods or to map multiple parameters to the properties of
     * an object
     * <p>
     * Example: Map a "foo.name" to an object attribute:
     * 
     * <pre>
     * <code>
     *  public class Task {
     *      private String name;
     *      
     *      ... getter and setter
     *  }
     *  
     *  public class MyActionHandler extends ActionHandler {
     *      &#64;Get
     *      public void showTasks(&#64;Param(name="foo") Task task) {
     *          String name = task.getName();
     *          ...
     *      }
     *  }
     * </code>
     * </pre>
     * 
     * @return the name of the parameter.
     */
    String name();
}
