package se.hitta.serialization;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import se.hitta.serialization.adapters.DefaultAdapterMapper;
import se.hitta.serialization.context.RootContext;
import se.hitta.serialization.implementations.JacksonJsonSerializer;

public final class ApiDemo
{

    @Test
    public void x() throws Exception
    {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("a", 1);
        m.put("x", false);
        final Serializer json = configure();

        RootContext root = json.start();
        root.beneath("apa").eachComplex(m.entrySet());
        json.finish();
        System.err.println(json.getWriter().toString());
    }

    @Test
    public void api() throws Exception
    {
        final Serializer json = configure();
        serializeSomeData(json);
        System.err.println(json.getWriter().toString());
    }

    @SuppressWarnings("unchecked")
    private void serializeSomeData(final Serializer json) throws Exception
    {
        RootContext context = json.start();
        {
            context.beneath("objects").eachComplex(Arrays.asList(new AnObject(), new AnObject()));
            context.beneath("primitives").eachPrimitive(Arrays.asList("1", 1, true));
            context.startContainer("a").startContainer("b").writeNameValue("name", "value");
        }
        json.finish();
    }

    private Serializer configure() throws Exception
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        return new JacksonJsonSerializer(new StringWriter(), mapper);
    }

    final class AnObject implements SerializationCapable
    {
        @Override
        public void write(final RootContext context) throws IOException
        {
            context.writeNameValue(getClass().getSimpleName(), "hello");
        }
    }
}