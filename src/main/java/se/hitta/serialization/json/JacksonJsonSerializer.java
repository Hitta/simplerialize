package se.hitta.serialization.json;

import java.io.IOException;
import java.io.Writer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import se.hitta.serialization.AbstractSerializer;
import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapter.AdapterMapper;

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
        super(mapper);
        this.generator = factory.createJsonGenerator(writer);
        this.generator.setPrettyPrinter(new DefaultPrettyPrinter());
    }

    @Override
    public Serializer start() throws Exception
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
    public Serializer startContainer(final String name) throws JsonGenerationException, IOException
    {
        this.generator.writeObjectFieldStart(name);
        return this;
    }

    @Override
    public Serializer endContainer() throws JsonGenerationException, IOException
    {
        this.generator.writeEndObject();
        return this;
    }

    @Override
    public Serializer writeRepeating(final String elementName, final Iterable<?> elements) throws Exception
    {
        this.generator.writeArrayFieldStart(elementName);
        for(final Object entry : elements)
        {
            this.generator.writeStartObject();
            writeWithAdapter(entry);
            this.generator.writeEndObject();
        }
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public Serializer writePrimitive(final Object target) throws Exception
    {
        this.generator.writeObject(target);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Boolean value) throws Exception
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final String value) throws JsonGenerationException, IOException
    {
        this.generator.writeStringField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Long value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Double value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(String name, boolean value) throws Exception
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final int value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final double value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final float value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Short value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeNumberField(name, value);
        }
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Integer value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeNumberField(name, value);
        }
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Float value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeNumberField(name, value);
        }
        return this;
    }

    @Override
    public Serializer writeNameValue(final String name, final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            this.generator.writeFieldName(name);
            writeWithAdapter(value.value());
        }
        return this;
    }
}