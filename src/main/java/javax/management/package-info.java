/*-
 * $Id$
 */

/**
 * <p>Provides the core classes for the Java Management Extensions.</p>
 *
 *        <h3 id="stdannot">Defining Standard MBeans with annotations</h3>
 *
 *        <p>As an alternative to creating an interface such as
 *            <code>ConfigurationMBean</code> and a class that implements it,
 *            you can write just the class, and use annotations to pick out the
 *            public methods that will make up the management interface. For
 *            example, the following class has the same management interface
 *            as a <code>Configuration</code> class that implements the
 *        <code>ConfigurationMBean</code> interface above.</p>
 *
 *        <pre>    {@link javax.management.MBean &#64;MBean}
 *    public class Configuration {
 *        {@link javax.management.ManagedAttribute &#64;ManagedAttribute}
 *        public int getCacheSize() {...}
 *        &#64;ManagedAttribute
 *        public void setCacheSize(int size) {...}
 *
 *        &#64;ManagedAttribute
 *        public long getLastChangedTime() {...}
 *
 *        {@link javax.management.ManagedOperation &#64;ManagedOperation}
 *        public void save() {...}
 *        ...
 *    }
 *        </pre>
 *
 *        <p>This approach simplifies development, but it does have two
 *            potential drawbacks.  First, if you run the Javadoc tool on
 *            this class, the documentation of the management interface may
 *            be mixed in with the documentation of non-management methods
 *            in the class.  Second, you cannot make a proxy
 *            as described <a href = "http://java.sun.com/javase/6/docs/api/javax/management/package-summary.html#proxy">below</a> if you do not have an
 *        interface like <code>ConfigurationMBean</code>.</p>
 */
package javax.management;
