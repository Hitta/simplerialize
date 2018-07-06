package se.hitta.simplerialize;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import se.hitta.simplerialize.adapters.DefaultAdapterMapper;
import se.hitta.simplerialize.implementations.JacksonJsonSerializer;

public class GenericTypesTest
{
    @Test
    public void canRegisterAdapterForGenericType()
    {
        AdapterMapper mapper = new DefaultAdapterMapper();

        mapper.register(new StringCollectionAdapter(), new TypeReference<Collection<String>>(){});

        SerializationAdapter<Collection<String>> adapter = mapper.resolveAdapter(new TypeReference<Collection<String>>(){});
        assertNotNull(adapter);
    }

    @Test(expected=IllegalStateException.class)
    public void requestForUnregisteredGenericTypeShouldThrowException()
    {
        AdapterMapper mapper = new DefaultAdapterMapper();

        mapper.register(new StringCollectionAdapter(), new TypeReference<Collection<String>>(){});

        mapper.resolveAdapter(new TypeReference<Collection<Double>>(){});
    }

    @Test
    public void canSerializeUsingAdapterForGenericType() throws IOException
    {
        String expected = "{\"strings\":{\"string\":[\"nisse\",\"kalle\",\"åsa\"]}}";

        Collection<String> strings = new ArrayList<String>();
        strings.add("nisse");
        strings.add("kalle");
        strings.add("åsa");

        AdapterMapper mapper = new DefaultAdapterMapper();

        mapper.register(new StringCollectionAdapter(), new TypeReference<Collection<String>>(){});

        Writer stringwriWriter = new StringWriter();

        Serializer serializer = new JacksonJsonSerializer(stringwriWriter, mapper);

        serializer.start();
        serializer.writeWithAdapter(strings, new TypeReference<Collection<String>>(){});
        serializer.close();

        assertEquals(expected, stringwriWriter.toString());

    }

    public static class StringCollectionAdapter implements SerializationAdapter<Collection<String>>
    {

        @Override
        public void write(Collection<String> strings, Serializer serializer) throws IOException
        {
            serializer.startContainer("strings");
            {
                serializer.eachPrimitive("string", strings);
            }
            serializer.endContainer();
        }

    }

}
