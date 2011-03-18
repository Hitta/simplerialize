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

import java.io.Writer;

/**
 * The interface for all concrete serializer implementations.
 * 
 * The main and most common usage pattern of a {@link SerializationContext} is
 * <code>
 * // Register your adapters
 * AdapterMapper mapper = ...;
 * // Create your serializer
 * Serializer serializer = ...;
 * // Get down to business
 * serializer.start().writeWithAdapter(someObject).finish();
 * </code>
 */
public interface SerializationContext extends SerializationEachContext, InsideContainer
{
    public SerializationEachContext beneath(final String container) throws Exception;

    /**
     * Start a container. For XML, usually an element. For JSON, usually an object.
     * 
     * @param name
     * @return
     * @throws Exception
     */
    public InsideContainer startContainer(final String name) throws Exception;

    public Writer getWriter();
}
