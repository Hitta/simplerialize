package se.hitta.serialization.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AdapterMapper
{
    private static final Logger log = LoggerFactory.getLogger(AdapterMapper.class);
    private final Map<Class<?>, Adapter<?>> adapterMappings = new HashMap<Class<?>, Adapter<?>>();

    public AdapterMapper()
    {
        final ObjectAdapter object = new ObjectAdapter();
        register(Object.class, object);
        register(String.class, object);
        register(Boolean.class, object);
        register(Boolean.class, object);
        register(Number.class, object);
        register(Short.class, object);
        register(Integer.class, object);
        register(Double.class, object);
        register(Float.class, object);
        register(Long.class, object);
        register(Byte.class, object);

        final IterableAdapter iterable = new IterableAdapter();
        register(Iterable.class, iterable);
        register(Collection.class, iterable);
        register(List.class, iterable);
        register(ArrayList.class, iterable);
        register(LinkedList.class, iterable);
        register(RandomAccess.class, iterable);
        register(Queue.class, iterable);
        register(Set.class, iterable);
    }

    public AdapterMapper register(final Class<?> clazz, final Adapter<?> adapter)
    {
        this.adapterMappings.put(clazz, adapter);
        return this;
    }

    /**
     * 1. Check for any explicit adapter for this object's class
     * 2. Check for any adapter on any of this obeject's interfaces
     * 3. Propagate upwards to the superclass
     * 
     * FIXME: Implement resolver cache
     */
    @SuppressWarnings("rawtypes")
    public final Adapter resolveAdapter(final Class<?> clazz)
    {
        final Adapter adapter = traverseAndFindAdapter(clazz);
        if(adapter == null)
        {
            throw new IllegalStateException("No adapter found for " + clazz);
        }
        else
        {
            if(log.isDebugEnabled())
            {
                log.debug("Found adapter " + adapter + " for " + clazz);
            }
            return adapter;
        }
    }

    @SuppressWarnings("rawtypes")
    public final Adapter traverseAndFindAdapter(final Class<?>... classes)
    {
        for(final Class<?> clazz : classes)
        {
            if(log.isDebugEnabled())
            {
                log.debug("Trying to find adapter for " + clazz);
            }
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
}