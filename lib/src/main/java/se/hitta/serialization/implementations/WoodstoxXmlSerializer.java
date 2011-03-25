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

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.context.CollectionContext;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

import com.ctc.wstx.api.WstxOutputProperties;
import com.natpryce.maybe.Maybe;

public final class WoodstoxXmlSerializer extends AbstractSerializer
{
    private final static XMLOutputFactory factory;
    final XMLStreamWriter generator;

    static
    {
        factory = XMLOutputFactory.newInstance();
        factory.setProperty(WstxOutputProperties.P_OUTPUT_ESCAPE_CR, true);
    }

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

    @Override
    public RootContext start() throws IOException
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

    @Override
    public void finish() throws IOException
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

    @Override
    public ContainerContext startContainer(final String name) throws IOException
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

    @Override
    public RootContext endContainer() throws IOException
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

    @Override
    public CollectionContext beneath(final String container) throws IOException
    {
        try
        {
            this.generator.writeStartElement(container);
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
        return new CollectionContext()
        {

            @Override
            public RootContext eachPrimitive(final Iterable<?> elements) throws IOException
            {
                return eachPrimitives(elements.iterator());
            }

            @Override
            public RootContext eachPrimitives(final Iterator<?> elements) throws IOException
            {
                try
                {
                    while(elements.hasNext())
                    {
                        WoodstoxXmlSerializer.this.generator.writeStartElement("value");
                        writeWithAdapter(elements.next());
                        WoodstoxXmlSerializer.this.generator.writeEndElement();
                    }
                    WoodstoxXmlSerializer.this.generator.writeEndElement();
                }
                catch(final XMLStreamException e)
                {
                    throw new IOException(e.getMessage(), e);
                }
                return WoodstoxXmlSerializer.this;
            }

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
                    writeWithAdapter(elements.next());
                }
                try
                {
                    WoodstoxXmlSerializer.this.generator.writeEndElement();
                }
                catch(final XMLStreamException e)
                {
                    throw new IOException(e.getMessage(), e);
                }
                return WoodstoxXmlSerializer.this;
            }
        };
    }

    @Override
    public RootContext writeObject(final Object target) throws IOException
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

    @Override
    public ContainerContext writeNameValue(final String name, final String value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Boolean value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Short value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Integer value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Long value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Float value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Double value) throws IOException
    {
        return writeAttribute(name, value);
    }

    @Override
    public ContainerContext writeNameValue(final String name, final Maybe<?> value) throws IOException
    {
        return writeAttribute(name, value);
    }

    public ContainerContext writeAttribute(final String name, final Maybe<?> value) throws IOException
    {
        if(value.isKnown())
        {
            writeAttribute(name, value.value());
        }
        return this;
    }

    public ContainerContext writeAttribute(final String name, final Object value) throws IOException
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