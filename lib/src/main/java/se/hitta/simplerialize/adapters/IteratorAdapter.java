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
package se.hitta.simplerialize.adapters;

import java.io.IOException;
import java.util.Iterator;

import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

/**
 * {@link SerializationAdapter} that writes the supplied {@link Iterator} by
 * iterating over it and delegating the serialization of the individual elements
 * to {@link Serializer#writeWithAdapter(Object)}
 */
final class IteratorAdapter implements SerializationAdapter<Iterator<?>>
{
    @Override
    public void write(final Iterator<?> target, final Serializer serializer) throws IOException
    {
        while(target.hasNext())
        {
            serializer.writeWithAdapter(target.next());
        }
    }
}