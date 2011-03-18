package se.hitta.serialization;

import java.util.Iterator;

/**
 * This represents the serialization context after invoking {@link SerializationContext#beneath(String)}.
 *   
 */
public interface SerializationEachContext
{
    /**
     * Write each of the elements returned from the supplied {@link Iterable}.
     * 
     * @param name Name of the container of each element
     * @param elements The {@link Iterable} whos elements to write
     * @return The outer {@link SerializationContext}
     * @throws Exception
     */
    SerializationContext writeEach(final String name, final Iterable<?> elements) throws Exception;
    
    /**
     * Write each of the elements returned from the supplied {@link Iterator}.
     * 
     * @param name Name of the container for each element
     * @param elements The {@link Iterator} who's elements to write
     * @return The outer {@link SerializationContext}
     * @throws Exception
     */
    SerializationContext writeEach(final String name, final Iterator<?> elements) throws Exception;
}