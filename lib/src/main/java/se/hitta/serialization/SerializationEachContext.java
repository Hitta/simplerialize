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