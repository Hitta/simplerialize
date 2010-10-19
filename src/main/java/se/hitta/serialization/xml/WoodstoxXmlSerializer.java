package se.hitta.serialization.xml;

import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import se.hitta.serialization.XmlSerializer;
import se.hitta.serialization.adapter.AdapterMapper;

import com.natpryce.maybe.Maybe;

public final class WoodstoxXmlSerializer implements XmlSerializer
{
    private final XMLStreamWriter generator;
    private final AdapterMapper mapper;

    public WoodstoxXmlSerializer(final Writer writer, final AdapterMapper mapper) throws Exception
    {
        this.mapper = mapper;
        final XMLOutputFactory factory = XMLOutputFactory.newInstance();
        this.generator = factory.createXMLStreamWriter(writer);
    }

    @Override
    public void done() throws Exception
    {
        this.generator.writeEndDocument();
        this.generator.close();
    }

    @Override
    public XmlSerializer startElement(String name) throws XMLStreamException
    {
        this.generator.writeStartElement(name);
        return this;
    }

    @Override
    public XmlSerializer endElement() throws XMLStreamException
    {
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public XmlSerializer startDocument() throws XMLStreamException
    {
        this.generator.writeStartDocument("UTF-8", "1.0");
        return this;
    }

    @Override
    public XmlSerializer writeAttribute(String name, Object value) throws Exception
    {
        this.generator.writeAttribute(name, value == null ? null : value.toString());
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public XmlSerializer writeWithAdapter(Object target) throws Exception
    {
        this.mapper.resolveAdapter(target.getClass()).writeXml(target, this);
        return this;
    }

    @Override
    public XmlSerializer writeElement(String name, String content) throws Exception
    {
        this.generator.writeStartElement(name);
        this.generator.writeCharacters(content);
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public XmlSerializer writeRepeating(String elementName, Iterable<?> elements) throws Exception
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
    public XmlSerializer writePrimitive(Object target) throws Exception
    {
        this.generator.writeCharacters(target.toString());
        return this;
    }

    @Override
    public XmlSerializer writeAttribute(String name, Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            writeAttribute(name, value.value());
        }
        return this;
    }

    @Override
    public XmlSerializer writeWithAdapter(Maybe<?> target) throws Exception
    {
        if(target.isKnown())
        {
            writeWithAdapter(target.value());
        }
        return this;
    }

}