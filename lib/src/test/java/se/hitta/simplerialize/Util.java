package se.hitta.simplerialize;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.custommonkey.xmlunit.Diff;
import org.xml.sax.SAXException;

import se.hitta.simplerialize.adapters.DefaultAdapterMapper;
import se.hitta.simplerialize.implementations.CompositeSerializer;
import se.hitta.simplerialize.implementations.JacksonJsonSerializer;
import se.hitta.simplerialize.implementations.WoodstoxXmlSerializer;

public final class Util
{
    public static Serializer createCompositeSerializer() throws IOException
    {
        // For testing purposes this creates a CompositeSerializer
        // in the real world you'd choose either a XML or JSON serializer
        final AdapterMapper mapper = new DefaultAdapterMapper();
        final JacksonJsonSerializer json = new JacksonJsonSerializer(newlineOnEndWriter(), mapper);
        final WoodstoxXmlSerializer xml = new WoodstoxXmlSerializer(newlineOnEndWriter(), mapper);
        final Serializer serializer = CompositeSerializer.wrap(json, xml);
        return serializer;
    }

    public static Writer newlineOnEndWriter()
    {
        return new StringWriter()
        {
            @Override
            public void close() throws IOException
            {
                append('\n');
            }

            @Override
            public void flush()
            {
                append('\n');
            }
        };
    }

    public static final void assertXmlEquals(final String expected, final String actual) throws SAXException, IOException
    {
        final Diff diff = new Diff(expected, actual);
        if(!diff.identical() && diff.similar())
        {
            fail("similar:\n" + diff.toString());
        }
        else if(!diff.identical())
        {
            fail("expected:\n" + expected + "\nactual:\n" + actual + "\ndiff failure was:\n" + diff.toString());
        }
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

    public static final String readExpected(final Class<?> clazz, final String extension) throws IOException
    {
        final String path = constructPath(clazz, extension);
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

    private static String constructPath(final Class<?> clazz, final String extension)
    {
        final String folder = clazz.getPackage().getName().replace('.', '/');
        final String filename = clazz.getSimpleName() + extension;
        return folder + '/' + filename;
    }
}