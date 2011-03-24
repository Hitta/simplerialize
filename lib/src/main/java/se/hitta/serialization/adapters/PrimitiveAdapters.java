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

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.SerializationRootContext;

final class PrimitiveAdapters
{
    public PrimitiveAdapters(final AdapterMapper mapper)
    {
        mapper.register(new SerializationAdapter<CharSequence>()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void write(final CharSequence target, final SerializationRootContext serializer) throws Exception
            {
                serializer.writeObject(target);
            }
        }, String.class, CharSequence.class, StringBuffer.class, StringBuilder.class);
        mapper.register(new SerializationAdapter<Number>()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void write(final Number target, final SerializationRootContext serializer) throws Exception
            {
                serializer.writeObject(target);
            }
        }, Number.class, Float.class, Integer.class, Short.class, Long.class);
        mapper.register(new SerializationAdapter<Boolean>()
        {
            @SuppressWarnings("deprecation")
            @Override
            public void write(final Boolean target, final SerializationRootContext serializer) throws Exception
            {
                serializer.writeObject(target);
            }
        }, Boolean.class);
    }
}