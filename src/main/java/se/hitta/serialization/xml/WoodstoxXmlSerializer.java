package se.hitta.serialization.xml;

import java.io.IOException;
import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

import org.codehaus.stax2.XMLStreamWriter2;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.Serializer;

public final class WoodstoxXmlSerializer extends Serializer
{

    private final XMLStreamWriter2 generator;

    public WoodstoxXmlSerializer(final AdapterMapper mapper, final Writer writer) throws Exception
    {
        super(mapper);
        final XMLOutputFactory xof = XMLOutputFactory.newInstance();
        try
        {
            this.generator = (XMLStreamWriter2)xof.createXMLStreamWriter(writer);
            this.generator.writeStartDocument("UTF-8", "1.0");
        }
        catch(final XMLStreamException e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public Serializer write(final String value) throws Exception
    {
        this.generator.writeRaw(value);
        return this;
    }

    @Override
    public Serializer write(final String name, final String value) throws Exception
    {
        if(value == null)
        {
            this.generator.writeEmptyElement(name);
        }
        else
        {
            this.generator.writeStartElement(name);
            this.generator.writeRaw(value);
            this.generator.writeEndElement();
        }
        return this;
    }

    @Override
    public Serializer write(final String name, final boolean value) throws Exception
    {
        this.generator.writeStartElement(name);
        this.generator.writeBoolean(value);
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public Serializer write(final String name, final double value) throws Exception
    {
        this.generator.writeStartElement(name);
        this.generator.writeDouble(value);
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public Serializer write(final String name, final int value) throws Exception
    {
        this.generator.writeStartElement(name);
        this.generator.writeInt(value);
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public Serializer writeStructureStart(String value) throws Exception
    {
        this.generator.writeStartElement(value);
        return this;
    }

    @Override
    public Serializer writeStructureEnd() throws Exception
    {
        this.generator.writeEndElement();
        return this;
    }

    @Override
    public Serializer done() throws Exception
    {
        this.generator.writeEndDocument();
        return this;
    }

    @Override
    public Serializer writeArrayStart(String name) throws Exception
    {
        return writeStructureStart(name);
    }

    @Override
    public Serializer writeArrayEnd() throws Exception
    {
        return writeStructureEnd();
    }


    @Override
    public Serializer write(int value) throws Exception
    {
        this.generator.writeInt(value);
        return this;
    }

    @Override
    public Serializer write(double value) throws Exception
    {
        this.generator.writeDouble(value);
        return this;
    }

    @Override
    public Serializer write(boolean value) throws Exception
    {
        this.generator.writeBoolean(value);
        return this;
    }

    @Override
    public Serializer write(final Integer value) throws Exception
    {
        this.generator.writeInt(value);
        return this;
    }

    @Override
    public Serializer write(String name, Integer value) throws Exception
    {
        this.generator.writeStartElement(name);
        this.generator.writeInt(value);
        this.generator.writeEndElement();
       return this;
    }
}