package se.hitta.serialization.structures;

import java.io.IOException;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class SimpleWithList extends AbstractSerializationTest
{
    @Override
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer(getClass().getSimpleName());
        serializer.writeWithAdapter(new Simple());
        serializer.writeWithAdapter(new ListOfObjects());
        container.endContainer();
    }
}