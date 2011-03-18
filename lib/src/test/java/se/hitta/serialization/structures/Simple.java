package se.hitta.serialization.structures;

import se.hitta.serialization.SerializationContext;

public final class Simple extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.startContainer("element").writeNameValue("with", "attribute").endContainer();
        serializer.endContainer();
    }
}