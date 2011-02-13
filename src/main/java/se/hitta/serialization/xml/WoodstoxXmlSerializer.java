package se.hitta.serialization.xml;

import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import se.hitta.serialization.AbstractSerializer;
import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapter.AdapterMapper;

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
        super(mapper);
        this.generator = factory.createXMLStreamWriter(writer);
    }

    @Override
    public Serializer start() throws Exception
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
    public Serializer startContainer(final String name) throws XMLStreamException
    {
        this.generator.writeStartElement(name);
        return this;
    }

    @Override
    public Serializer endContainer() throws XMLStreamException
    {
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public Serializer writeRepeating(final String elementName, final Iterable<?> elements) throws Exception
    {
        for(final Object entry : elements)
        {
            this.generator.writeStartElement(elementName);
            writeWithAdapter(entry);
            this.generator.writeEndElement();
        }
        return this;
    }

    @Override
    public Serializer writePrimitive(final Object target) throws Exception
    {
        this.generator.writeCharacters(target.toString());
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final String value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Boolean value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Short value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Integer value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Long value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Float value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Double value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final boolean value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final int value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final double value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final float value) throws Exception
    {
        return writeAttribute(name, value);
    }

    @Override
    public Serializer writeNameValue(final String name, final Maybe<?> value) throws Exception
    {
        return writeAttribute(name, value);
    }

    public Serializer writeAttribute(final String name, final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            writeAttribute(name, value.value());
        }
        return this;
    }

    public Serializer writeAttribute(final String name, final Object value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeAttribute(name, value.toString());
        }
        return this;
    }
}