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

import javax.management.remote.JMXConnectionNotification;

/**
 *<p>Specifies the kinds of notification an MBean can emit, when this
 * cannot be represented by a single {@link NotificationInfo &#64;NotificationInfo} annotation.</p>
 *
 * <p>For example, this annotation specifies that an MBean can emit
 * {@link AttributeChangeNotification} and {@link JMXConnectionNotification}:</p>
 *
 * <pre> <code>&#64;NotificationInfos</code>(
 *     <code>&#64;NotificationInfo</code>(
 *         types = {{@link AttributeChangeNotification#ATTRIBUTE_CHANGE}},
 *         notificationClass = AttributeChangeNotification.class),
 *     <code>&#64;NotificationInfo</code>(
 *         types = {{@link JMXConnectionNotification#OPENED},
 *                  {@link JMXConnectionNotification#CLOSED}},
 *         notificationClass = JMXConnectionNotification.class)
 * )
 * </pre>
 *
 * <p>If an MBean has both <code>NotificationInfo</code> and <code>NotificationInfos</code> on the same class or interface, the effect is
 * the same as if the <code>NotificationInfo</code> were moved inside the
 * <code>NotificationInfos</code>.</p>
 */
@Documented
@Inherited
@Target(value = TYPE)
@Retention(value = RUNTIME)
public @interface NotificationInfos {
	NotificationInfo[] value();
}
