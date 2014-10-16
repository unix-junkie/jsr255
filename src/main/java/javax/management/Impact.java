/*-
 * $Id$
 */
package javax.management;

/**
 *<p>Defines the impact of an MBean operation, in particular whether it
 * has an effect on the MBean or simply returns information.  This enum
 * is used in the {@link ManagedOperation &#64;ManagedOperation} annotation.
 * Its {@link #getCode()} method can be used to get an <code>int</code> suitable
 * for use as the <code>impact</code> parameter in an {@link MBeanOperationInfo}
 * constructor.</p>
 */
public enum Impact {
	/**
	 * The operation is read-like: it returns information but does not change any state.
	 *
	 * @see MBeanOperationInfo#INFO
	 */
	INFO(MBeanOperationInfo.INFO),

	/**
	 * The operation is write-like: it has an effect but does not return any information from the MBean.
	 *
	 * @see MBeanOperationInfo#ACTION
	 */
	ACTION(MBeanOperationInfo.ACTION),

	/**
	 * The operation is both read-like and write-like: it has an effect, and it also returns information from the MBean.
	 *
	 * @see MBeanOperationInfo#ACTION_INFO
	 */
	ACTION_INFO(MBeanOperationInfo.ACTION_INFO),

	/**
	 * The impact of the operation is unknown or cannot be expressed using one of the other values.
	 *
	 * @see MBeanOperationInfo#UNKNOWN
	 */
	UNKNOWN(MBeanOperationInfo.UNKNOWN),
	;

	private final int code;

	Impact(final int code) {
		assert code == this.ordinal();
		this.code = code;
	}

	/**
	 * The equivalent <code>int</code> code used by the {@link MBeanOperationInfo} constructors.
	 *
	 * @return the <code>int</code> code.
	 */
	public int getCode() {
		return this.code;
	}

	/**
	 * Return the <code>Impact</code> value corresponding to the given <code>int</code>
	 * code.  The <code>code</code> is the value that would be used in an
	 * <code>MBeanOperationInfo</code> constructor.
	 *
	 * @param code the <code>int</code> code.
	 * @return an <code>Impact</code> value <code>x</code> such that
	 * <code>code == x.</code>{@link #getCode()}, or <code>Impact.UNKNOWN</code>
	 * if there is no such value.
	 */
	public static Impact forCode(final int code) {
		switch (code) {
		case MBeanOperationInfo.INFO:
			return INFO;
		case MBeanOperationInfo.ACTION:
			return ACTION;
		case MBeanOperationInfo.ACTION_INFO:
			return ACTION_INFO;
		case MBeanOperationInfo.UNKNOWN:
		default:
			return UNKNOWN;
		}
	}
}
