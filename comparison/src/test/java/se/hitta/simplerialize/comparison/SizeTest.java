package se.hitta.simplerialize.comparison;

import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

import se.hitta.simplerialize.comparison.jaxb.JaxbTest;
import se.hitta.simplerialize.comparison.serialization.SerializeWithEmbeddedAdapterTest;
import se.hitta.simplerialize.comparison.serialization.SerializeWithStandaloneAdapterTest;
import se.hitta.simplerialize.comparison.simple.SimpleTest;

/**
 * Performs serialization to a {@link Writer} that only counts the amount of bytes written.
 */
public class SizeTest
{

    @Test
    public void size() throws Exception
    {
        System.err.println("\n= = = = = = = = = = = = = = size report = = = = = = = = = = = = = =");
        measure(new Delegate(new JaxbTest()));
        measure(new Delegate(new SerializeWithEmbeddedAdapterTest()));
        measure(new Delegate(new SerializeWithStandaloneAdapterTest()));
        measure(new Delegate(new SimpleTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    public void measure(final Delegate d) throws Exception
    {
        d.execute();
        System.err.println(d);
    }

    public static final class Delegate
    {
        int xmlBytes = 0;
        int jsonBytes = 0;
        private final AbstractTest target;

        public Delegate(final AbstractTest target)
        {
            this.target = target;
        }

        public void execute() throws Exception
        {
            this.target.serializeXmlTo(new Writer()
            {
                @Override
                public void write(char[] cbuf, int off, int len) throws IOException
                {
                    Delegate.this.xmlBytes += len;
                }

                @Override
                public void flush() throws IOException
                {}

                @Override
                public void close() throws IOException
                {}
            });
            this.target.serializeJsonTo(new Writer()
            {
                @Override
                public void write(char[] cbuf, int off, int len) throws IOException
                {
                    Delegate.this.jsonBytes += len;
                }

                @Override
                public void flush() throws IOException
                {}

                @Override
                public void close() throws IOException
                {}
            });
        }

        @Override
        public final String toString()
        {
            return this.target.getClass().getSimpleName() + "\nXML:\t" + this.xmlBytes + "\tbytes\nJSON:\t" + this.jsonBytes + "\tbytes\n";
        }
    }
}