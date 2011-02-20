package se.hitta.serialization.comparison.serialization;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapter.SerializationAdapter;

public final class RootAdapter implements SerializationAdapter<SampleObject>
{
    @Override
    public void write(final SampleObject root, final Serializer serializer) throws Exception
    {
        serializer.startContainer("root");
        serializer.writeRepeating("attributes", root.attributes);
        serializer.endContainer();
    }
}