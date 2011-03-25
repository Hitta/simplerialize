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

import se.hitta.serialization.context.RootContext;

/**
 * The interface for all concrete serializer implementations.
 * 
 * The main and most common usage pattern of a {@link Serializer} is
 * <code>
 * // Register your adapters
 * AdapterMapper mapper = ...;
 * // Create your serializer
 * Serializer serializer = ...;
 * // Get down to business
 * serializer.start().writeWithAdapter(someObject).finish();
 * </code>
 */
public interface Serializer
{
    /**
     * Start output, only call once and <strong>don't ever</strong> call it from within a {@link SerializationAdapter}
     * 
     * @return
     * @throws Exception
     */
    RootContext start() throws Exception;

    /**
     * Finish and flush any remaining output, <strong>don't ever</strong> call it from within a {@link SerializationAdapter}
     * @throws Exception
     */
    void finish() throws Exception;

    /**
     * 
     * @return
     */
    Writer getWriter();
}