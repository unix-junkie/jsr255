/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>Indicates that a method in an MBean class defines an MBean attribute.
 * This annotation must be applied to a public method of a public class
 * that is itself annotated with an {@link MBean &#64;MBean} or
 * {@link MXBean &#64;MXBean} annotation, or inherits such an annotation from
 * a superclass.</p>
 *
 * <p>The annotated method must be a getter or setter.  In other words,
 * it must look like one of the following...</p>
 *
 * <pre>
 * <i>T</i> get<i>Foo</i>()
 * void set<i>Foo</i>(<i>T</i> param)
 * </pre>
 *
 * <p>...where <i><code>T</code></i> is any type and <i><code>Foo</code></i> is the
 * name of the attribute.  For any attribute <i><code>Foo</code></i>, if only
 * a <code>get</code><i><code>Foo</code></i> method has a <code>ManagedAttribute</code>
 * annotation, then <i><code>Foo</code></i> is a read-only attribute.  If only
 * a <code>set</code><i><code>Foo</code></i> method has a <code>ManagedAttribute</code>
 * annotation, then <i><code>Foo</code></i> is a write-only attribute.  If
 * both <code>get</code><i><code>Foo</code></i> and <code>set</code><i><code>Foo</code></i>
 * methods have the annotation, then <i><code>Foo</code></i> is a read-write
 * attribute.  In this last case, the type <i><code>T</code></i> must be the
 * same in both methods.</p>
 */
@Retention(value = RUNTIME)
@Target(value = METHOD)
@Documented
public @interface ManagedAttribute {
	// empty
}
