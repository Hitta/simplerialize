package se.hitta.serialization.comparison.serialization;

import java.io.IOException;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class RootAdapter implements SerializationAdapter<SampleObject>
{
    @Override
    public void write(final SampleObject root, final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer("root");
        container.beneath("attributes").eachComplex(root.attributes.entrySet());
        container.endContainer();
    }
}