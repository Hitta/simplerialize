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
import java.util.Map;
import java.util.Map.Entry;


import com.natpryce.maybe.Maybe;

public abstract class AbstractSerializer implements SerializationContext, Serializer
{
    final AdapterMapper mapper;
    private final Writer writer;

    public AbstractSerializer(final Writer writer, final AdapterMapper mapper) throws Exception
    {
        this.writer = writer;
        this.mapper = mapper;
    }

    @Override
    public final SerializationContext writeWithAdapter(final Maybe<?> target) throws Exception
    {
        if(target != null && target.isKnown())
        {
            writeWithAdapter(target.value());
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> SerializationContext writeWithAdapter(final T target) throws Exception
    {
        writeWithAdapter((Class<T>)target.getClass(), target);
        return this;
    }

    @Override
    public final <T> SerializationContext writeWithAdapter(final Class<T> adapterClass, final T target) throws Exception
    {
        this.mapper.resolveAdapter(adapterClass).write(target, this);
        return this;
    }

    @Override
    public final SerializationContext writeRepeating(final String name, final Map<?, ?> elements) throws Exception
    {
        startContainer(name).writeRepeating(elements).endContainer();
        return this;
    }

    @Override
    public final SerializationContext writeRepeating(final Map<?, ?> elements) throws Exception
    {
        for(final Entry<?, ?> element : elements.entrySet())
        {
            writeNameValue(element.getKey().toString(), element.getValue().toString());
        }
        return this;
    }

    @Override
    public final Writer getWriter()
    {
        return this.writer;
    }
}