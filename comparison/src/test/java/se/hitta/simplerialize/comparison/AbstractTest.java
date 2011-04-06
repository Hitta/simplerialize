package se.hitta.simplerialize.comparison;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

public abstract class AbstractTest
{

    @Test
    public final void asXml() throws Exception
    {
        System.err.println("\nSerializing XML - " + getClass().getSimpleName() + ":");
        serializeXmlTo(new OutputStreamWriter(System.err));
    }

    public abstract void serializeXmlTo(final Writer writer) throws Exception;

    @Test
    public final void asJson() throws Exception
    {
        System.err.println("\nSerializing JSON - " + getClass().getSimpleName() + ":");
        final StringWriter writer = new StringWriter();
        serializeJsonTo(writer);
        System.err.println(writer.toString());
    }

    public abstract void serializeJsonTo(final Writer writer) throws Exception;
}
