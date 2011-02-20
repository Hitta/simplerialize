package se.hitta.serialization;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import se.hitta.serialization.adapter.DefaultAdapterMapper;
import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

public final class NameValueForPrimitivesTest
{
    @Test
    public void stringNameAndStringValue() throws Exception
    {
        new SerializerAssertion()
        {
            @Override
            void writeValue(final Serializer serializer) throws Exception
            {
                serializer.writeNameValue("string", "string");
            }
        }.asJsonEquals("{\"string\": \"string\"}").asXmlEquals("string=\"string\"");
    }

    @Test
    public void stringNameAndIntegerValue() throws Exception
    {
        new SerializerAssertion()
        {
            @Override
            void writeValue(final Serializer serializer) throws Exception
            {
                serializer.writeNameValue("string", 1);
            }
        }.asJsonEquals("{\"string\": 1}").asXmlEquals("string=\"1\"");
    }

    @Test
    public void stringNameAndBooleanValue() throws Exception
    {
        new SerializerAssertion()
        {
            @Override
            void writeValue(final Serializer serializer) throws Exception
            {
                serializer.writeNameValue("string", false);
            }
        }.asJsonEquals("{\"string\": false}").asXmlEquals("string=\"false\"");
    }

    
    public SerializerAssertion expect(final Object value)
    {
        return new SerializerAssertion()
        {
            @Override
            void writeValue(final Serializer serializer) throws Exception
            {
                serializer.writePrimitive(value);
            }
        };
    }

    public abstract class SerializerAssertion
    {
        private final StringWriter jsonResult = new StringWriter();
        private final StringWriter xmlResult = new StringWriter();

        SerializerAssertion asJsonEquals(final String expected) throws Exception
        {
            final Serializer serializer = new JacksonJsonSerializer(this.jsonResult, new DefaultAdapterMapper()).start().startContainer("container");
            writeValue(serializer);
            serializer.endContainer().finish();
            assertValuesAreEqual("{\"container\":" + expected + "}", this.jsonResult.toString());
            return this;
        }

        private void assertValuesAreEqual(String expected, String actual)
        {
            expected = StringUtils.remove(expected, '\n');
            expected = StringUtils.remove(expected, '\t');
            expected = StringUtils.remove(expected, ' ');
            actual = StringUtils.remove(actual, '\n');
            actual = StringUtils.remove(actual, '\t');
            actual = StringUtils.remove(actual, ' ');
            assertEquals(expected, actual);
        }

        SerializerAssertion asXmlEquals(final String expected) throws Exception
        {
            final Serializer serializer = new WoodstoxXmlSerializer(this.xmlResult, new DefaultAdapterMapper()).start().startContainer("container");
            writeValue(serializer);
            serializer.endContainer().finish();
            assertValuesAreEqual("<?xml version='1.0' encoding='UTF-8'?><container " +expected + "/>", this.xmlResult.toString());
            return this;
        }

        abstract void writeValue(final Serializer serializer) throws Exception;
    }
}