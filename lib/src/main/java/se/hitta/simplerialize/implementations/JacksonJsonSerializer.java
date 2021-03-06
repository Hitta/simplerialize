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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

/**
 * The calling application has to separately close the underlying {@link OutputStream} and {@link Writer} instances used to create the serializer.
 */
public final class JacksonJsonSerializer extends AbstractSerializer
{
    private static final JsonFactory factory = new JsonFactory();
    private final JsonGenerator generator;

    /**
     *
     * @param stream
     * @param mapper
     * @throws IOException
     */
    public JacksonJsonSerializer(final OutputStream stream, final AdapterMapper mapper) throws IOException
    {
        this(new OutputStreamWriter(stream), mapper);
    }

    /**
     *
     * @param writer
     * @param mapper
     * @throws IOException
     */
    public JacksonJsonSerializer(final Writer writer, final AdapterMapper mapper) throws IOException
    {
        super(writer, mapper);
        this.generator = factory.createGenerator(writer);
        this.generator.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#start()
     */
    @Override
    public Serializer start() throws IOException
    {
        this.generator.writeStartObject();
        return this;
    }

    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException
    {
        this.generator.close();
    }

    /*
     * (non-Javadoc)
     * @see java.io.Flushable#flush()
     */
    @Override
    public void flush() throws IOException
    {
        this.generator.flush();
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#startContainer(java.lang.String)
     */
    @Override
    public Serializer startContainer(final String name) throws IOException
    {
        this.generator.writeObjectFieldStart(name);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#endContainer()
     */
    @Override
    public Serializer endContainer() throws IOException
    {
        this.generator.writeEndObject();
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.lang.Iterable)
     */
    @Override
    public Serializer eachComplex(final String container, final Iterable<?> elements) throws IOException
    {
        return eachComplex(container, elements.iterator(), false);
    }

    /* (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.lang.Iterable, boolean)
     */
    @Override
    public Serializer eachComplex(String container, Iterable<?> elements, boolean outputEmpty) throws IOException
    {
        return eachComplex(container, elements.iterator(), outputEmpty);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.util.Iterator)
     */
    @Override
    public Serializer eachComplex(final String container, final Iterator<?> elements) throws IOException
    {
        return eachComplex(container, elements, false);
    }

    /* (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.util.Iterator, boolean)
     */
    @Override
    public Serializer eachComplex(String container, Iterator<?> elements, boolean outputEmpty) throws IOException
    {
        if(elements.hasNext() || outputEmpty)
        {
            this.generator.writeArrayFieldStart(container);
            while(elements.hasNext())
            {
                this.generator.writeStartObject();
                writeWithAdapter(elements.next());
                this.generator.writeEndObject();
            }
            this.generator.writeEndArray();
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.lang.Iterable, se.hitta.simplerialize.SerializationAdapter)
     */
    @Override
    public Serializer eachComplex(String container, Iterable<?> elements, SerializationAdapter adapter) throws IOException
    {
        return eachComplex(container, elements.iterator(), adapter);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.util.Iterator, se.hitta.simplerialize.SerializationAdapter)
     */
    @Override
    public Serializer eachComplex(String container, Iterator<?> elements, SerializationAdapter adapter) throws IOException
    {
        if(elements.hasNext())
        {
            this.generator.writeArrayFieldStart(container);
            while(elements.hasNext())
            {
                this.generator.writeStartObject();
                adapter.write(elements.next(), this);
                this.generator.writeEndObject();
            }
            this.generator.writeEndArray();
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachPrimitive(java.lang.String, java.lang.Iterable)
     */
    @Override
    public Serializer eachPrimitive(final String container, final Iterable<?> elements) throws IOException
    {
        return eachPrimitives(container, elements.iterator());
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachPrimitives(java.lang.String, java.util.Iterator)
     */
    @Override
    public Serializer eachPrimitives(final String container, final Iterator<?> elements) throws IOException
    {
        if(elements.hasNext())
        {
            this.generator.writeArrayFieldStart(container);
            while(elements.hasNext())
            {
                writeWithAdapter(elements.next());
            }
            this.generator.writeEndArray();
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeObject(java.lang.Object)
     */
    @Override
    @Deprecated
    public Serializer writeObject(final Object target) throws IOException
    {
        this.generator.writeObject(target);
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Boolean)
     */
    @Override
    public Serializer writeNameValue(final String name, final Boolean value) throws IOException
    {
        if(value != null)
        {
            this.generator.writeBooleanField(name, value);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.CharSequence)
     */
    @Override
    public Serializer writeNameValue(final String name, final CharSequence value) throws IOException
    {
        if(value != null)
        {
            this.generator.writeStringField(name, value.toString());
        }
        return this;
    }

    /* (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNumberValue(java.lang.String, java.lang.Number)
     */
    @Override
    public <T extends Number> Serializer writeNameValue(String name, T value) throws IOException
    {
        if(value != null)
        {
            if(value instanceof Short || value instanceof Integer)
            {
                this.generator.writeNumberField(name, value.intValue());
            }
            else if(value instanceof Long)
            {
                this.generator.writeNumberField(name, value.longValue());
            }
            else if(value instanceof Float)
            {
                this.generator.writeNumberField(name, value.floatValue());
            }
            else if(value instanceof Double)
            {
                this.generator.writeNumberField(name, value.doubleValue());
            }
        }

        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, com.google.common.base.Optional)
     */
    @Override
    public Serializer writeNameValue(final String name, final com.google.common.base.Optional<?> value) throws IOException
    {
        if(value != null && value.isPresent() && value.get() != null)
        {
            this.generator.writeFieldName(name);
            writeWithAdapter(value.get());
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.util.Optional)
     */
    @Override
    public Serializer writeNameValue(final String name, final java.util.Optional<?> value) throws IOException
    {
        if(value != null && value.isPresent() && value.get() != null)
        {
            this.generator.writeFieldName(name);
            writeWithAdapter(value.get());
        }
        return this;
    }

    @Override
    public Serializer writeNullValue(String name) throws IOException {
        this.generator.writeNullField(name);
        return this;
    }

    @Override
    public Serializer eachNestedPrimitive(String container, Iterable<?> elements) throws IOException {
        return eachNestedPrimitive(container, elements.iterator());
    }

    @Override
    public Serializer eachNestedPrimitive(String container, Iterator<?> elements) throws IOException {
        if(elements.hasNext()) {
            this.generator.writeArrayFieldStart(container);
            while(elements.hasNext()) {
                final Object next = elements.next();
                if (next instanceof Iterable)
                    innerPrimitiveArray(((Iterable<?>) next).iterator());
                else
                    writeWithAdapter(next);
            }
            this.generator.writeEndArray();
        }
        return this;
    }

    protected Serializer innerPrimitiveArray(final Iterator<?> elements) throws IOException {
        if(elements.hasNext()) {
            this.generator.writeStartArray();
            while(elements.hasNext())
            {
                final Object next = elements.next();
                if (next instanceof Iterable)
                    innerPrimitiveArray(((Iterable<?>) next).iterator());
                else
                    writeWithAdapter(next);
            }
            this.generator.writeEndArray();
        }
        return this;
    }

    @Override
    public Serializer eachNestedComplex(String container, Iterable<?> elements) throws IOException {
        return eachNestedComplex(container, elements.iterator());
    }

    @Override
    public Serializer eachNestedComplex(String container, Iterator<?> elements) throws IOException {
        if(elements.hasNext()) {
            this.generator.writeArrayFieldStart(container);
            while(elements.hasNext()) {
                final Object next = elements.next();
                if (next instanceof Iterable)
                    innerComplexArray(((Iterable<?>) next).iterator());
                else {
                    this.generator.writeStartObject();
                    writeWithAdapter(next);
                    this.generator.writeEndObject();
                }
            }
            this.generator.writeEndArray();
        }
        return this;
    }

    protected Serializer innerComplexArray(final Iterator<?> elements) throws IOException {
        if(elements.hasNext()) {
            this.generator.writeStartArray();
            while(elements.hasNext())
            {
                final Object next = elements.next();
                if (next instanceof Iterable)
                    innerComplexArray(((Iterable<?>) next).iterator());
                else {
                    this.generator.writeStartObject();
                    writeWithAdapter(next);
                    this.generator.writeEndObject();
                }
            }
            this.generator.writeEndArray();
        }
        return this;
    }

}