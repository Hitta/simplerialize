package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class ListOfPrimitives extends AbstractSerializationTest
{
    @Override
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer("container");
        serializer.beneath("items").eachPrimitive(Arrays.asList("foo", "bar"));
        container.endContainer();
    }
}