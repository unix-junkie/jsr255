/*-
 * $Id$
 */
package javax.management.openmbean;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

import javax.management.NotCompliantMBeanException;

/**
 * <p>Specifies the MXBean mapping to be used for this Java type.</p>
 *
 * @see MXBeanMapping
 */
@Retention(value = RUNTIME)
@Target(value = TYPE)
@Documented
@Inherited
public @interface MXBeanMappingClass {
	/**
	 * <p>The {@link MXBeanMapping} class to be used to map the
	 * annotated type.  This class must have a public constructor with
	 * a single argument of type {@link Type}.  The
	 * constructor will be called with the annotated type as an
	 * argument.  See the <code>MXBeanMapping</code> documentation
	 * for an example.</p>
	 *
	 * <p>If the <code>MXBeanMapping</code> cannot in fact handle that
	 * type, the constructor should throw an {@link OpenDataException}.  If the constructor throws this or any other
	 * exception then an MXBean in which the annotated type appears is
	 * invalid, and registering it in the MBean Server will produce a
	 * {@link NotCompliantMBeanException}.
	 *</p>
	 */
	Class<? extends MXBeanMapping> value();
}
