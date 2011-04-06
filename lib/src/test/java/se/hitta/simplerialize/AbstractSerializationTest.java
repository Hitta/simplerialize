package se.hitta.simplerialize;

import java.io.IOException;
import java.io.StringWriter;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

import se.hitta.simplerialize.adapters.DefaultAdapterMapper;
import se.hitta.simplerialize.implementations.JacksonJsonSerializer;
import se.hitta.simplerialize.implementations.WoodstoxXmlSerializer;

public abstract class AbstractSerializationTest implements SerializationCapable
{
    private StringWriter result;

    @Before
    public void init()
    {
        this.result = new StringWriter();
    }

    @Test
    public final void asXml() throws Exception
    {
        XMLUnit.setIgnoreWhitespace(true);
        Util.assertXmlEquals(readExpectedXml(), serialize(new WoodstoxXmlSerializer(this.result, createMapper())));
    }

    @Test
    public final void asJson() throws Exception
    {
        Util.assertJsonEquals(readExpectedJson(), serialize(new JacksonJsonSerializer(this.result, createMapper())));
    }

    private String serialize(final Serializer serializer) throws IOException
    {
        serializer.start();
        serializer.writeWithAdapter(AbstractSerializationTest.class, this);
        serializer.close();
        return this.result.toString();
    }

    // Testclasses may override to configure the mapper
    public AdapterMapper createMapper()
    {
        return new DefaultAdapterMapper();
    }

    public final String readExpectedXml() throws IOException
    {
        return Util.readExpected(getClass(), ".xml");
    }

    public final String readExpectedJson() throws IOException
    {
        return Util.readExpected(getClass(), ".json");
    }
}