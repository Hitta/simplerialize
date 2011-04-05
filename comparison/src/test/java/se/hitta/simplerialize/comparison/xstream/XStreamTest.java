package se.hitta.simplerialize.comparison.xstream;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import se.hitta.simplerialize.comparison.AbstractTest;
import se.hitta.simplerialize.comparison.simple.SampleObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public final class XStreamTest extends AbstractTest
{
    @Override
    public void serializeXmlTo(final Writer writer) throws Exception
    {
        final XStream xstream = new XStream();
        xstream.alias("root", SampleObject.class);
        xstream.toXML(createRoot(), writer);
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
    {
        final XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("root", SampleObject.class);
        xstream.toXML(createRoot(), writer);
    }
}