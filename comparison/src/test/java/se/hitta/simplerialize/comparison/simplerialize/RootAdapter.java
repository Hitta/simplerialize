package se.hitta.simplerialize.comparison.simplerialize;

import java.io.IOException;

import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;
import se.hitta.simplerialize.comparison.SampleObject;

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