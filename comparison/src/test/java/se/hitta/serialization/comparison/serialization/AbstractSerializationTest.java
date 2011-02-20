package se.hitta.serialization.comparison.serialization;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.comparison.PerformanceTestable;
import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

public abstract class AbstractSerializationTest implements PerformanceTestable
{
    public final SampleObject createRoot()
    {
        final Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("one", "1");
        attributes.put("two", "2");
        attributes.put("three", "3");
        return new SampleObject(attributes);
    }

    @Test
    public final void serialize() throws Exception
    {
        final StringWriter w = new StringWriter();
        serializeTo(w);
        System.err.println(w.toString());
    }

    public abstract AdapterMapper getMapper();

    @Override
    public void serializeTo(final Writer writer) throws Exception
    {
        final Serializer serializer = new WoodstoxXmlSerializer(writer, getMapper());
        serializer.start().writeWithAdapter(createRoot()).finish();
    }

    @Test
    public void asJson() throws Exception
    {
        final StringWriter w = new StringWriter();
        final Serializer serializer = new JacksonJsonSerializer(w, getMapper());
        serializer.start().writeWithAdapter(createRoot()).finish();
        System.err.println(w.toString());
    }
}