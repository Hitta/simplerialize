package se.hitta.serialization.comparison.serialization;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.SerializationContext;

public final class RootAdapter implements SerializationAdapter<SampleObject>
{
    @Override
    public void write(final SampleObject root, final SerializationContext serializer) throws Exception
    {
        serializer.startContainer("root");
        serializer.writeRepeating("attributes", root.attributes);
        serializer.endContainer();
    }
}