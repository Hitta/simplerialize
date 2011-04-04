package se.hitta.serialization;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import se.hitta.serialization.adapters.DefaultAdapterMapper;
import se.hitta.serialization.implementations.CompositeSerializer;
import se.hitta.serialization.implementations.JacksonJsonSerializer;
import se.hitta.serialization.implementations.WoodstoxXmlSerializer;

public final class ApiDemo
{
    @Test
    @SuppressWarnings("unchecked")
    public void apiDemo() throws Exception
    {
        final Serializer serializer = configure();
        serializer.start();
        serializer.startContainer("root");
        {
            serializer.eachComplex("objects", Arrays.asList(new Stuff(), new Stuff()));
            serializer.eachPrimitive("primitives", Arrays.asList("1", 1, true));
            serializer.startContainer("a");
            {
                serializer.startContainer("b");
                {
                    serializer.writeNameValue("name", "value");
                }
                serializer.endContainer();
            }
            serializer.endContainer();
        }
        serializer.endContainer();
        serializer.finish();
        serializer.printTo(System.err);
    }

    private Serializer configure() throws Exception
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        final JacksonJsonSerializer json = new JacksonJsonSerializer(new NewlineAtEndWriter(), mapper);
        final WoodstoxXmlSerializer xml = new WoodstoxXmlSerializer(new NewlineAtEndWriter(), mapper);
        return CompositeSerializer.wrap(json, xml);
    }

    @Test
    public void mapDemo() throws Exception
    {
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("a", 1);
        m.put("x", false);
        final Serializer serializer = configure();
        serializer.start().eachComplex("entry", m.entrySet()).finish();
        serializer.printTo(System.err);
    }

    final class Stuff implements SerializationCapable
    {
        @Override
        public void write(final Serializer context) throws IOException
        {
            context.writeNameValue(getClass().getSimpleName(), "hello");
        }
    }

    static final class NewlineAtEndWriter extends StringWriter
    {
        @Override
        public String toString()
        {
            return super.toString() + '\n';
        }
    }
}