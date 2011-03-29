package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Collections;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class EmptyListOfObjects extends AbstractSerializationTest
{
    @Override
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer("container");
        container.beneath("list").eachComplex(Collections.emptyList());
        container.endContainer();
    }
}