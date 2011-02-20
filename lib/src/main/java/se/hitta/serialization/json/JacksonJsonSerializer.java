package se.hitta.serialization.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

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
        writeRepeating(elementName, elements.iterator());
        return this;
    }
    
    @Override
    public Serializer writeRepeating(final String elementName, final Iterator<?> elements) throws Exception
    {
        this.generator.writeArrayFieldStart(elementName);
        writeWithAdapter(elements);
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public Serializer writeObject(final Object target) throws Exception
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

    @Override
    public Serializer writePrimitive(final String value) throws Exception
    {
        this.generator.writeString(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Boolean value) throws Exception
    {
        this.generator.writeBoolean(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Short value) throws Exception
    {
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Integer value) throws Exception
    {
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Long value) throws Exception
    {
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Float value) throws Exception
    {
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Double value) throws Exception
    {
        this.generator.writeNumber(value);
        return this;
    }

    @Override
    public Serializer writePrimitive(final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            writeObject(value.value());
        }
        return this;
    }
}