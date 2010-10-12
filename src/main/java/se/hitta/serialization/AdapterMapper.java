package se.hitta.serialization;

import java.util.HashMap;
import java.util.Map;

public final class AdapterMapper
{
    private final Map<Class<?>, Adapter<?>> adapterMappings = new HashMap<Class<?>, Adapter<?>>();

    public AdapterMapper()
    {
        register(String.class, stringAdapter);
        register(Integer.class, integerAdapter);
        register(Object.class, objectAdapter);
    }

    public void register(final Class<?> clazz, final Adapter<?> adapter)
    {
        this.adapterMappings.put(clazz, adapter);
    }

    /**
     * 1. Check for any explicit adapter for this object's class
     * 2. Check for any adapter on any of this obeject's interfaces
     * 3. Propagate upwards to the superclass
     * 
     * FIXME: Implement resolver cache
     */
    public final Adapter resolveAdapter(final Class<?> clazz)
    {
        final Adapter adapter = traverseAndFindAdapter(clazz);
        return (adapter == null) ? objectAdapter : adapter;
    }

    public final Adapter traverseAndFindAdapter(final Class<?>... classes)
    {
        for(final Class<?> clazz : classes)
        {
            if(this.adapterMappings.containsKey(clazz))
            {
                return this.adapterMappings.get(clazz);
            }
            else if(clazz != null)
            {
                final Adapter<?> adapter = traverseAndFindAdapter(clazz.getInterfaces());
                return (adapter == null) ? traverseAndFindAdapter(clazz.getSuperclass()) : adapter;
            }
        }
        return null;
    }

    private static final Adapter<Object> objectAdapter = new Adapter<Object>()
    {
        @Override
        public void write(final Object target, final Serializer serializer) throws Exception
        {
            serializer.write(target == null ? (String)null : target.toString());
        }
    };

    private static final Adapter<Integer> integerAdapter = new Adapter<Integer>()
    {
        @Override
        public void write(final Integer target, final Serializer serializer) throws Exception
        {
            serializer.write(target);
        }
    };

    private static final Adapter<String> stringAdapter = new Adapter<String>()
    {
        @Override
        public void write(final String target, final Serializer serializer) throws Exception
        {
            serializer.write(target);
        }
    };
}