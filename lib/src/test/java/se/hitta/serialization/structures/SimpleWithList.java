package se.hitta.serialization.structures;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;

public final class SimpleWithList extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationRootContext serializer) throws Exception
    {
        final SerializationContainerContext container = serializer.startContainer(getClass().getSimpleName());
        serializer.writeWithAdapter(new Simple());
        serializer.writeWithAdapter(new ListOfObjects());
        container.end();
    }
}