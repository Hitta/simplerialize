package se.hitta.simplerialize.structures;

import java.io.IOException;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

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