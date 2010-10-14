package se.hitta.serialization.json;

import java.io.IOException;
import java.io.Writer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.Serializer;

public final class JacksonJsonSerializer extends Serializer
{

    private final JsonGenerator generator;

    public JacksonJsonSerializer(final AdapterMapper mapper, final Writer writer) throws IOException
    {
        super(mapper);
        final JsonFactory factory = new JsonFactory();
        this.generator = factory.createJsonGenerator(writer);
    }

    @Override
    public Serializer write(final String value) throws Exception
    {
        this.generator.writeString(value);
        return this;
    }

    @Override
    public Serializer write(final String name, final String value) throws IOException
    {
        this.generator.writeStringField(name, value);
        return this;
    }

    @Override
    public Serializer write(final String name, final boolean value) throws IOException
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public Serializer write(final String name, final double value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer write(final String name, final int value) throws IOException
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer writeStructureStart(final String value) throws IOException
    {
        this.generator.writeStartObject();
        this.generator.writeObjectFieldStart(value);
        return this;
    }

    @Override
    public Serializer writeStructureEnd() throws IOException
    {
        this.generator.writeEndObject();
        return this;
    }

    @Override
    public Serializer done() throws IOException
    {
        this.generator.close();
        return this;
    }

    @Override
    public Serializer writeArrayStart(final String name) throws Exception
    {
        this.generator.writeArrayFieldStart(name);
        return this;
    }

    @Override
    public Serializer writeArrayEnd() throws Exception
    {
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public Serializer write(int value) throws Exception
    {
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer write(double value) throws Exception
    {
        this.generator.writeNumber(value);
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
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer write(String name, Integer value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }
}