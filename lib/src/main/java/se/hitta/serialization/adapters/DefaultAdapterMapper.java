/*
 * Copyright 2011 Hittapunktse AB (http://www.hitta.se/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.hitta.serialization.adapters;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationRootContext;

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
     * time which ought to improve the lookup speed as the resolver cache is self tuned and thus eliminating repeating expensive {@link Class} graph traversals.
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

        register(new ObjectAdapter(), Object.class);

        new PrimitiveAdapters(this);

        final IterableAdapter iterable = new IterableAdapter();
        register(iterable, Iterable.class);
        register(iterable, Collection.class);
        register(new IteratorAdapter(), Iterator.class);
        register(new ByteBufferAsBase64Adapter(), ByteBuffer.class, MappedByteBuffer.class);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.serialization.adapter.AdapterMapper#register(se.hitta.serialization.adapter.SerializationAdapter, java.lang.Class<?>[])
     */
    @Override
    public AdapterMapper register(final SerializationAdapter<?> adapter, final Class<?>... classes)
    {
        for(final Class<?> clazz : classes)
        {
            this.adapterMappings.put(clazz, adapter);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.serialization.adapter.AdapterMapper#resolveAdapter(java.lang.Class)
     */
    @Override
    public <T> SerializationAdapter<T> resolveAdapter(final Class<T> clazz)
    {
        @SuppressWarnings("unchecked")
        final SerializationAdapter<T> adapter = traverseAndFindAdapter(clazz);
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

    /**
     * Recursively traverse upwards the class hierarchy for the given classes and try to find either an {@link SerializationAdapter}
     * that's registered for a {@link Class} or find a {@link Class} that implements {@link SerializationCapable} whereby
     * {@link DefaultAdapterMapper#createBridgeFor(Class)} will be used to create and adapter for that {@link Class} and register it
     * with this {@link AdapterMapper}.
     * 
     * @param classes The classes to traverse
     * @return An {@link SerializationAdapter} or <code>null</code> if no adapter was found.
     */
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
                     * So we create a bridge between the SerializationCapable
                     * and SerializationAdapter interfaces and store that as the adapter.
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
                    SerializationAdapter<?> adapter = traverseAndFindAdapter(clazz.getInterfaces());
                    if(adapter == null)
                    {
                        adapter = traverseAndFindAdapter(clazz.getSuperclass());
                    }
                    if(adapter != null)
                    {
                        if(log.isDebugEnabled())
                        {
                            log.debug("Registering " + adapter + " for " + clazz);
                        }
                        this.adapterMappings.put(clazz, adapter);
                    }
                    return adapter;
                }
            }
        }
        return null;
    }

    /**
     * Create a {@link SerializationAdapter} for a {@link Class} that implements {@link SerializationCapable}.
     * 
     * @param clazz
     * @return
     */
    public SerializationAdapter<?> createBridgeFor(final Class<?> clazz)
    {
        return new SerializationAdapter<SerializationCapable>()
        {
            @Override
            public void write(final SerializationCapable target, final SerializationRootContext serializer) throws Exception
            {
                target.write(serializer);
            }
        };
    }
}