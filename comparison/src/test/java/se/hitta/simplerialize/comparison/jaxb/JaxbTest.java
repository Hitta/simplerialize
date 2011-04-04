package se.hitta.simplerialize.comparison.jaxb;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import se.hitta.simplerialize.comparison.AbstractTest;

public final class JaxbTest extends AbstractTest
{

    @Override
    public void serializeXmlTo(final Writer writer) throws Exception
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

    @Override
    public void serializeJsonTo(final Writer writer) throws Exception
    {
        final ObjectMapper mapper = new ObjectMapper();
        final AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
        mapper.getSerializationConfig().setAnnotationIntrospector(introspector);
        mapper.writeValue(writer, createRoot());
    }
}