/*-
 * $Id$
 */
package javax.management;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>Specifies the kinds of notification an MBean can emit.  In both the
 * following examples, the MBean emits notifications of type
 * <code>"com.example.notifs.create"</code> and of type
 * <code>"com.example.notifs.destroy"</code>:</p>
 *
 * <pre> // Example one: a Standard MBean
 * <code>&#64;NotificationInfo</code>(types={"com.example.notifs.create",
 *                          "com.example.notifs.destroy"})
 * public interface CacheMBean {...}
 *
 * public class Cache implements CacheMBean {...}
 * </pre>
 *
 * <pre> // Example two: an annotated MBean
 * {@link MBean &#64;MBean}
 * <code>&#64;NotificationInfo</code>(types={"com.example.notifs.create",
 *                          "com.example.notifs.destroy"})
 * public class Cache {...}
 * </pre>
 *
 * <p>Each <code>&#64;NotificationInfo</code> produces an {@link MBeanNotificationInfo} inside the {@link MBeanInfo} of each MBean
 * to which the annotation applies.</p>
 *
 * <p>If you need to specify different notification classes, or different
 * descriptions for different notification types, then you can group
 * several <code>&#64;NotificationInfo</code> annotations into a containing
 * {@link NotificationInfos &#64;NotificationInfos} annotation.
 *
 * </p><p>The <code>NotificationInfo</code> and <code>NotificationInfos</code>
 * annotations can be applied to the MBean implementation class, or to
 * any parent class or interface.  These annotations on a class take
 * precedence over annotations on any superclass or superinterface.
 * If an MBean does not have these annotations on its class or any
 * superclass, then superinterfaces are examined.  It is an error for
 * more than one superinterface to have these annotations, unless one
 * of them is a child of all the others.</p>
 */
@Documented
@Inherited
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface NotificationInfo {
	/**
	 * <p>The {@linkplain Notification#getType() notification types}
	 * that this MBean can emit.</p>
	 */
	String[] types();

	/**
	 * <p>The class that emitted notifications will have.  It is recommended
	 * that this be {@link Notification}, or one of its standard subclasses
	 * in the JMX API.</p>
	 */
	Class<? extends Notification> notificationClass() default Notification.class;

	/**
	 * <p>The description of this notification.  For example:
	 *
	 *</p><pre> <code>&#64;NotificationInfo</code>(
	 *         types={"com.example.notifs.create"},
	 *         description=<code>&#64;Description</code>("object created"))
	 *</pre>
	 */
	Description description() default @Description("");

	/**
	 * <p>Additional descriptor fields for the derived <code>MBeanNotificationInfo</code>.  They are specified in the same way as
	 * for the {@link DescriptorFields &#64;DescriptorFields} annotation,
	 * for example:</p>
	 * <pre> <code>&#64;NotificationInfo</code>(
	 *         types={"com.example.notifs.create"},
	 *         descriptorFields={"severity=6"})
	 * </pre>
	 */
	String[] descriptorFields() default {};
}
