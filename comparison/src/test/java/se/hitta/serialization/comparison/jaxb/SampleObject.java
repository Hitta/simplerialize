package se.hitta.serialization.comparison.jaxb;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationContext;

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
    public void write(final SerializationContext serializer) throws Exception
    {
        serializer.startContainer("root").writeRepeating("attributes", this.attributes).endContainer();
    }

    // JAXB cruft
    private SampleObject()
    {
        this.attributes = new HashMap<String, String>();
    }
}