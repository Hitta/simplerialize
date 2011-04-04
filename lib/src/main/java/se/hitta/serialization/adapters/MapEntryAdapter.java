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
import java.util.Map;
import java.util.Map.Entry;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;

/**
 * {@link SerializationAdapter} that writes all {@link Entry}s of the supplied
 * {@link Map} using {@link Serializer#writeNameValue(String, CharSequence)}.
 * Hence {@link Object#toString()} is called on both key and value of each
 * {@link Entry}. This is obviously insufficient if your map contains complex
 * objects in which case you should write a custom {@link SerializationAdapter}
 */
public final class MapEntryAdapter implements SerializationAdapter<Entry<?, ?>>
{
    /*
     * (non-Javadoc)
     * @see se.hitta.serialization.SerializationAdapter#write(java.lang.Object, se.hitta.serialization.Serializer)
     */
    @Override
    public void write(final Entry<?, ?> target, final Serializer serializer) throws IOException
    {
        serializer.writeNameValue(target.getKey().toString(), target.getValue().toString());
    }
}