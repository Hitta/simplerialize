package se.hitta.serialization.comparison.serialization;

import java.util.Map;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationContext;

public final class SampleObject implements SerializationCapable
{
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
}