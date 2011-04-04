package se.hitta.simplerialize.comparison.serialization;

import java.io.IOException;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;

public final class RootAdapter implements SerializationAdapter<SampleObject>
{
    @Override
    public void write(final SampleObject root, final Serializer serializer) throws IOException
    {
        serializer.startContainer("root");
        serializer.eachComplex("attributes", root.attributes.entrySet());
        serializer.endContainer();
    }
}