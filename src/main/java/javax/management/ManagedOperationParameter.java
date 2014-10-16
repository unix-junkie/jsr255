/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>The name of a managed operation parameter. It will be the value of the
 * <code>getName()</code> method of an {@link MBeanParameterInfo}.</p>
 *
 * <p>Here's the usage example:</p>
 *
 * <pre>
 * public interface ConfigurationMBean {
 *     void save(
 *         String fileName);
 *     ...
 * }
 *
 * <b>{@link MBean &#64;MBean}</b>
 * <b>{@link Description &#64;Description}</b>("Application configuration")
 * public class Configuration implements ConfigurationMBean {
 *     <code>&#64;Override</code>
 *     <b>{@link ManagedOperation &#64;ManagedOperation}</b>(impact = {@link Impact#ACTION})
 *     <b>{@link Description &#64;Description}</b>("Save the configuration to a file")
 *     public void save(
 *         <b><code>&#64;ManagedOperationParameter</code></b>("fileName")
 *         <b>{@link Description &#64;Description}</b>("Optional name of the file, or null for the default name")
 *         String fileName) {...}
 *     ...
 * }
 * </pre>
 *
 * <p>This annotation is not present in the original <a href =
 * "https://jcp.org/en/jsr/detail?id=255">JSR 255</a> <a href =
 * "https://jcp.org/aboutJava/communityprocess/edr/jsr255/">Early Draft Review</a>.
 * </p>
 *
 * @author <a href = "mailto:andrewbass@gmail.com">Andrew ``Bass'' Shcheglov</a>
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface ManagedOperationParameter {
	/**
	 * <p>The name of a managed operation parameter.</p>
	 *
	 * @return the name of a managed operation parameter.
	 */
	String value();
}
