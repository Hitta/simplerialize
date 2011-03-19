package se.hitta.serialization;

import java.util.Iterator;

/**
 * This represents the serialization context after invoking {@link SerializationContext#beneath(String)}.
 *   
 */
public interface SerializationEachContext extends SerializerGlobalContext
{
    /**
     * Write each of the elements returned from the supplied {@link Iterable}.
     * 
     * @param container Name of the container of each element
     * @param elements The {@link Iterable} whos elements to write
     * @return The outer {@link SerializationContext}
     * @throws Exception
     */
    SerializationContext writePrimitives(String container, Iterable<?> elements) throws Exception;
    
    /**
     * Write each of the elements returned from the supplied {@link Iterator}.
     * 
     * @param container Name of the container for each element
     * @param elements The {@link Iterator} who's elements to write
     * @return The outer {@link SerializationContext}
     * @throws Exception
     */
    SerializationContext writePrimitives(String container, Iterator<?> elements) throws Exception;
    
    /**
     * Write each element of the supplied {@link Iterable}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    SerializationContext writeRepeating(Iterable<?> elements) throws Exception;
    
    /**
     * Write each element of the supplied {@link Iterator}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    SerializationContext writeRepeating(Iterator<?> elements) throws Exception;
}