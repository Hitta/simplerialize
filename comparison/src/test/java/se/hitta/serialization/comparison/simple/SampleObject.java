package se.hitta.serialization.comparison.simple;

import java.util.Map;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.capable.SerializationCapable;

@Root
public final class SampleObject implements SerializationCapable
{
    @ElementMap(key = "key", value = "value", attribute = true, inline = true)
    public final Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public void write(final Serializer serializer) throws Exception
    {
        serializer.startContainer("root").writeRepeating("attributes", this.attributes).endContainer();
    }
}