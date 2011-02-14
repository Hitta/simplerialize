package se.hitta.serialization.cases;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.adapter.DefaultAdapterMapper;
import se.hitta.serialization.adapter.SerializationAdapter;
import se.hitta.serialization.capable.SerializationCapable;
import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

public abstract class AbstractSerializationTest implements SerializationCapable
{
    @Test
    public final void asXml() throws Exception
    {
        final StringWriter writer = new StringWriter();
        final AdapterMapper mapper = new DefaultAdapterMapper();
        final Serializer serializer = new WoodstoxXmlSerializer(writer, mapper).start();
        final SerializationAdapter<AbstractSerializationTest> adapter = mapper.resolveAdapter(AbstractSerializationTest.class);
        adapter.write(this, serializer);
        serializer.finish();
        // Assert
        XMLUnit.setIgnoreWhitespace(true);
        final String expected = readExpectedXml();
        final Diff diff = new Diff(expected, writer.toString());
        if(!diff.identical() && diff.similar())
        {
            System.err.println("Similar:\n" + diff.toString());
        }
        else if(!diff.identical())
        {
            fail("expected:\n" + expected + "\nactual:\n" + writer.toString() + "\ndiff failure was:\n" + diff.toString());
        }
    }

    private String readExpectedXml() throws IOException
    {
        final InputStream is = getClass().getClassLoader().getResourceAsStream(getClass().getSimpleName() + ".xml");
        final byte[] raw = new byte[is.available()];
        is.read(raw);
        is.close();
        final String expected = new String(raw);
        return expected;
    }

    @Test
    public final void asJson() throws Exception
    {
        final StringWriter writer = new StringWriter();
        final AdapterMapper mapper = new DefaultAdapterMapper();
        final Serializer serializer = new JacksonJsonSerializer(writer, mapper).start();
        SerializationAdapter<AbstractSerializationTest> adapter = mapper.resolveAdapter(AbstractSerializationTest.class);
        adapter.write(this, serializer);
        serializer.finish();
        final ObjectMapper m = new ObjectMapper();
        final JsonNode expected = readExpectedJson(m);
        final JsonNode actual = m.readTree(writer.toString());
        if(!expected.equals(actual))
        {
            fail("expected:\n" + expected.toString() + "\nactual:\n" + actual.toString());
        }
    }

    private JsonNode readExpectedJson(final ObjectMapper m)
    {
        try
        {
            final InputStream is = getClass().getClassLoader().getResourceAsStream(getClass().getSimpleName() + ".json");
            final JsonNode expected = m.readTree(is);
            is.close();
            return expected;
        }
        catch(Exception e)
        {
            fail("Failed to expected JSON: " + e.getMessage());
            return null;
        }
    }
}