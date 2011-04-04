package se.hitta.simplerialize.comparison.jaxb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.Serializer;

@XmlRootElement(name = "root")
public final class SampleObject implements SerializationCapable
{
    @XmlJavaTypeAdapter(AttributesAdaptor.class)
    public final Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("root");
        serializer.eachComplex("attributes", this.attributes.entrySet());
        serializer.endContainer();
    }

    // JAXB cruft
    private SampleObject()
    {
        this.attributes = new HashMap<String, String>();
    }
}