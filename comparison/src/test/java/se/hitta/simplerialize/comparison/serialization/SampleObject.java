package se.hitta.simplerialize.comparison.serialization;

import java.io.IOException;
import java.util.Map;

import se.hitta.simplerialize.SerializationCapable;
import se.hitta.simplerialize.Serializer;

public final class SampleObject implements SerializationCapable
{
    public final Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("root");
        serializer.eachComplex("attributes", this.attributes.entrySet());
        serializer.endContainer();
    }
}