package se.hitta.serialization.comparison.jaxb;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import se.hitta.serialization.comparison.PerformanceTestable;

public final class JaxbTest implements PerformanceTestable
{
    @Test
    public void serialize() throws Exception
    {
        serializeTo(new OutputStreamWriter(System.err));
    }

    @Override
    public void serializeTo(final Writer writer) throws Exception
    {
        final JAXBContext context = JAXBContext.newInstance(SampleObject.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(createRoot(), writer);
    }

    private SampleObject createRoot()
    {
        final Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("one", "1");
        attributes.put("two", "2");
        attributes.put("three", "3");
        return new SampleObject(attributes);
    }

}