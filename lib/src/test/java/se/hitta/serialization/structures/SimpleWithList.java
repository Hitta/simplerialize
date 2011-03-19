package se.hitta.serialization.structures;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationContext;

public final class SimpleWithList extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        final InsideContainer container = serializer.startContainer(getClass().getSimpleName());
        serializer.writeWithAdapter(new Simple());
        serializer.writeWithAdapter(new ListOfObjects());
        container.end();
    }
}