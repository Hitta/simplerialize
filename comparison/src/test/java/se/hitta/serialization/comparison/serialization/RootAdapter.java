package se.hitta.serialization.comparison.serialization;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;

public final class RootAdapter implements SerializationAdapter<SampleObject>
{
    @Override
    public void write(final SampleObject root, final SerializationRootContext serializer) throws Exception
    {
        final SerializationContainerContext container = serializer.startContainer("root");
        container.writeRepeating("attributes", root.attributes);
        container.end();
    }
}