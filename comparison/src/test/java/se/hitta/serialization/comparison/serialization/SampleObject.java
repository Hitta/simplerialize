package se.hitta.serialization.comparison.serialization;

import java.io.IOException;
import java.util.Map;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class SampleObject implements SerializationCapable
{
    public final Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public void write(final RootContext serializer) throws IOException
    {
        ContainerContext container = serializer.startContainer("root");
        container.beneath("attributes").eachComplex(this.attributes.entrySet());
        container.endContainer();
    }
}