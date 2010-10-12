package se.hitta.serialization.json;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.Serializer;

public class JacksonJsonSerializerTest
{
    @Test
    public void intProperty() throws Exception
    {
        this.expected = "{\"ett\":1}";
        this.serializer.write("ett", 1);
    }

    @Test
    public void stringProperty() throws Exception
    {
        this.expected = "{\"ett\":\"två\"}";
        this.serializer.write("ett", "två");
    }

    @Test
    public void booleanProperty() throws Exception
    {
        this.expected = "{\"ett\":true}";
        this.serializer.write("ett", true);
    }

    @Test
    public void doubleProperty() throws Exception
    {
        this.expected = "{\"ett\":1.2}";
        this.serializer.write("ett", 1.2);
    }

    @Before
    public void init() throws Exception
    {
        this.writer = new StringWriter();
        this.serializer = new JacksonJsonSerializer(new AdapterMapper(), new PrintWriter(this.writer));
        this.serializer.writeStructureStart(WRAPPER_OBJECT_NAME);
    }

    @After
    public void verify() throws Exception
    {
        this.serializer.writeStructureEnd();
        this.serializer.done();
        assertEquals("{\"" + WRAPPER_OBJECT_NAME + "\":" + this.expected + "}", this.writer.toString());
    }

    private String expected;
    private Serializer serializer;
    private StringWriter writer;
    private static final String WRAPPER_OBJECT_NAME = "test";
}