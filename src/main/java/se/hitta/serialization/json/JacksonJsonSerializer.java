package se.hitta.serialization.json;

import java.io.IOException;
import java.io.Writer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.adapter.AdapterMapper;

import com.natpryce.maybe.Maybe;

public final class JacksonJsonSerializer implements JsonSerializer
{
    private final JsonGenerator generator;
    private final AdapterMapper mapper;

    public JacksonJsonSerializer(final Writer writer, final AdapterMapper mapper) throws Exception
    {
        this.mapper = mapper;
        final JsonFactory factory = new JsonFactory();
        this.generator = factory.createJsonGenerator(writer);
    }

    @Override
    public void done() throws Exception
    {
        this.generator.close();
    }

    @Override
    public JsonSerializer endObject() throws JsonGenerationException, IOException
    {
        this.generator.writeEndObject();
        return this;
    }

    @Override
    public JsonSerializer startObject() throws JsonGenerationException, IOException
    {
        this.generator.writeStartObject();
        return this;
    }

    @Override
    public JsonSerializer startObject(final String name) throws JsonGenerationException, IOException
    {
        this.generator.writeObjectFieldStart(name);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Boolean value) throws Exception
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final String value) throws JsonGenerationException, IOException
    {
        this.generator.writeStringField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Long value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Double value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(String name, boolean value) throws Exception
    {
        this.generator.writeBooleanField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final int value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final double value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final float value) throws Exception
    {
        this.generator.writeNumberField(name, value);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Short value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeNumberField(name, value);
        }
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Integer value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeNumberField(name, value);
        }
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Float value) throws Exception
    {
        if(value != null)
        {
            this.generator.writeNumberField(name, value);
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonSerializer writeWithAdapter(final Object target) throws Exception
    {
        this.mapper.resolveAdapter(target.getClass()).writeJson(target, this);
        return this;
    }

    @Override
    public JsonSerializer startArray() throws Exception
    {
        this.generator.writeStartArray();
        return this;
    }

    @Override
    public JsonSerializer endArray() throws Exception
    {
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public JsonSerializer writePrimitive(final Object target) throws Exception
    {
        this.generator.writeObject(target);
        return this;
    }

    @Override
    public JsonSerializer startArray(final String name) throws Exception
    {
        this.generator.writeArrayFieldStart(name);
        return this;
    }

    @Override
    public JsonSerializer writeRepeating(final String elementName, final Iterable<?> elements) throws Exception
    {
        for(final Object entry : elements)
        {
            this.generator.writeObjectFieldStart(elementName);
            writeWithAdapter(entry);
            this.generator.writeEndObject();
        }
        return this;
    }

    @Override
    public JsonSerializer writeArray(final Iterable<?> elements) throws Exception
    {
        this.generator.writeStartArray();
        for(final Object entry : elements)
        {
            writeWithAdapter(entry);
        }
        this.generator.writeEndArray();
        return this;
    }

    @Override
    public JsonSerializer writeArrayField(final String name, final Iterable<?> elements) throws Exception
    {
        if(elements != null && elements.iterator().hasNext())
        {
            this.generator.writeFieldName(name);
            writeArray(elements);
        }
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name) throws Exception
    {
        this.generator.writeFieldName(name);
        return this;
    }

    @Override
    public JsonSerializer writeField(final String name, final Maybe<?> value) throws Exception
    {
        if(value.isKnown())
        {
            writeField(name);
            writeWithAdapter(value.value());
        }
        return this;
    }

    @Override
    public JsonSerializer writeWithAdapter(final Maybe<?> target) throws Exception
    {
        if(target.isKnown())
        {
            writeWithAdapter(target.value());
        }
        return this;
    }
}