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
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationContext;
import se.hitta.serialization.SerializationEachContext;

import com.ctc.wstx.api.WstxOutputProperties;
import com.natpryce.maybe.Maybe;

public final class WoodstoxXmlSerializer extends AbstractSerializer
{
    private final XMLStreamWriter generator;
    private final static XMLOutputFactory factory;

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
    public SerializationContext start() throws Exception
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
    public InsideContainer startContainer(final String name) throws XMLStreamException
    {
        this.generator.writeStartElement(name);
        return this;
    }

    @Override
    public SerializationContext end() throws XMLStreamException
    {
        this.generator.writeEndElement();
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
            writeWithAdapter(elements.next());
        }
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public SerializationEachContext beneath(final String container) throws Exception
    {
        this.generator.writeStartElement(container);
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
            this.generator.writeStartElement(name);
            writeWithAdapter(elements.next());
            this.generator.writeEndElement();
        }
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public SerializationContext writeObject(final Object target) throws Exception
    {
        this.generator.writeCharacters(target.toString());
        return this;
    }

    @Override
    public InsideContainer writeNameValue(final String name, final String value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Boolean value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Short value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Integer value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Long value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Float value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Double value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public InsideContainer writeNameValue(final String name, final Maybe<?> value) throws Exception
    {
        return writeAttribute(name, value);
    }

    public InsideContainer writeAttribute(final String name, final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            writeAttribute(name, value.value());
        }
        return this;
    }

    public InsideContainer writeAttribute(final String name, final Object value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeAttribute(name, value.toString());
        }
        return this;
    }
}