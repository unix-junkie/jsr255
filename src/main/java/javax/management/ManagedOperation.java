/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.management.Impact.UNKNOWN;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *<p>Indicates that a method in an MBean class defines an MBean operation.
 * This annotation can be applied to:</p>
 *
 * <ul>
 * <li>A public method of a public class
 * that is itself annotated with an {@link MBean &#64;MBean} or
 * {@link MXBean &#64;MXBean} annotation, or inherits such an annotation from
 * a superclass.</li>
 * <li>A method of an MBean or MXBean interface.
 * </ul>
 *
 * <p>Every method in an MBean or MXBean interface defines an MBean
 * operation even without this annotation, but the annotation allows
 * you to specify the impact of the operation:</p>
 *
 * <pre>
 * public interface ConfigurationMBean {
 *     <code>&#64;ManagedOperation</code>}(impact = {@link Impact#ACTION})
 *     public void save();
 *     ...
 * }
 * </pre>
 */
@Retention(value = RUNTIME)
@Target(value = METHOD)
@Documented
public @interface ManagedOperation {
	/**
	 * The impact of this operation, as shown by
	 * {@link MBeanOperationInfo#getImpact()}.
	 */
	Impact impact() default UNKNOWN;
}
