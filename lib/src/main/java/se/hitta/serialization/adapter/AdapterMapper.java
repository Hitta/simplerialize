package se.hitta.serialization.adapter;

import se.hitta.serialization.capable.SerializationCapable;

/**
 * An {@link AdapterMapper} maps {@link Class} objects to {@link SerializationAdapter} instances.
 * It's also responsible for trying to find a suitable adapter by moving up the type hierarchy
 * should one not already be registered for the supplied {@link Class}. 
 */
public interface AdapterMapper
{
    public abstract AdapterMapper register(final Class<?> clazz, final SerializationAdapter<?> adapter);

    /**
     * This method is responsible for finding an adapter. It usually boils down to:
     * <ol>
     * <li>Check for any adapter explicitly registered for the provide type</li>
     * <li>Check whether the type implements {@link SerializationCapable} and thus should be considered its own adapter</li>
     * <li>Check for any adapter on any of the interfaces that the type implements</li>
     * <li>Repeat from step (1) on the superclass type</li>
     * </ol>
     * 
     * @param clazz the type to provide an adapter for
     * @return the adapter that should be used to serialize instances of the type
     */
    public abstract <T> SerializationAdapter<T> resolveAdapter(final Class<T> clazz);
}