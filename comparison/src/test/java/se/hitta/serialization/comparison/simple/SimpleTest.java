package se.hitta.serialization.comparison.simple;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import se.hitta.serialization.comparison.AbstractTest;

public final class SimpleTest extends AbstractTest
{
    @Override
    public void serializeXmlTo(final Writer writer) throws Exception
    {
        Serializer serializer = new Persister();
        serializer.write(createRoot(), writer);
    }

    private SampleObject createRoot()
    {
        final Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("one", "1");
        attributes.put("two", "2");
        attributes.put("three", "3");
        return new SampleObject(attributes);
    }

    @Override
    public void serializeJsonTo(final Writer writer) throws Exception
    {}
}