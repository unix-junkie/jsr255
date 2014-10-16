/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>The textual description of an MBean or part of an MBean.  This
 * description is intended to be displayed to users to help them
 * understand what the MBean does.  Ultimately it will be the value of
 * the <code>getDescription()</code> method of an {@link MBeanInfo}, {@link MBeanAttributeInfo}, or similar.</p>
 *
 * <p>This annotation applies to Standard MBean interfaces and to
 * MXBean interfaces, as well as to MBean classes defined using the
 * {@link MBean &#64;MBean} or {@link MXBean &#64;MXBean} annotations.  For
 * example, a Standard MBean might be defined like this:</p>
 *
 * <pre>
 * <b><code>&#64;Description</code></b>("Application configuration")
 * public interface ConfigurationMBean {
 *     <b><code>&#64;Description</code></b>("Cache size in bytes")
 *     public int getCacheSize();
 *     public void setCacheSize(int size);
 *
 *     <b><code>&#64;Description</code></b>("Last time the configuration was changed, " +
 *                  "in milliseconds since 1 Jan 1970")
 *     public long getLastChangedTime();
 *
 *     <b><code>&#64;Description</code></b>("Save the configuration to a file")
 *     public void save(
 *         <b><code>&#64;Description</code></b>("Optional name of the file, or null for the default name")
 *         String fileName);
 * }
 * </pre>
 *
 * <p>The <code>MBeanInfo</code> for this MBean will have a {@link MBeanInfo#getDescription() getDescription()} that is <code>"Application configuration"</code>.  It will contain an <code>MBeanAttributeInfo</code> for the <code>CacheSize</code> attribute that is
 * defined by the methods <code>getCacheSize</code> and <code>setCacheSize</code>, and another <code>MBeanAttributeInfo</code> for <code>LastChangedTime</code>.  The {@link MBeanFeatureInfo#getDescription() getDescription()} for <code>CacheSize</code> will be <code>"Cache size
 * in bytes"</code>.  Notice that there is no need to add a
 * <code>&#64;Description</code> to both <code>getCacheSize</code> and <code>setCacheSize</code> - either alone will do.  But if you do add a
 * <code>&#64;Description</code> to both, it must be the same.</p>
 *
 * <p>The <code>MBeanInfo</code> will also contain an {@link MBeanOperationInfo} where {@link MBeanFeatureInfo#getDescription() getDescription()} is <code>"Save the configuration to a file"</code>.  This <code>MBeanOperationInfo</code> will contain an {@link MBeanParameterInfo}
 * where {@link MBeanFeatureInfo#getDescription() getDescription()}
 * is <code>"Optional name of the file, or null for the default
 * name"</code>.</p>
 *
 * <p>The <code>&#64;Description</code> annotation can also be applied to the
 * public constructors of the implementation class.  Continuing the
 * above example, the <code>Configuration</code> class implementing <code>ConfigurationMBean</code> might look like this:</p>
 *
 * <pre>
 * public class Configuration implements ConfigurationMBean {
 *     <b><code>&#64;Description</code></b>("A Configuration MBean with the default file name")
 *     public Configuration() {
 *         this(DEFAULT_FILE_NAME);
 *     }
 *
 *     <b><code>&#64;Description</code></b>("A Configuration MBean with a specified file name")
 *     public Configuration(
 *         <b><code>&#64;Description</code></b>("Name of the file the configuration is stored in")
 *         String fileName) {...}
 *     ...
 * }
 * </pre>
 *
 * <p>The <code>&#64;Description</code> annotation also works in MBeans that
 * are defined using the <code>&#64;MBean</code> or <code>&#64;MXBean</code> annotation
 * on classes.  Here is an alternative implementation of <code>Configuration</code> that does not use an <code>ConfigurationMBean</code>
 * interface.</p>
 *
 * <pre>
 * <b><code>&#64;MBean</code></b>
 * <b><code>&#64;Description</code></b>("Application configuration")
 * public class Configuration {
 *     <b><code>&#64;Description</code></b>("A Configuration MBean with the default file name")
 *     public Configuration() {
 *         this(DEFAULT_FILE_NAME);
 *     }
 *
 *     <b><code>&#64;Description</code></b>("A Configuration MBean with a specified file name")
 *     public Configuration(
 *         <b><code>&#64;Description</code></b>("Name of the file the configuration is stored in")
 *         String fileName) {...}
 *
 *     <b><code>&#64;ManagedAttribute</code></b>
 *     <b><code>&#64;Description</code></b>("Cache size in bytes")
 *     public int getCacheSize() {...}
 *     <b><code>&#64;ManagedAttribute</code></b>
 *     public void setCacheSize(int size) {...}
 *
 *     <b><code>&#64;ManagedOperation</code></b>
 *     <b><code>&#64;Description</code></b>("Last time the configuration was changed, " +
 *                  "in milliseconds since 1 Jan 1970")
 *     public long getLastChangedTime() {...}
 *
 *     <b><code>&#64;ManagedOperation</code></b>
 *     <b><code>&#64;Description</code></b>("Save the configuration to a file")
 *     public void save(
 *         <b><code>&#64;Description</code></b>("Optional name of the file, or null for the default name")
 *         String fileName) {...}
 *     ...
 * }
 * </pre>
 */
@Documented
@Retention(value = RUNTIME)
@Target(value = {CONSTRUCTOR, FIELD, METHOD, PARAMETER, TYPE})
public @interface Description {
	String value();

	@DescriptorKey(value = "descriptionResourceBundleBaseName"/*, omitIfDefault = true */)
	String bundleBaseName() default "";

	@DescriptorKey(value = "descriptionResourceKey"/*, omitIfDefault = true*/)
	String key() default "";
}
