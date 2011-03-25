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
package se.hitta.serialization.implementations;

import java.io.IOException;
import java.io.Writer;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.Serializer;
import se.hitta.serialization.context.ContainerContext;

import com.natpryce.maybe.Maybe;

public abstract class AbstractSerializer implements Serializer, ContainerContext
{
    private final AdapterMapper mapper;
    private final Writer writer;

    public AbstractSerializer(final Writer writer, final AdapterMapper mapper)
    {
        this.writer = writer;
        this.mapper = mapper;
    }

    @Override
    public final ContainerContext writeWithAdapter(final Maybe<?> target) throws IOException
    {
        if(target != null && target.isKnown())
        {
            writeWithAdapter(target.value());
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> ContainerContext writeWithAdapter(final T target) throws IOException
    {
        writeWithAdapter((Class<T>)target.getClass(), target);
        return this;
    }

    @Override
    public final <T> ContainerContext writeWithAdapter(final Class<T> adapterClass, final T target) throws IOException
    {
        this.mapper.resolveAdapter(adapterClass).write(target, this);
        return this;
    }

    @Override
    public final Writer getWriter()
    {
        return this.writer;
    }
}