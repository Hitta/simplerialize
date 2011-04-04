package se.hitta.serialization.structures;

import java.io.IOException;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.Serializer;

public final class Simple extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.startContainer("element");
        serializer.writeNameValue("with", "attribute");
        serializer.endContainer();
        serializer.endContainer();
    }
}