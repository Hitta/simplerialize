package se.hitta.simplerialize.implementations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import se.hitta.simplerialize.Serializer;

/**
 * This {@link Class} wraps an arbitrary number of {@link Serializer}s. All
 * methods invoked on an {@link CompositeSerializer} are executed on all of the
 * wrapped {@link Serializer}s in the order they were passed to
 * {@link CompositeSerializer#wrap(Serializer...)}. It was developed as an
 * experiment and may be used to ease comparison of JSON and XML serialization
 * without having to explicitly manage one {@link Serializer} instance per
 * format. <strong>Don't use it</strong> anywhere near production.
 */
public final class CompositeSerializer
{
    private static final Class<?>[] interfaces = new Class[] { Serializer.class };

    /**
     * Create a new {@link CompositeSerializer} that wraps the supplied {@link Serializer} instances.
     * 
     * @param serializers The {@link Serializer}s to wrap
     * @return A new {@link CompositeSerializer} as a {@link Serializer}
     */
    public static Serializer wrap(final Serializer... serializers)
    {
        final ClassLoader loader = serializers[0].getClass().getClassLoader();
        return (Serializer)Proxy.newProxyInstance(loader, interfaces, new SerializersProxy(serializers));
    }

    static final class SerializersProxy implements InvocationHandler
    {
        private final Serializer[] serializers;

        SerializersProxy(final Serializer... serializers)
        {
            this.serializers = serializers;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable
        {
            try
            {
                final Object[] results = invoke(method, args);
                return (results.length == 0) ? null : proxy;
            }
            catch(final InvocationTargetException e)
            {
                throw e.getTargetException();
            }
            catch(final Exception e)
            {
                throw new RuntimeException(method.getName() + ": " + e.getMessage(), e);
            }
        }

        private Object[] invoke(final Method method, final Object[] args) throws IllegalAccessException, InvocationTargetException
        {
            final List<Object> results = new ArrayList<Object>();
            for(final Serializer serializer : this.serializers)
            {
                final Object result = method.invoke(serializer, args);
                if(result != null)
                {
                    results.add(result);
                }
            }
            return results.toArray();
        }
    }
}