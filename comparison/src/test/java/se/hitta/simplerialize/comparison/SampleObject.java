package se.hitta.simplerialize.comparison;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import se.hitta.simplerialize.SerializationCapable;
import se.hitta.simplerialize.Serializer;
import se.hitta.simplerialize.comparison.jaxb.AttributesAdaptor;

/**
 * The sample object used to compare sizing ad timing of the different
 * libraries. XStream and Simplerializer (in the case of an external adapter)
 * both doesn't require anything from the object to be serialized. 
 */
// JAXB
@XmlRootElement(name = "root")
// Simple
@Root(name = "root")
public final class SampleObject implements SerializationCapable
{
    // JAXB
    @XmlJavaTypeAdapter(AttributesAdaptor.class)
    // Simple, also simple doesn't seem to allow us to mark the field as final
    @ElementMap(key = "key", value = "value", attribute = true, inline = true)
    public Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    // JAXB
    private SampleObject()
    {
        this.attributes = new HashMap<String, String>();
    }

    // Simplerializer: inline adapter approach
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("root");
        serializer.eachComplex("attributes", this.attributes.entrySet());
        serializer.endContainer();
    }
}