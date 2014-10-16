/*-
 * $Id$
 */
package javax.management.openmbean;

import java.lang.reflect.Type;

import javax.management.StandardMBean;

/**
 * <p>Defines how types are mapped for a given MXBean or set of MXBeans.
 * An <code>MXBeanMappingFactory</code> can be specified either through the
 * {@link MXBeanMappingFactoryClass} annotation, or through the
 * {@link javax.management.MBeanOptions} argument to a {@link StandardMBean} constructor
 * or MXBean proxy.</p>
 *
 * <p>An <code>MXBeanMappingFactory</code> must return an <code>MXBeanMapping</code>
 * for any Java type that appears in the MXBeans that the factory is being
 * used for.  Usually it does that by handling any custom types, and
 * forwarding everything else to the {@linkplain #DEFAULT default mapping
 * factory}.</p>
 *
 * <p>Consider the <code>MyLinkedList</code> example from the {@link MXBeanMapping}
 * documentation.  If we are unable to change the <code>MyLinkedList</code> class
 * to add an {@link MXBeanMappingClass} annotation, we could achieve the same
 * effect by defining <code>MyLinkedListMappingFactory</code> as follows:</p>
 *
 * <pre> public class MyLinkedListMappingFactory implements MXBeanMappingFactory {
 *     public MyLinkedListMappingFactory() {}
 *
 *     public MXBeanMapping mappingForType(Type t, MXBeanMappingFactory f)
 *     throws OpenDataException {
 *         if (t == MyLinkedList.class)
 *             return new MyLinkedListMapping(t);
 *         else
 *             return MXBeanMappingFactory.DEFAULT.mappingForType(t, f);
 *     }
 * }
 * </pre>
 *
 * <p>The mapping factory handles only the <code>MyLinkedList</code> class.
 * Every other type is forwarded to the default mapping factory.
 * This includes types such as <code>MyLinkedList[]</code> and
 * <code>List&lt;MyLinkedList&gt;</code>; the default mapping factory will recursively
 * invoke <code>MyLinkedListMappingFactory</code> to map the contained
 * <code>MyLinkedList</code> type.</p>
 *
 * <p>Once we have defined <code>MyLinkedListMappingFactory</code>, we can use
 * it in an MXBean interface like this:</p>
 *
 * <pre> &#64;MXBeanMappingFactoryClass(MyLinkedListMappingFactory.class)
 * public interface SomethingMXBean {
 *     public MyLinkedList getSomething();
 * }
 * </pre>
 *
 * <p>Alternatively we can annotate the package that <code>SomethingMXBean</code>
 * appears in, or we can supply the factory to a {@link StandardMBean}
 * constructor or MXBean proxy.</p>
 */
public abstract class MXBeanMappingFactory {
	/**
	 * <p>Mapping factory that applies the default rules for MXBean
	 * mappings, as described in the <a href = "http://java.sun.com/javase/6/docs/api/javax/management/MXBean.html#MXBean-spec">MXBean specification</a>.</p>
	 */
	public static final MXBeanMappingFactory DEFAULT = new MXBeanMappingFactory() {
		/**
		 * @see MXBeanMappingFactory#mappingForType(Type, MXBeanMappingFactory)
		 */
		@Override
		public MXBeanMapping mappingForType(final Type t, final MXBeanMappingFactory f)
		throws OpenDataException {
			throw new UnsupportedOperationException("Not implemented");
		}
	};

	/**
	 * <p>Construct an instance of this class.</p>
	 */
	protected MXBeanMappingFactory() {
		// empty
	}

	/**
	 * <p>Return the mapping for the given Java type.  Typically, a
	 * mapping factory will return mappings for types it handles, and
	 * forward other types to another mapping factory, most often
	 * the {@linkplain #DEFAULT default one}.</p>
	 *
	 * @param t the Java type to be mapped.
	 * @param f the original mapping factory that was consulted to do
	 * the mapping.  A mapping factory should pass this parameter intact
	 * if it forwards a type to another mapping factory.  In the example,
	 * this is how <code>MyLinkedListMappingFactory</code> works for types
	 * like <code>MyLinkedList[]</code> and <code>List&lt;MyLinkedList&gt;</code>.
	 * @return the mapping for the given type.
	 * @throws OpenDataException if this type cannot be mapped.  This
	 * exception is appropriate if the factory is supposed to handle
	 * all types of this sort (for example, all linked lists), but
	 * cannot handle this particular type.
	 */
	public abstract MXBeanMapping mappingForType(final Type t, final MXBeanMappingFactory f)
	throws OpenDataException;
}
