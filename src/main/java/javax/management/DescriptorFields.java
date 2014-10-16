/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 *<p>Annotation that adds fields to a {@link Descriptor}.  This can be the
 * Descriptor for an MBean, or for an attribute, operation, or constructor
 * in an MBean, or for a parameter of an operation or constructor.</p>
 *
 * <p>Consider this Standard MBean interface, for example:</p>
 *
 * <pre>
 * public interface CacheControlMBean {
 *     <b>&#64;DescriptorFields("units=bytes")</b>
 *     public long getCacheSize();
 * }
 * </pre>
 *
 * <p>When a Standard MBean is made using this interface, the usual rules
 * mean that it will have an attribute called <code>CacheSize</code> of type
 * <code>long</code>.  The <code>DescriptorFields</code> annotation will ensure
 * that the {@link MBeanAttributeInfo} for this attribute will have a
 * <code>Descriptor</code> that has a field called <code>units</code> with
 * corresponding value <code>bytes</code>.</p>
 *
 * <p>Similarly, if the interface looks like this:</p>
 *
 * <pre>
 * public interface CacheControlMBean {
 *     <b>&#64;DescriptorFields({"units=bytes", "since=1.5"})</b>
 *     public long getCacheSize();
 * }
 * </pre>
 *
 * <p>then the resulting <code>Descriptor</code> will contain the following
 * fields:</p>
 *
 * <table border="2">
 * <tr><th>Name</th><th>Value</th></tr>
 * <tr><td>units</td><td>"bytes"</td></tr>
 * <tr><td>since</td><td>"1.5"</td></tr>
 * </table>
 *
 * <p>The <code>&#64;DescriptorFields</code> annotation can be applied to:</p>
 *
 * <ul>
 * <li>a Standard MBean or MXBean interface;
 * <li>a method in such an interface;
 * <li>a parameter of a method in a Standard MBean or MXBean interface
 * when that method is an operation (not a getter or setter for an attribute);
 * <li>a public constructor in the class that implements a Standard MBean
 * or MXBean;
 * <li>a parameter in such a constructor.
 * </ul>
 *
 * <p>Other uses of the annotation will either fail to compile or be
 * ignored.</p>
 *
 * <p>Interface annotations are checked only on the exact interface
 * that defines the management interface of a Standard MBean or an
 * MXBean, not on its parent interfaces.  Method annotations are
 * checked only in the most specific interface in which the method
 * appears; in other words, if a child interface overrides a method
 * from a parent interface, only <code>&#64;DescriptorFields</code> annotations in
 * the method in the child interface are considered.
 *
 * <p>The Descriptor fields contributed in this way must be consistent
 * with each other and with any fields contributed by {@link DescriptorKey &#64;DescriptorKey} annotations.  That is, two
 * different annotations, or two members of the same annotation, must
 * not define a different value for the same Descriptor field.  Fields
 * from annotations on a getter method must also be consistent with
 * fields from annotations on the corresponding setter method.</p>
 *
 * <p>The Descriptor resulting from these annotations will be merged
 * with any Descriptor fields provided by the implementation, such as
 * the <a href="Descriptor.html#immutableInfo"><code>immutableInfo</code></a> field for an MBean.  The fields from the annotations
 * must be consistent with these fields provided by the implementation.</p>
 *
 * <h4>&#64;DescriptorFields and &#64;DescriptorKey</h4>
 *
 * <p>The {@link DescriptorKey &#64;DescriptorKey} annotation provides
 * another way to use annotations to define Descriptor fields.
 * <code>&#64;DescriptorKey</code> requires more work but is also more
 * robust, because there is less risk of mistakes such as misspelling
 * the name of the field or giving an invalid value.
 * <code>&#64;DescriptorFields</code> is more convenient but includes
 * those risks.  <code>&#64;DescriptorFields</code> is more
 * appropriate for occasional use, but for a Descriptor field that you
 * add in many places, you should consider a purpose-built annotation
 * using <code>&#64;DescriptorKey</code>.
 *
 * @since 1.7
 */
@Documented
@Inherited
@Target(value = {CONSTRUCTOR, METHOD, PARAMETER, TYPE})
@Retention(value = RUNTIME)
public @interface DescriptorFields {
	String[] value();
}
