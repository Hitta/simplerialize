package se.hitta.serialization.comparison;

import java.io.IOException;
import java.io.Writer;

import org.junit.Test;

import se.hitta.serialization.comparison.jaxb.JaxbTest;
import se.hitta.serialization.comparison.serialization.SerializeWithEmbeddedAdapterTest;
import se.hitta.serialization.comparison.serialization.SerializeWithStandaloneAdapterTest;

/**
 * Performs serialization to a {@link Writer} that only counts the amount of bytes written.
 */
public class SizeTest
{

    @Test
    public void size() throws Exception
    {
        System.err.println("= = = = = = = = = = = = = = size report = = = = = = = = = = = = = =");
        measure(new Delegate(new JaxbTest()));
        measure(new Delegate(new SerializeWithEmbeddedAdapterTest()));
        measure(new Delegate(new SerializeWithStandaloneAdapterTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    public void measure(final Delegate d) throws Exception
    {
        d.execute();
        System.err.println(d);
    }

    public static final class Delegate
    {
        int bytes = 0;
        private final PerformanceTestable target;

        public Delegate(final PerformanceTestable target)
        {
            this.target = target;
        }

        public void execute() throws Exception
        {
            this.target.serializeTo(new Writer()
            {
                @Override
                public void write(char[] cbuf, int off, int len) throws IOException
                {
                    Delegate.this.bytes += len;
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
            return this.bytes + " bytes\t" + this.target.getClass().getSimpleName();
        }
    }
}