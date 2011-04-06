package se.hitta.simplerialize;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public final class ApiDemo
{
    @Test
    @SuppressWarnings("unchecked")
    public void nestedObjects() throws Exception
    {
        final Serializer serializer = Util.createCompositeSerializer();
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
        serializer.close();
        System.err.println(getClass().getSimpleName() + ": nested objects");
        serializer.printTo(System.err);
    }

    @Test
    public void mapEntries() throws Exception
    {
        final Map<String, Object> m = new HashMap<String, Object>();
        m.put("a", 1);
        m.put("x", false);
        final Serializer serializer = Util.createCompositeSerializer();
        serializer.start().eachComplex("entry", m.entrySet()).flush();
        System.err.println(getClass().getSimpleName() + ": map entries");
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
}