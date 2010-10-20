package se.hitta.serialization.adapter;

import se.hitta.serialization.capable.SerializationCapable;

/**
 * An adapter mapper maps {@link Class} objects to {@link SerializationAdapter} instances.
 * It's also responsible for trying to find a suitable adapter should one not already be registered. 
 */
public interface AdapterMapper
{

    public abstract AdapterMapper register(final Class<?> clazz, final SerializationAdapter<?> adapter);

    /**
     * This class is responsible for finding an adapter, it usually boils down to:
     * 1. Check for any explicit adapter for this object's class
     * 2. Check wheyher this class implements {@link SerializationCapable} and thus should be considered its own adapter
     * 3. Check for any adapter on any of this obeject's interfaces
     * 4. Repeat on the superclass
     */
    @SuppressWarnings("rawtypes")
    public abstract SerializationAdapter resolveAdapter(final Class<?> clazz);

}