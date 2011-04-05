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
package se.hitta.simplerialize.adapters;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.SerializationCapable;

/**
 * The default {@link AdapterMapper} implementation. The client may provide the
 * actual {@link Map} instance to be used as the backing storage thus allowing
 * customization on thread safety, load factors etc. This class registering
 * default adapters for {@link Object}, {@link String}, {@link Boolean},
 * {@link Number}, {@link Byte}, and {@link Iterable}. Use
 * {@link AdapterMapper#register(SerializationAdapter, Class...)} to override
 * these defaults or use {@link AdapterMapper#skip(Class)} to skip serialization
 * for a given type. 
 */
public final class DefaultAdapterMapper implements AdapterMapper
{
    private static final Logger log = LoggerFactory.getLogger(DefaultAdapterMapper.class);
    private final Map<Class<?>, SerializationAdapter<?>> mappings;

    /**
     * Creates a new instance with a {@link ConcurrentHashMap} as storage. A
     * {@link ConcurrentHashMap} ought to be a good default to allow non
     * blocking reads while still providing thread safety on write operations to
     * allow the {@link AdapterMapper} instance to be shared by serializers and
     * also re-used over time which ought to improve the lookup speed as the
     * resolver cache is self tuned and thus eliminating repeating expensive
     * {@link Class} graph traversals.
     */
    public DefaultAdapterMapper()
    {
        this(new ConcurrentHashMap<Class<?>, SerializationAdapter<?>>());
    }

    /**
     * Creates a new instance and uses the supplied {@link Map} as the backing
     * store / cache for the mappings of {@link Class} to
     * {@link SerializationAdapter}s.
     * 
     * @param storage the {@link Map} instance to use as the backing storage of
     * this instance
     * @see AdapterMapper#register(SerializationAdapter, Class...)
     * @see AdapterMapper#skip(Class)
     */
    public DefaultAdapterMapper(final Map<Class<?>, SerializationAdapter<?>> storage)
    {
        this.mappings = storage;
        register(new CharSequenceAdapter(), String.class, CharSequence.class, StringBuffer.class, StringBuilder.class);
        register(new NumberAdapter(), Number.class, Float.class, Integer.class, Short.class, Long.class);
        register(new BooleanAdapter(), Boolean.class);
        register(new ObjectAdapter(), Object.class);
        register(new MapEntryAdapter(), Entry.class);
        register(new IterableAdapter(), Iterable.class, Collection.class);
        register(new IteratorAdapter(), Iterator.class);
        register(new ByteBufferAsBase64Adapter(), ByteBuffer.class, MappedByteBuffer.class);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.adapter.AdapterMapper#register(se.hitta.simplerialize.adapter.SerializationAdapter, java.lang.Class<?>[])
     */
    @Override
    public AdapterMapper register(final SerializationAdapter<?> adapter, final Class<?>... classes)
    {
        for(final Class<?> clazz : classes)
        {
            this.mappings.put(clazz, adapter);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.AdapterMapper#skip(java.lang.Class)
     */
    @Override
    public <T> AdapterMapper skip(final Class<T> clazz)
    {
        return register(NullAdapter.instance, clazz);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.adapter.AdapterMapper#resolveAdapter(java.lang.Class)
     */
    @Override
    public <T> SerializationAdapter<T> resolveAdapter(final Class<T> clazz)
    {
        @SuppressWarnings("unchecked")
        final SerializationAdapter<T> adapter = search(clazz);
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

    /*
     * Recursively traverse upwards the class hierarchy for the given classes
     * and try to find either an registered {@link SerializationAdapter} or find
     * an implementation of {@link SerializationCapable} whereby
     * {@link DefaultAdapterMapper#createAdapterFor(Class)} will be used to
     * create and adapter for that{@link Class} and register it with this
     * instance.
     */
    @SuppressWarnings("rawtypes")
    private final SerializationAdapter search(final Class<?>... classes)
    {
        for(final Class<?> clazz : classes)
        {
            if(log.isDebugEnabled())
            {
                log.debug("Trying to find adapter for " + clazz);
            }
            if(clazz != null)
            {
                return lookup(clazz);
            }
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    private SerializationAdapter lookup(final Class<?> clazz)
    {
        if(this.mappings.containsKey(clazz))
        {
            return this.mappings.get(clazz);
        }
        else if(SerializationCapable.class.isAssignableFrom(clazz))
        {
            /*
             * The class we're trying to serialize is its own adapter.
             * So we register a bridge adapter to map between the
             * SerializationCapable and SerializationAdapter interfaces.
             */
            if(log.isDebugEnabled())
            {
                log.debug("Registering adapter bridge for " + clazz);
            }
            this.mappings.put(clazz, SerializationCapableAdapter.instance);
            return SerializationCapableAdapter.instance;
        }
        else
        {
            // Recurse upwards for interfaces
            SerializationAdapter<?> adapter = search(clazz.getInterfaces());
            if(adapter == null)
            {
                // Recurse upwards for superclasses
                adapter = search(clazz.getSuperclass());
            }
            if(adapter != null)
            {
                if(log.isDebugEnabled())
                {
                    log.debug("Registering " + adapter + " for " + clazz);
                }
                this.mappings.put(clazz, adapter);
            }
            return adapter;
        }
    }
}