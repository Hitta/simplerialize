package se.hitta.simplerialize.comparison;

import java.io.Writer;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import se.hitta.simplerialize.comparison.jaxb.JaxbTest;
import se.hitta.simplerialize.comparison.serialization.SerializeWithEmbeddedAdapterTest;
import se.hitta.simplerialize.comparison.serialization.SerializeWithStandaloneAdapterTest;
import se.hitta.simplerialize.comparison.simple.SimpleTest;
import se.hitta.simplerialize.comparison.xstream.XStreamTest;

/**
 * Time serialization to a no-op {@link Writer}
 */
public class TimingTest
{
    static final int iterations = 2000;

    @Test
    public void timeXml() throws Exception
    {
        System.err.println("\n= = = = = = = = = = = = timing XML for " + iterations + " repetitions  = = = = = = = = = = = =");
        report(new XmlDelegate(new JaxbTest()));
        report(new XmlDelegate(new SerializeWithEmbeddedAdapterTest()));
        report(new XmlDelegate(new SerializeWithStandaloneAdapterTest()));
        report(new XmlDelegate(new SimpleTest()));
        report(new XmlDelegate(new XStreamTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    @Test
    public void timeJson() throws Exception
    {

        System.err.println("\n= = = = = = = = = = = = timing JSON for " + iterations + " repetitions  = = = = = = = = = = = =");
        report(new JsonDelegate(new JaxbTest()));
        report(new JsonDelegate(new SerializeWithEmbeddedAdapterTest()));
        report(new JsonDelegate(new SerializeWithStandaloneAdapterTest()));
        report(new JsonDelegate(new XStreamTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    public void report(final Delegate delegate) throws Exception
    {
        // Warm up with one third of the to be timed iterations
        iterate(iterations / 3, delegate);
        // Perform and report
        final long start = System.nanoTime();
        iterate(iterations, delegate);
        final long stop = System.nanoTime();
        System.err.println(TimeUnit.NANOSECONDS.toMillis(stop - start) + "ms\t" + delegate);
    }

    public void iterate(final int iterations, final Delegate d) throws Exception
    {
        for(int i = 0; i < iterations; i++)
        {
            d.execute();
        }
    }
}