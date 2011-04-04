package se.hitta.serialization.comparison.serialization;

import java.io.IOException;
import java.util.Map;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.Serializer;

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