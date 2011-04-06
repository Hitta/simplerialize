package se.hitta.simplerialize.comparison.simplerialize;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.Serializer;
import se.hitta.simplerialize.comparison.AbstractTest;
import se.hitta.simplerialize.comparison.SampleObject;
import se.hitta.simplerialize.implementations.JacksonJsonSerializer;
import se.hitta.simplerialize.implementations.WoodstoxXmlSerializer;

public abstract class AbstractSerializationTest extends AbstractTest
{
    public final SampleObject createRoot()
    {
        final Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("one", "1");
        attributes.put("two", "2");
        attributes.put("three", "3");
        return new SampleObject(attributes);
    }

    public abstract AdapterMapper getMapper();

    @Override
    public void serializeXmlTo(final Writer writer) throws Exception
    {
        final Serializer serializer = new WoodstoxXmlSerializer(writer, getMapper());
        serializer.start().writeWithAdapter(createRoot()).close();
    }

    @Override
    public void serializeJsonTo(final Writer writer) throws Exception
    {
        final Serializer serializer = new JacksonJsonSerializer(writer, getMapper());
        serializer.start().writeWithAdapter(createRoot()).close();
    }
}