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

import se.hitta.serialization.adapters.DefaultAdapterMapper;

/**
 * Interface for classes that wish to be their own {@link SerializationAdapter}. When the {@link DefaultAdapterMapper} encounters
 * a class that implements this interface during its search for an adapter it will automatically create a {@link SerializationAdapter}
 * wrapper object around it and register it as an adapter for the type.
 */
public interface SerializationCapable
{
    /**
     * Write thyself using the provided {@link SerializationContext}.
     * 
     * @param serializer
     * @throws Exception
     */
    public void write(SerializationContext context) throws Exception;
}