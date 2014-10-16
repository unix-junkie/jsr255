/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *<p>Indicates that the annotated class is a Standard MBean.  A Standard
 * MBean class can be defined as in this example:</p>
 *
 * <pre> <code>&#64;MBean</code>
 * public class Configuration {
 *     {@link ManagedAttribute &#64;ManagedAttribute}
 *     public int getCacheSize() {...}
 *     <code>&#64;ManagedAttribute</code>
 *     public void setCacheSize(int size);
 *
 *     <code>&#64;ManagedAttribute</code>
 *     public long getLastChangedTime();
 *
 *     {@link ManagedOperation &#64;ManagedOperation}
 *     public void save();
 * }
 * </pre>
 *
 * <p>The class must be public.  Public methods within the class can be
 * annotated with <code>&#64;ManagedOperation</code> to indicate that they are
 * MBean operations.  Public getter and setter methods within the class
 * can be annotated with <code>&#64;ManagedAttribute</code> to indicate that they define
 * MBean attributes.</p>
 *
 * <p>If the MBean is to be an MXBean rather than a Standard MBean, then
 * the {@link MXBean &#64;MXBean} annotation must be used instead of
 * <code>&#64;MBean</code>.</p>
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Inherited
public @interface MBean {
	// empty
}
