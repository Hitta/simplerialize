package se.hitta.serialization;

import java.util.Map;

import com.natpryce.maybe.Maybe;

public interface InsideContainer extends SerializationContext
{
    /**
     * End (close) a container.
     * 
     * @return
     * @throws Exception
     */
    public SerializationContext end() throws Exception;

    /**
     * Write the supplied target object using an adapter.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public InsideContainer writeWithAdapter(Maybe<?> target) throws Exception;

    /**
     * Write the supplied target object using the adapter found by this {@link Serializer}'s underlying {@link AdapterMapper}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public <T> InsideContainer writeWithAdapter(final T target) throws Exception;

    /**
     * Write the supplied target object using the adapter found for the supplied {@link Class}.
     * 
     * @param <T>
     * @param adapterClass
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public <T> InsideContainer writeWithAdapter(final Class<T> adapterClass, final T target) throws Exception;

    /**
     * Write each element of the supplied {@link Map} as using 
     * 
     * @param target
     * @return
     * @throws Exception
     */
    public SerializationContext writeRepeating(String container, Map<?, ?> elements) throws Exception;

    /**
     * Write the elements of the supplied {@link Map}. 
     * 
     * @param target
     * @return
     * @throws Exception
     */
    public InsideContainer writeRepeating(Map<?, ?> elements) throws Exception;
}