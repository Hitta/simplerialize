package se.hitta.serialization.adapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.XmlSerializer;
import se.hitta.serialization.capable.SerializationCapable;

/**
 * The default {@link AdapterMapper} implementation. The client may provide the actual {@link Map} instance to be used as the backing storage thus allowing
 * customization on thread safety, load factors etc. 
 */
public final class DefaultAdapterMapper implements AdapterMapper
{
    private static final Logger log = LoggerFactory.getLogger(DefaultAdapterMapper.class);
    private final Map<Class<?>, SerializationAdapter<?>> adapterMappings;

    /**
     * Creates a new instance with a {@link ConcurrentHashMap} as storage. A {@link ConcurrentHashMap} ought to be a good default to allow non blocking reads
     * while still providing thread safety on write operations to allow the {@link AdapterMapper} instance to be shared by serializers and also re-used over
     * time which would over time improve the lookup speed as the resolver cache is self tuned and thus limiting expensive {@link Class} graph traversals.
     */
    public DefaultAdapterMapper()
    {
        this(new ConcurrentHashMap<Class<?>, SerializationAdapter<?>>());
    }

    /**
     * Creates a new instance and uses the supplied {@link Map} as the backing store / cache for Class -> Adapter mappings. Instansiation also includes
     * registering default adapters for {@link Object}, {@link String}, {@link Boolean}, {@link Number}, {@link Byte}, and {@link Iterable}. Use
     * {@link AdapterMapper#register(Class, SerializationAdapter)} to override.
     *
     * @param storage
     */
    public DefaultAdapterMapper(final Map<Class<?>, SerializationAdapter<?>> storage)
    {
        this.adapterMappings = storage;
        final ObjectAdapter object = new ObjectAdapter();
        register(Object.class, object);
        register(String.class, object);
        register(Boolean.class, object);
        register(Number.class, object);
        register(Byte.class, object);

        final IterableAdapter iterable = new IterableAdapter();
        register(Iterable.class, iterable);
    }

    /* (non-Javadoc)
     * @see se.hitta.serialization.adapter.AM#register(java.lang.Class, se.hitta.serialization.adapter.SerializationAdapter)
     */
    @Override
    public AdapterMapper register(final Class<?> clazz, final SerializationAdapter<?> adapter)
    {
        this.adapterMappings.put(clazz, adapter);
        return this;
    }

    /* (non-Javadoc)
     * @see se.hitta.serialization.adapter.AM#resolveAdapter(java.lang.Class)
     */
    @Override
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
            if(clazz != null)
            {
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
                else
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
        }
        return null;
    }

    private SerializationAdapter<?> createBridgeFor(Class<?> clazz)
    {
        return new SerializationAdapter<SerializationCapable>()
        {
            @Override
            public void writeJson(final SerializationCapable target, final JsonSerializer serializer) throws Exception
            {
                target.write(serializer);
            }

            @Override
            public void writeXml(final SerializationCapable target, final XmlSerializer serializer) throws Exception
            {
                target.write(serializer);
            }
        };
    }
}