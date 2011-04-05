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

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.Serializer;

import com.ctc.wstx.api.WstxOutputProperties;
import com.natpryce.maybe.Maybe;

/**
 *
 */
public final class WoodstoxXmlSerializer extends AbstractSerializer
{
    /*
     * Had only XMLStreamException been a subclass of IOException...
     */

    private final static XMLOutputFactory factory;
    private final XMLStreamWriter generator;

    static
    {
        factory = XMLOutputFactory.newInstance();
        factory.setProperty(WstxOutputProperties.P_OUTPUT_ESCAPE_CR, true);
    }

    /**
     * 
     * @param stream
     * @param mapper
     * @throws IOException
     */
    public WoodstoxXmlSerializer(final OutputStream stream, final AdapterMapper mapper) throws IOException
    {
        this(new OutputStreamWriter(stream), mapper);
    }

    /**
     * 
     * @param writer
     * @param mapper
     * @throws IOException
     */
    public WoodstoxXmlSerializer(final Writer writer, final AdapterMapper mapper) throws IOException
    {
        super(writer, mapper);
        try
        {
            this.generator = factory.createXMLStreamWriter(writer);
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#start()
     */
    @Override
    public Serializer start() throws IOException
    {
        try
        {
            this.generator.writeStartDocument();
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    @Override
    public void close() throws IOException
    {
        try
        {
            this.generator.writeEndDocument();
            this.generator.close();
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.io.Flushable#flush()
     */
    @Override
    public void flush() throws IOException
    {
        try
        {
            this.generator.flush();
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#startContainer(java.lang.String)
     */
    @Override
    public Serializer startContainer(final String name) throws IOException
    {
        try
        {
            this.generator.writeStartElement(name);
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#endContainer()
     */
    @Override
    public Serializer endContainer() throws IOException
    {
        try
        {
            this.generator.writeEndElement();
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
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
            try
            {
                this.generator.writeStartElement(container);
                while(elements.hasNext())
                {
                    this.generator.writeStartElement("value");
                    writeWithAdapter(elements.next());
                    this.generator.writeEndElement();
                }
                this.generator.writeEndElement();
            }
            catch(final XMLStreamException e)
            {
                throw new IOException(e.getMessage(), e);
            }
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.lang.Iterable)
     */
    @Override
    public Serializer eachComplex(final String container, final Iterable<?> elements) throws IOException
    {
        return eachComplex(container, elements.iterator());
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#eachComplex(java.lang.String, java.util.Iterator)
     */
    @Override
    public Serializer eachComplex(final String container, final Iterator<?> elements) throws IOException
    {
        if(elements.hasNext())
        {
            try
            {
                this.generator.writeStartElement(container);
                while(elements.hasNext())
                {
                    writeWithAdapter(elements.next());
                }
                this.generator.writeEndElement();
            }
            catch(final XMLStreamException e)
            {
                throw new IOException(e.getMessage(), e);
            }
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeObject(java.lang.Object)
     */
    @Override
    public Serializer writeObject(final Object target) throws IOException
    {
        try
        {
            this.generator.writeCharacters(target.toString());
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
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
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Boolean)
     */
    @Override
    public Serializer writeNameValue(final String name, final Boolean value) throws IOException
    {
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Short)
     */
    @Override
    public Serializer writeNameValue(final String name, final Short value) throws IOException
    {
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Integer)
     */
    @Override
    public Serializer writeNameValue(final String name, final Integer value) throws IOException
    {
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Long)
     */
    @Override
    public Serializer writeNameValue(final String name, final Long value) throws IOException
    {
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Float)
     */
    @Override
    public Serializer writeNameValue(final String name, final Float value) throws IOException
    {
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, java.lang.Double)
     */
    @Override
    public Serializer writeNameValue(final String name, final Double value) throws IOException
    {
        return writeAttribute(name, value);
    }

    /*
     * (non-Javadoc)
     * @see se.hitta.simplerialize.Serializer#writeNameValue(java.lang.String, com.natpryce.maybe.Maybe)
     */
    @Override
    public Serializer writeNameValue(final String name, final Maybe<?> value) throws IOException
    {
        return writeAttribute(name, value);
    }

    private Serializer writeAttribute(final String name, final Maybe<?> value) throws IOException
    {
        if(value.isKnown())
        {
            writeAttribute(name, value.value());
        }
        return this;
    }

    private Serializer writeAttribute(final String name, final Object value) throws IOException
    {
        if(value != null)
        {
            try
            {
                this.generator.writeAttribute(name, value.toString());
            }
            catch(final XMLStreamException e)
            {
                throw new IOException(e.getMessage(), e);
            }
        }
        return this;
    }
}