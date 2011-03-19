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
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationContext;
import se.hitta.serialization.SerializationEachContext;

import com.natpryce.maybe.Maybe;

public final class JacksonJsonSerializer extends AbstractSerializer
{
    private final JsonGenerator generator;
    private static final JsonFactory factory;

    static
    {
        factory = new JsonFactory();
    }

    public JacksonJsonSerializer(final Writer writer, final AdapterMapper mapper) throws Exception
    {
        super(writer, mapper);
        this.generator = factory.createJsonGenerator(writer);
    }

    @Override
    public SerializationContext start() throws Exception
    {
        this.generator.writeStartObject();
        return this;
    }

    @Override
    public void finish() throws Exception
    {
        this.generator.close();
    }

    @Override
    public InsideContainer startContainer(final String name) throws JsonGenerationException, IOException
    {
        this.generator.writeObjectFieldStart(name);
        return this;
    }

    @Override
    public SerializationContext end() throws JsonGenerationException, IOException
    {
        this.generator.writeEndObject();
        return this;
    }

    @Override
    public SerializationContext writeRepeating(final Iterable<?> elements) throws Exception
    {
        writeRepeating(elements.iterator());
        return this;
    }

    @Override
    public SerializationContext writeRepeating(final Iterator<?> elements) throws Exception
    {
        while(elements.hasNext())
        {
            this.generator.writeStartObject();
            writeWithAdapter(elements.next());
            this.generator.writeEndObject();
        }
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public SerializationEachContext beneath(final String container) throws Exception
    {
        this.generator.writeArrayFieldStart(container);
        return this;
    }

    @Override
    public SerializationContext writePrimitives(final String name, final Iterable<?> elements) throws Exception
    {
        writePrimitives(name, elements.iterator());
        return this;
    }

    @Override
    public SerializationContext writePrimitives(final String name, final Iterator<?> elements) throws Exception
    {
        while(elements.hasNext())
        {
            this.generator.writeStartObject();
            this.generator.writeFieldName(name);
            writeWithAdapter(elements.next());
            this.generator.writeEndObject();
        }
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public SerializationContext writeObject(final Object target) throws Exception
    {
        this.generator.writeObject(target);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Boolean value) throws Exception
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final String value) throws JsonGenerationException, IOException
    {
        this.generator.writeStringField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Long value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Double value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Short value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Integer value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Float value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            this.generator.writeFieldName(name);
            writeWithAdapter(value.value());
        }
        return this;
    }
}