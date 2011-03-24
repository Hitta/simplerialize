package se.hitta.serialization;

import java.io.StringWriter;
import java.util.Arrays;

import org.junit.Test;

import se.hitta.serialization.adapters.DefaultAdapterMapper;
import se.hitta.serialization.implementations.JacksonJsonSerializer;

public final class ApiDemo
{
    @Test
    public void api() throws Exception
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        final StringWriter writer = new StringWriter();
        final Serializer json = new JacksonJsonSerializer(writer, mapper);
        SerializationRootContext context = json.start();
        {
            context.beneath("objects").eachComplex(Arrays.asList(new AnObject(), new AnObject()));
            context.beneath("primitives").eachPrimitive(Arrays.asList("1", "2", "3"));
        }
        json.finish();
        System.err.println(writer.toString());
    }

    final class AnObject implements SerializationCapable
    {
        @Override
        public void write(final SerializationRootContext context) throws Exception
        {
            context.writeNameValue(getClass().getSimpleName(), "hello");
        }
    }
}