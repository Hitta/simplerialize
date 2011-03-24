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

import java.io.Writer;
import java.util.Iterator;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;
import se.hitta.serialization.SerializationCollectionContext;

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

    public WoodstoxXmlSerializer(final Writer writer, final AdapterMapper mapper) throws Exception
    {
        super(writer, mapper);
        this.generator = factory.createXMLStreamWriter(writer);
    }

    @Override
    public SerializationRootContext start() throws Exception
    {
        this.generator.writeStartDocument();
        return this;
    }

    @Override
    public void finish() throws Exception
    {
        this.generator.writeEndDocument();
        this.generator.close();
    }

    @Override
    public SerializationContainerContext startContainer(final String name) throws XMLStreamException
    {
        this.generator.writeStartElement(name);
        return this;
    }

    @Override
    public SerializationRootContext end() throws XMLStreamException
    {
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public SerializationCollectionContext beneath(final String container) throws Exception
    {
        this.generator.writeStartElement(container);
        return new SerializationCollectionContext()
        {

            @Override
            public SerializationRootContext eachPrimitive(final Iterable<?> elements) throws Exception
            {
                eachPrimitives(elements.iterator());
                return WoodstoxXmlSerializer.this;
            }

            @Override
            public SerializationRootContext eachPrimitives(final Iterator<?> elements) throws Exception
            {
                while(elements.hasNext())
                {
                    WoodstoxXmlSerializer.this.generator.writeStartElement("value");
                    writeWithAdapter(elements.next());
                    WoodstoxXmlSerializer.this.generator.writeEndElement();
                }
                WoodstoxXmlSerializer.this.generator.writeEndElement();
                return WoodstoxXmlSerializer.this;
            }

            @Override
            public SerializationRootContext eachComplex(final Iterable<?> elements) throws Exception
            {
                eachComplex(elements.iterator());
                return WoodstoxXmlSerializer.this;
            }

            @Override
            public SerializationRootContext eachComplex(final Iterator<?> elements) throws Exception
            {
                while(elements.hasNext())
                {
                    writeWithAdapter(elements.next());
                }
                WoodstoxXmlSerializer.this.generator.writeEndElement();
                return WoodstoxXmlSerializer.this;
            }
        };
    }

    @Override
    public SerializationRootContext writeObject(final Object target) throws Exception
    {
        this.generator.writeCharacters(target.toString());
        return this;
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final String value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Boolean value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Short value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Integer value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Long value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Float value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Double value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public SerializationContainerContext writeNameValue(final String name, final Maybe<?> value) throws Exception
    {
        return writeAttribute(name, value);
    }

    public SerializationContainerContext writeAttribute(final String name, final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            writeAttribute(name, value.value());
        }
        return this;
    }

    public SerializationContainerContext writeAttribute(final String name, final Object value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeAttribute(name, value.toString());
        }
        return this;
    }
}