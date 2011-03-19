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

import com.natpryce.maybe.Maybe;

/**
 * Interface for serialization methods that are available in all contexts.
 *
 */
public interface SerializerGlobalContext
{
    /**
     * Write the supplied target object using an adapter.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    public SerializationContext writeWithAdapter(Maybe<?> target) throws Exception;

    /**
     * Write the supplied target object using the adapter found by this {@link Serializer}'s underlying {@link AdapterMapper}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    public <T> SerializationContext writeWithAdapter(final T target) throws Exception;

    /**
     * Write the supplied target object using the adapter found for the supplied {@link Class}.
     * 
     * @param <T>
     * @param adapterClass
     * @param target
     * @return
     * @throws Exception
     */
    public <T> SerializationContext writeWithAdapter(final Class<T> adapterClass, final T target) throws Exception;
    

    /**
     * Write a name value pair with a {@link String} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, String value) throws Exception;

    /**
     * Write a name value pair with a {@link Boolean} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Boolean value) throws Exception;

    /**
     * Write a name value pair with a {@link Short} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Short value) throws Exception;

    /**
     * Write a name value pair with a {@link Integer} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Integer value) throws Exception;

    /**
     * Write a name value pair with a {@link Long} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Long value) throws Exception;

    /**
     * Write a name value pair with a {@link Float} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Float value) throws Exception;

    /**
     * Write a name value pair with a {@link Double} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Double value) throws Exception;

    /**
     * Write a name value pair with a {@link Maybe} value.
     * @param name
     * @param value
     * @return
     * @throws Exception
     */
    public SerializationContext writeNameValue(String name, Maybe<?> value) throws Exception;
}