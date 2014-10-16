/*-
 * $Id$
 */
package javax.management.openmbean;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>Specifies the MXBean mapping factory to be used for Java types
 * in an MXBean interface, or in all MXBean interfaces in a package.</p>
 *
 * <p>Applying a mapping factory to all Java types in an MXBean interface
 * looks like this:</p>
 *
 * <pre> &#64;MXBeanMappingFactoryClass(MyLinkedListMappingFactory.class)
 * public interface SomethingMXBean {
 *     public MyLinkedList getSomething();
 * }
 * </pre>
 *
 * <p>Applying a mapping factory to all Java types in all MXBean interfaces
 * in a package, say <code>com.example.mxbeans</code>, looks like this.  In the
 * package source directory, create a file called <code>package-info.java</code>
 * with these contents:</p>
 *
 * <pre> &#64;MXBeanMappingFactoryClass(MyLinkedListMappingFactory.class)
 * package com.example.mxbeans;
 * </pre>
 *
 * @see MXBeanMappingFactory
 */
@Retention(value = RUNTIME)
@Target(value = {TYPE, PACKAGE})
@Documented
@Inherited
public @interface MXBeanMappingFactoryClass {
	/**
	 * <p>The {@link MXBeanMappingFactory} class to be used to map
	 * types in the annotated interface or package.  This class must
	 * have a public constructor with no arguments.  See the <code>MXBeanMappingFactory</code> documentation for an example.</p>
	 */
	Class<? extends MXBeanMappingFactory> value();
}
