package se.hitta.serialization.comparison.jaxb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

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
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer("root");
        container.beneath("attributes").eachComplex(this.attributes.entrySet());
        container.endContainer();
    }

    // JAXB cruft
    private SampleObject()
    {
        this.attributes = new HashMap<String, String>();
    }
}