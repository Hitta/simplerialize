package se.hitta.serialization.comparison.serialization;

import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.SerializationContext;

public final class RootAdapter implements SerializationAdapter<SampleObject>
{
    @Override
    public void write(final SampleObject root, final SerializationContext serializer) throws Exception
    {
        final InsideContainer container = serializer.startContainer("root");
        container.writeRepeating("attributes", root.attributes);
        container.end();
    }
}