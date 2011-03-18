package se.hitta.serialization.structures;

import se.hitta.serialization.SerializationContext;

public final class SimpleWithList extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.writeWithAdapter(new Simple());
        serializer.writeWithAdapter(new List());
        serializer.endContainer();
    }
}