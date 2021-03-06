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
package se.hitta.simplerialize.implementations;

import java.io.IOException;
import java.io.Writer;

import com.fasterxml.jackson.core.type.TypeReference;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.Serializer;

abstract class AbstractSerializer implements Serializer
{
    private final AdapterMapper mapper;
    private final Writer writer;

    AbstractSerializer(final Writer writer, final AdapterMapper mapper)
    {
        this.writer = writer;
        this.mapper = mapper;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeWithAdapter(com.google.common.base.Optional)
     */
    @Override
    public final Serializer writeWithAdapter(final com.google.common.base.Optional<?> target) throws IOException
    {
        if(target != null && target.isPresent())
        {
            writeWithAdapter(target.get());
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeWithAdapter(java.util.Optional)
     */
    @Override
    public final Serializer writeWithAdapter(final java.util.Optional<?> target) throws IOException
    {
        if(target != null && target.isPresent())
        {
            writeWithAdapter(target.get());
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeWithAdapter(java.lang.Object)
     */
    @Override
    @SuppressWarnings("unchecked")
    public final <T> Serializer writeWithAdapter(final T target) throws IOException
    {
        writeWithAdapter((Class<T>)target.getClass(), target);
        return this;
    }
    
    /* (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeWithAdapter(java.lang.Object, org.codehaus.jackson.type.TypeReference)
     */
    @Override
    public <T> Serializer writeWithAdapter(T target, TypeReference<T> typeReference) throws IOException
    {
        this.mapper.resolveAdapter(typeReference).write(target, this);
        return this;
    }
    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeWithAdapter(java.lang.Class, java.lang.Object)
     */
    @Override
    public final <T> Serializer writeWithAdapter(final Class<T> adapterClass, final T target) throws IOException
    {
        this.mapper.resolveAdapter(adapterClass).write(target, this);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#printTo(java.lang.Appendable)
     */
    @Override
    public final void printTo(final Appendable target) throws IOException
    {
        target.append(this.writer.toString());
    }
}