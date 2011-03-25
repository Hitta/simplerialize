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
package se.hitta.serialization.context;

import java.io.IOException;
import java.util.Iterator;

import se.hitta.serialization.SerializationAdapter;

/**
 * This represents the serialization context after invoking {@link RootContext#beneath(String)}.
 *   
 */
public interface CollectionContext
{
    /**
     * Write each primitive returned from the supplied {@link Iterable}.
     * 
     * @param elements The {@link Iterable} whose elements to write
     * @return The outer {@link RootContext}
     * @throws Exception
     */
    RootContext eachPrimitive(Iterable<?> elements) throws IOException;

    /**
     * Write each primitive returned from the supplied {@link Iterator}.
     * 
     * @param elements The {@link Iterator} who's elements to write
     * @return The outer {@link RootContext}
     * @throws Exception
     */
    RootContext eachPrimitives(Iterator<?> elements) throws IOException;

    /**
     * Write each object of the supplied {@link Iterable} with their respective {@link SerializationAdapter}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    RootContext eachComplex(Iterable<?> elements) throws IOException;

    /**
     * Write each element of the supplied {@link Iterator}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    RootContext eachComplex(Iterator<?> elements) throws IOException;
}