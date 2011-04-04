package se.hitta.serialization;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.xml.sax.SAXException;

import se.hitta.serialization.adapters.DefaultAdapterMapper;
import se.hitta.serialization.implementations.JacksonJsonSerializer;
import se.hitta.serialization.implementations.WoodstoxXmlSerializer;

public abstract class AbstractSerializationTest implements SerializationCapable
{
    @Test
    public final void asXml() throws Exception
    {
        final StringWriter actual = new StringWriter();
        final AdapterMapper mapper = createMapper();
        final Serializer serializer = new WoodstoxXmlSerializer(actual, mapper);
        final SerializationAdapter<AbstractSerializationTest> adapter = mapper.resolveAdapter(AbstractSerializationTest.class);
        serializer.start();
        adapter.write(this, serializer);
        serializer.finish();
        XMLUnit.setIgnoreWhitespace(true);
        assertXmlEquals(readExpectedXml(), actual.toString());
    }

    @Test
    public final void asJson() throws Exception
    {
        final StringWriter actual = new StringWriter();
        final AdapterMapper mapper = createMapper();
        final Serializer serializer = new JacksonJsonSerializer(actual, mapper);
        SerializationAdapter<AbstractSerializationTest> adapter = mapper.resolveAdapter(AbstractSerializationTest.class);
        serializer.start();
        adapter.write(this, serializer);
        serializer.finish();
        assertJsonEquals(readExpectedJson(), actual.toString());
    }

    public static final void assertJsonEquals(final String expected, final String actual) throws IOException, JsonProcessingException
    {
        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actualJson = mapper.readTree(actual);
        final JsonNode expectedJson = mapper.readTree(expected);
        if(!expectedJson.equals(actualJson))
        {
            fail("expected:\n" + expected + "\nactual:\n" + actual);
        }
    }
    
    public AdapterMapper createMapper()
    {
        return new DefaultAdapterMapper();
    }

    public final String readExpectedJson() throws IOException
    {
        return readExpected(".json");
    }

    public static final void assertXmlEquals(final String expected, final String actual) throws SAXException, IOException
    {
        final Diff diff = new Diff(expected, actual);
        if(!diff.identical() && diff.similar())
        {
            System.err.println("Similar:\n" + diff.toString());
        }
        else if(!diff.identical())
        {
            fail("expected:\n" + expected + "\nactual:\n" + actual + "\ndiff failure was:\n" + diff.toString());
        }
    }

    public final String readExpectedXml() throws IOException
    {
        return readExpected(".xml");
    }

    private final String readExpected(final String extension) throws IOException
    {
        final Class<?> clazz = getClass();
        final String folder = clazz.getPackage().getName().replace('.', '/');
        final String filename = clazz.getSimpleName() + extension;
        final String path = folder + '/' + filename;
        final InputStream is = clazz.getClassLoader().getResourceAsStream(path);
        if(is == null)
        {
            throw new FileNotFoundException(path);
        }
        final byte[] raw = new byte[is.available()];
        is.read(raw);
        is.close();
        return new String(raw);
    }
}