package se.hitta.serialization.comparison.serialization;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.comparison.AbstractTest;
import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

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
        serializer.start().writeWithAdapter(createRoot()).finish();
    }

    @Override
    public void serializeJsonTo(final Writer writer) throws Exception
    {
        final Serializer serializer = new JacksonJsonSerializer(writer, getMapper());
        serializer.start().writeWithAdapter(createRoot()).finish();
    }
}