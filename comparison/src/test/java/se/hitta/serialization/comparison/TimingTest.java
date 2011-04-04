package se.hitta.serialization.comparison;

import java.io.Writer;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import se.hitta.serialization.comparison.jaxb.JaxbTest;
import se.hitta.serialization.comparison.serialization.SerializeWithEmbeddedAdapterTest;
import se.hitta.serialization.comparison.serialization.SerializeWithStandaloneAdapterTest;
import se.hitta.serialization.comparison.simple.SimpleTest;

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
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    @Test
    public void timeJson() throws Exception
    {

        System.err.println("\n= = = = = = = = = = = = timing JSON for " + iterations + " repetitions  = = = = = = = = = = = =");
        report(new JsonDelegate(new JaxbTest()));
        report(new JsonDelegate(new SerializeWithEmbeddedAdapterTest()));
        report(new JsonDelegate(new SerializeWithStandaloneAdapterTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    public void report(final Delegate delegate) throws Exception
    {
        // Warm up with one third of the to be timed iterations
        iterate(iterations / 3, delegate);
        // Perform and report
        final StopWatch timer = new StopWatch();
        timer.start();
        iterate(iterations, delegate);
        timer.stop();
        System.err.println(timer.getTime() + "ms\t" + delegate);
    }

    public void iterate(final int iterations, final Delegate d) throws Exception
    {
        for(int i = 0; i < iterations; i++)
        {
            d.execute();
        }
    }
}