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
import java.util.Iterator;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.context.CollectionContext;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

import com.natpryce.maybe.Maybe;

public final class JacksonJsonSerializer extends AbstractSerializer
{
    final JsonGenerator generator;
    private static final JsonFactory factory;

    static
    {
        factory = new JsonFactory();
    }

    public JacksonJsonSerializer(final Writer writer, final AdapterMapper mapper) throws IOException
    {
        super(writer, mapper);
        this.generator = factory.createJsonGenerator(writer);
    }

    @Override
    public RootContext start() throws IOException
    {
        this.generator.writeStartObject();
        return this;
    }

    @Override
    public void finish() throws IOException
    {
        this.generator.close();
    }

    @Override
    public ContainerContext startContainer(final String name) throws IOException
    {
        this.generator.writeObjectFieldStart(name);
        return this;
    }

    @Override
    public RootContext endContainer() throws IOException
    {
        this.generator.writeEndObject();
        return this;
    }


    @Override
    public CollectionContext beneath(final String container) throws IOException
    {
        this.generator.writeArrayFieldStart(container);
        return new CollectionContext()
        {

            @Override
            public RootContext eachComplex(final Iterable<?> elements) throws IOException
            {
                return eachComplex(elements.iterator());
            }

            @Override
            public RootContext eachComplex(final Iterator<?> elements) throws IOException
            {
                while(elements.hasNext())
                {
                    JacksonJsonSerializer.this.generator.writeStartObject();
                    writeWithAdapter(elements.next());
                    JacksonJsonSerializer.this.generator.writeEndObject();
                }
                JacksonJsonSerializer.this.generator.writeEndArray();
                return JacksonJsonSerializer.this;
            }

            @Override
            public RootContext eachPrimitive(final Iterable<?> elements) throws IOException
            {
                return eachPrimitives(elements.iterator());
            }

            @Override
            public RootContext eachPrimitives(final Iterator<?> elements) throws IOException
            {
                while(elements.hasNext())
                {
                    writeWithAdapter(elements.next());
                }
                JacksonJsonSerializer.this.generator.writeEndArray();
                return JacksonJsonSerializer.this;
            }

        };
    }

    @Override
    public RootContext writeObject(final Object target) throws IOException
    {
        this.generator.writeObject(target);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Boolean value) throws IOException
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final CharSequence value) throws IOException
    {
        this.generator.writeStringField(name, value.toString());
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Long value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Double value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Short value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Integer value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Float value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Maybe<?> value) throws IOException
    {
        if(value.isKnown())
        {
            this.generator.writeFieldName(name);
            writeWithAdapter(value.value());
        }
        return this;
    }

}