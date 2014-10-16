/*-
 * $Id$
 */
package javax.management;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;
import static javax.management.MBeanOperationInfo.UNKNOWN;

import java.beans.ConstructorProperties;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>An MBean whose management interface is determined by reflection
 * on a Java interface, but parameter names and textual descriptions are
 * specified using annotations on an implementation class.</p>
 *
 * <p>This class is not present in the original <a href =
 * "https://jcp.org/en/jsr/detail?id=255">JSR 255</a> <a href =
 * "https://jcp.org/aboutJava/communityprocess/edr/jsr255/">Early Draft Review</a>.
 * </p>
 *
 * @author <a href = "mailto:andrewbass@gmail.com">Andrew ``Bass'' Shcheglov</a>
 * @see Description
 * @see ConstructorProperties
 * @see ManagedAttribute
 * @see ManagedOperation
 * @see ManagedOperationParameter
 * @see MBean
 */
public class AnnotatedStandardMBean extends StandardMBean {
	private static final Map<String, Class<?>> BUILT_IN_MAP;

	static {
		final Map<String, Class<?>> builtInMap = new LinkedHashMap<String, Class<?>>();
		for (final Class<?> clazz : asList(Boolean.TYPE,
				Byte.TYPE,
				Character.TYPE,
				Double.TYPE,
				Float.TYPE,
				Integer.TYPE,
				Long.TYPE,
				Short.TYPE,
				Void.TYPE)) {
			builtInMap.put(clazz.getName(), clazz);
		}
		BUILT_IN_MAP = unmodifiableMap(builtInMap);
	}


	public <T> AnnotatedStandardMBean(final T implementation,
			final Class<T> mbeanInterface)
	throws NotCompliantMBeanException {
		super(implementation, mbeanInterface);
	}

	protected AnnotatedStandardMBean(final Class<?> mbeanInterface)
	throws NotCompliantMBeanException {
		super(mbeanInterface);
	}

	public <T> AnnotatedStandardMBean(final T implementation,
			final Class<T> mbeanInterface,
			final boolean isMXBean) {
		super(implementation, mbeanInterface, isMXBean);
	}

	protected AnnotatedStandardMBean(final Class<?> mbeanInterface,
			final boolean isMXBean) {
		super(mbeanInterface, isMXBean);
	}

	/*
	 * Type.
	 */

	/**
	 * @see StandardMBean#getDescription(MBeanInfo)
	 */
	@Override
	protected final String getDescription(final MBeanInfo info) {
		final Description description = this.getImplementationClass().getAnnotation(Description.class);
		return description != null ? description.value() : super.getDescription(info);
	}

	/*
	 * Constructors.
	 */

	/**
	 * @see StandardMBean#getDescription(MBeanConstructorInfo)
	 */
	@Override
	protected final String getDescription(final MBeanConstructorInfo info) {
		try {
			if (info == null) {
				return null;
			}
			final MBeanParameterInfo signature[] = info.getSignature();
			final Class<?> parameterTypes[] = getParameterTypes(signature);
			final Constructor<?> ctor = this.getImplementationClass().getConstructor(parameterTypes);
			if (ctor != null) {
				final Description description = ctor.getAnnotation(Description.class);
				if (description != null) {
					return description.value();
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getDescription(info);
	}

	/*
	 * Constructor parameters.
	 */

	/**
	 * @see StandardMBean#getDescription(MBeanConstructorInfo, MBeanParameterInfo, int)
	 */
	@Override
	protected final String getDescription(final MBeanConstructorInfo ctor,
			final MBeanParameterInfo param,
			final int sequence) {
		try {
			final MBeanParameterInfo signature[] = ctor.getSignature();
			final Constructor<?> constructor = this.getImplementationClass().getConstructor(getParameterTypes(signature));
			if (constructor != null) {
				final Annotation annotations[] = constructor.getParameterAnnotations()[sequence];
				for (final Annotation annotation : annotations) {
					if (annotation instanceof Description) {
						return ((Description) annotation).value();
					}
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getDescription(ctor, param, sequence);
	}

	/**
	 * @see StandardMBean#getParameterName(MBeanConstructorInfo, MBeanParameterInfo, int)
	 */
	@Override
	protected final String getParameterName(final MBeanConstructorInfo ctor,
			final MBeanParameterInfo param,
			final int sequence) {
		try {
			final MBeanParameterInfo signature[] = ctor.getSignature();
			final Constructor<?> constructor = this.getImplementationClass().getConstructor(getParameterTypes(signature));
			if (constructor != null) {
				final ConstructorProperties constructorProperties = constructor.getAnnotation(ConstructorProperties.class);
				if (constructorProperties != null) {
					final String parameterNames[] = constructorProperties.value();
					if (sequence < parameterNames.length) {
						return parameterNames[sequence];
					}
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getParameterName(ctor, param, sequence);
	}

	/*
	 * Attributes.
	 */

	/**
	 * @see StandardMBean#getDescription(MBeanAttributeInfo)
	 */
	@Override
	protected final String getDescription(final MBeanAttributeInfo info) {
		try {
			if (info == null) {
				return null;
			}
			final List<String> descriptions = new ArrayList<String>();
			if (info.isWritable()) {
				final Method method = this.getImplementationClass().getMethod("set" + info.getName(), forType(info.getType()));
				if (method != null) {
					final Description description = method.getAnnotation(Description.class);
					if (description != null) {
						descriptions.add(description.value());
					}
				}
			}
			if (info.isReadable()) {
				final Method method = this.getImplementationClass().getMethod((info.isIs() ? "is" : "get") + info.getName());
				if (method != null) {
					final Description description = method.getAnnotation(Description.class);
					if (description != null) {
						descriptions.add(description.value());
					}
				}
			}
			if (!descriptions.isEmpty()) {
				return descriptions.iterator().next();
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getDescription(info);
	}

	/*
	 * Operations.
	 */

	/**
	 * @see StandardMBean#getImpact(MBeanOperationInfo)
	 */
	@Override
	protected final int getImpact(final MBeanOperationInfo info) {
		try {
			if (info == null) {
				return UNKNOWN;
			}
			final MBeanParameterInfo signature[] = info.getSignature();
			final Class<?> parameterTypes[] = getParameterTypes(signature);
			final Method method = this.getImplementationClass().getMethod(info.getName(), parameterTypes);
			if (method != null) {
				final ManagedOperation operation = method.getAnnotation(ManagedOperation.class);
				if (operation != null) {
					return operation.impact().getCode();
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getImpact(info);
	}

	/**
	 * @see StandardMBean#getDescription(MBeanOperationInfo)
	 */
	@Override
	protected final String getDescription(final MBeanOperationInfo info) {
		try {
			if (info == null) {
				return null;
			}
			final MBeanParameterInfo signature[] = info.getSignature();
			final Class<?> parameterTypes[] = getParameterTypes(signature);
			final Method method = this.getImplementationClass().getMethod(info.getName(), parameterTypes);
			if (method != null) {
				final Description description = method.getAnnotation(Description.class);
				if (description != null) {
					return description.value();
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getDescription(info);
	}

	/*
	 * Operation parameters.
	 */

	/**
	 * @see StandardMBean#getParameterName(MBeanOperationInfo, MBeanParameterInfo, int)
	 */
	@Override
	protected final String getParameterName(final MBeanOperationInfo op,
			final MBeanParameterInfo param,
			final int sequence) {
		try {
			final MBeanParameterInfo[] signature = op.getSignature();
			final Class<?> parameterTypes[] = getParameterTypes(signature);
			final Method method = this.getImplementationClass().getMethod(op.getName(), parameterTypes);
			if (method != null) {
				final Annotation annotations[] = method.getParameterAnnotations()[sequence];
				for (final Annotation annotation : annotations) {
					if (annotation instanceof ManagedOperationParameter) {
						return ((ManagedOperationParameter) annotation).value();
					}
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getParameterName(op, param, sequence);
	}

	/**
	 * @see StandardMBean#getDescription(MBeanOperationInfo, MBeanParameterInfo, int)
	 */
	@Override
	protected final String getDescription(final MBeanOperationInfo op,
			final MBeanParameterInfo param,
			final int sequence) {
		try {
			final MBeanParameterInfo[] signature = op.getSignature();
			final Class<?> parameterTypes[] = getParameterTypes(signature);
			final Method method = this.getImplementationClass().getMethod(op.getName(), parameterTypes);
			if (method != null) {
				final Annotation annotations[] = method.getParameterAnnotations()[sequence];
				for (final Annotation annotation : annotations) {
					if (annotation instanceof Description) {
						return ((Description) annotation).value();
					}
				}
			}
		} catch (final ClassNotFoundException ignored) {
			// Ignore.
		} catch (final NoSuchMethodException ignored) {
			// Ignore.
		}
		return super.getDescription(op, param, sequence);
	}

	private static Class<?>[] getParameterTypes(final MBeanParameterInfo signature[])
	throws ClassNotFoundException {
		final Class<?> parameterTypes[] = new Class<?>[signature.length];
		int i = 0;
		for (final MBeanParameterInfo p : signature) {
			parameterTypes[i++] = forType(p.getType());
		}
		return parameterTypes;
	}

	private static Class<?> forType(final String type) throws ClassNotFoundException {
		final Class<?> primitiveClass = BUILT_IN_MAP.get(type);
		return primitiveClass == null ? Class.forName(type) : primitiveClass;
	}
}
