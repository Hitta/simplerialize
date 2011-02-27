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
public class PerformanceTest
{
    static final int iterations = 2000;

    @Test
    public void timeXml() throws Exception
    {
        System.err.println("\n= = = = = = = = = = = = timing XML for " + iterations + " repetitions  = = = = = = = = = = = =");
        time(new XmlDelegate(new JaxbTest()));
        time(new XmlDelegate(new SerializeWithEmbeddedAdapterTest()));
        time(new XmlDelegate(new SerializeWithStandaloneAdapterTest()));
        time(new XmlDelegate(new SimpleTest()));
        System.err.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    @Test
    public void timeJson() throws Exception
    {
        System.err.println("\n= = = = = = = = = = = = timing JSON for " + iterations + " repetitions  = = = = = = = = = = = =");
        time(new JsonDelegate(new JaxbTest()));
        time(new JsonDelegate(new SerializeWithEmbeddedAdapterTest()));
        time(new JsonDelegate(new SerializeWithStandaloneAdapterTest()));
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
}