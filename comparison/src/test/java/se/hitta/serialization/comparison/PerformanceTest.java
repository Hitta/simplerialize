package se.hitta.serialization.comparison;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import se.hitta.serialization.comparison.jaxb.JaxbTest;
import se.hitta.serialization.comparison.serialization.SerializeWithEmbeddedAdapterTest;
import se.hitta.serialization.comparison.serialization.SerializeWithStandaloneAdapterTest;

/**
 * Time serialization to a no-op {@link Writer}
 */
public class PerformanceTest
{
    static final int iterations = 2000;

    @Test
    public void timings() throws Exception
    {
        System.err.println("= = = = = = = = = = = = timing report for " + iterations + " repetitions  = = = = = = = = = = = =");
        time(new Delegate(new JaxbTest()));
        time(new Delegate(new SerializeWithEmbeddedAdapterTest()));
        time(new Delegate(new SerializeWithStandaloneAdapterTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    public void time(final Delegate d) throws Exception
    {
        final StopWatch timer = new StopWatch();
        timer.start();
        for(int i = 0; i < iterations; i++)
        {
            d.execute();
        }
        timer.stop();
        System.err.println(timer.getTime() + "ms\t" + d);
    }

    public static final class Delegate
    {
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
                {}

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
            return this.target.getClass().getSimpleName();
        }
    }
}