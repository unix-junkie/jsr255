/*-
 * $Id$
 */
package javax.management.openmbean;

import java.io.InvalidObjectException;
import java.lang.reflect.Type;

/**
 * <p>A custom mapping between Java types and Open types for use in MXBeans.
 * To define such a mapping, subclass this class and define at least the
 * {@link #fromOpenValue(Object) fromOpenValue} and {@link #toOpenValue(Object) toOpenValue}
 * methods, and optionally the {@link #checkReconstructible()} method.
 * Then either use an {@link MXBeanMappingClass} annotation on your custom
 * Java types, or include this MXBeanMapping in an
 * {@link MXBeanMappingFactory}.</p>
 *
 * <p>For example, suppose we have a class <code>MyLinkedList</code>, which looks
 * like this:</p>
 *
 * <pre> public class MyLinkedList {
 *     public MyLinkedList(String name, MyLinkedList next) {...}
 *     public String getName() {...}
 *     public MyLinkedList getNext() {...}
 * }
 * </pre>
 *
 * <p>This is not a valid type for MXBeans, because it contains a
 * self-referential property "next" defined by the <code>getNext()</code>
 * method.  MXBeans do not support recursive types.  So we would like
 * to specify a mapping for <code>MyLinkedList</code> explicitly. When an
 * MXBean interface contains <code>MyLinkedList</code>, that will be mapped
 * into a <code>String[]</code>, which is a valid Open Type.</p>
 *
 * <p>To define this mapping, we first subclass <code>MXBeanMapping</code>:</p>
 *
 * <pre> public class MyLinkedListMapping extends MXBeanMapping {
 *     public MyLinkedListMapping(Type type) throws OpenDataException {
 *         super(MyLinkedList.class, ArrayType.getArrayType(SimpleType.STRING));
 *         if (type != MyLinkedList.class)
 *             throw new OpenDataException("Mapping only valid for MyLinkedList");
 *     }
 *
 *     &#64;Override
 *     public Object fromOpenValue(Object openValue) throws InvalidObjectException {
 *         String[] array = (String[]) openValue;
 *         MyLinkedList list = null;
 *         for (int i = array.length - 1; i &gt;= 0; i--)
 *             list = new MyLinkedList(array[i], list);
 *         return list;
 *     }
 *
 *     &#64;Override
 *     public Object toOpenValue(Object javaValue) throws OpenDataException {
 *         ArrayList&lt;String&gt; array = new ArrayList&lt;String&gt;();
 *         for (MyLinkedList list = (MyLinkedList) javaValue; list != null;
 *              list = list.getNext())
 *             array.add(list.getName());
 *         return array.toArray(new String[0]);
 *     }
 * }
 * </pre>
 *
 * <p>The call to the superclass constructor specifies what the
 * original Java type is (<code>MyLinkedList.class</code>) and what Open
 * Type it is mapped to (<code>ArrayType.getArrayType(SimpleType.STRING)</code>). The <code>fromOpenValue</code> method says how we go from the Open Type (<code>String[]</code>) to the Java type (<code>MyLinkedList</code>), and the <code>toOpenValue</code> method says how we go from the Java type to the Open
 * Type.</p>
 *
 * <p>With this mapping defined, we can annotate the <code>MyLinkedList</code>
 * class appropriately:</p>
 *
 * <pre> &#64;MXBeanMappingClass(MyLinkedListMapping.class)
 * public class MyLinkedList {...}
 * </pre>
 *
 * <p>Now we can use <code>MyLinkedList</code> in an MXBean interface and it
 * will work.</p>
 *
 * <p>If we are unable to modify the <code>MyLinkedList</code> class,
 * we can define an {@link MXBeanMappingFactory}.  See the documentation
 * of that class for further details.</p>
 */
public abstract class MXBeanMapping {
	private final Type javaType;

	private final OpenType<?> openType;

	private final Class<?> openClass;

	/**
	 * <p>Construct a mapping between the given Java type and the given
	 * Open Type.</p>
	 *
	 * @param javaType the Java type (for example, <code>MyLinkedList</code>).
	 * @param openType the Open Type (for example, <code>ArrayType.getArrayType(SimpleType.STRING)</code>)
	 * @throws NullPointerException if either argument is null.
	 */
	protected MXBeanMapping(final Type javaType, final OpenType<?> openType) {
		final boolean javaTypeNull = javaType == null;
		if (javaTypeNull || openType == null) {
			throw new NullPointerException((javaTypeNull ? "javaType" : "openType") + " is null");
		}
		this.javaType = javaType;
		this.openType = openType;
		try {
			this.openClass = Class.forName(this.openType.getClassName());
		} catch (final ClassNotFoundException cnfe) {
			throw new IllegalArgumentException(cnfe.getMessage(), cnfe);
		}
	}

	/**
	 * <p>The Java type that was supplied to the constructor.</p>
	 *
	 * @return the Java type that was supplied to the constructor.
	 */
	public final Type getJavaType() {
		return this.javaType;
	}

	/**
	 * <p>The Open Type that was supplied to the constructor.</p>
	 *
	 * @return the Open Type that was supplied to the constructor.
	 */
	public final OpenType<?> getOpenType() {
		return this.openType;
	}

	/**
	 * <p>The Java class that corresponds to instances of the
	 * {@linkplain #getOpenType() Open Type} for this mapping.</p>
	 *
	 * @return the Java class that corresponds to instances of the
	 * Open Type for this mapping.
	 * @see OpenType#getClassName()
	 */
	public final Class<?> getOpenClass() {
		return this.openClass;
	}

	/**
	 * <p>Convert an instance of the Open Type into the Java type.</p>
	 *
	 * @param openValue the value to be converted.
	 * @return the converted value.
	 * @throws InvalidObjectException if the value cannot be converted.
	 */
	public abstract Object fromOpenValue(final Object openValue) throws InvalidObjectException;

	/**
	 * <p>Convert an instance of the Java type into the Open Type.</p>
	 *
	 * @param javaValue the value to be converted.
	 * @return the converted value.
	 * @throws OpenDataException if the value cannot be converted.
	 */
	public abstract Object toOpenValue(final Object javaValue) throws OpenDataException;

	/**
	 * <p>Throw an appropriate InvalidObjectException if we will not
	 * be able to convert back from the open data to the original Java
	 * object.  The {@link #fromOpenValue(Object)} throws an
	 * exception if a given open data value cannot be converted.  This
	 * method throws an exception if <em>no</em> open data values can
	 * be converted.  The default implementation of this method never
	 * throws an exception.  Subclasses can override it as
	 * appropriate.</p>
	 *
	 * @throws InvalidObjectException if <code>fromOpenValue</code> will throw
	 * an exception no matter what its argument is.
	 */
	@SuppressWarnings("unused")
	public void checkReconstructible() throws InvalidObjectException {
		// empty
	}
}
