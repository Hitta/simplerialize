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

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.XmlSerializer;
import se.hitta.serialization.capable.SerializationCapable;

public final class AdapterMapper
{
    private static final Logger log = LoggerFactory.getLogger(AdapterMapper.class);
    private final Map<Class<?>, SerializationAdapter<?>> adapterMappings = new HashMap<Class<?>, SerializationAdapter<?>>();

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

    public AdapterMapper register(final Class<?> clazz, final SerializationAdapter<?> adapter)
    {
        this.adapterMappings.put(clazz, adapter);
        return this;
    }

    /**
     * 1. Check for any explicit adapter for this object's class
     * 2. Check for any adapter on any of this obeject's interfaces
     * 3. Propagate upwards to the superclass
     */
    @SuppressWarnings("rawtypes")
    public final SerializationAdapter resolveAdapter(final Class<?> clazz)
    {
        final SerializationAdapter adapter = traverseAndFindAdapter(clazz);
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
    public final SerializationAdapter traverseAndFindAdapter(final Class<?>... classes)
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
            else if(SerializationCapable.class.isAssignableFrom(clazz))
            {
                /*
                 * The class we're trying to serialize is its own adapter.
                 * So we create a bridge between the SerializationAdapter
                 * and SerializationCapable interfaces and store that as the adapter.
                 */
                final SerializationAdapter<?> bridge = createBridgeFor(clazz);
                if(log.isDebugEnabled())
                {
                    log.debug("Created adapter bridge for " + clazz);
                }
                this.adapterMappings.put(clazz, bridge);
                return bridge;
            }
            else if(clazz != null)
            {
                final SerializationAdapter<?> adapter = traverseAndFindAdapter(clazz.getInterfaces());
                if(adapter == null)
                {
                    return traverseAndFindAdapter(clazz.getSuperclass());
                }
                else
                {
                    this.adapterMappings.put(clazz, adapter);
                    return adapter;
                }
            }
        }
        return null;
    }

    private SerializationAdapter<?> createBridgeFor(Class<?> clazz)
    {
        return new SerializationAdapter<SerializationCapable<?>>()
        {
            @Override
            public void writeJson(final SerializationCapable<?> target, final JsonSerializer serializer) throws Exception
            {
                target.write(serializer);
            }

            @Override
            public void writeXml(final SerializationCapable<?> target, final XmlSerializer serializer) throws Exception
            {
                target.write(serializer);
            }
        };
    }
}