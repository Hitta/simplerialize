package se.hitta.serialization.comparison.serialization;

import java.util.Map;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;

public final class SampleObject implements SerializationCapable
{
    public final Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public void write(final SerializationRootContext serializer) throws Exception
    {
        SerializationContainerContext container = serializer.startContainer("root");
        container.writeRepeating("attributes", this.attributes);
        container.end();
    }
}