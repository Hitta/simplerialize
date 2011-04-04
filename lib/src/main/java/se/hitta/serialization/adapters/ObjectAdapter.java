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
package se.hitta.serialization.adapters;

import java.io.IOException;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;

/**
 * The last resort {@link SerializationAdapter} when all else fails. Writes the
 * supplied {@link Object} with {@link Serializer#writeObject(Object)}. This
 * adapter is used internally within this library but should not be used by
 * clients of the library, hence it's {@link Deprecated}.
 */
public final class ObjectAdapter implements SerializationAdapter<Object>
{
    /*
     * (non-Javadoc)
     * @see se.hitta.serialization.SerializationAdapter#write(java.lang.Object, se.hitta.serialization.Serializer)
     */
    @Override
    public void write(final Object target, final Serializer serializer) throws IOException
    {
        if(target != null)
        {
            serializer.writeObject(target);
        }
    }
}