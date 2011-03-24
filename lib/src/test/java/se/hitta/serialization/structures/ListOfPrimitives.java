package se.hitta.serialization.structures;

import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;

public final class ListOfPrimitives extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationRootContext serializer) throws Exception
    {
        final SerializationContainerContext container = serializer.startContainer("container");
        serializer.beneath("items").eachPrimitive(Arrays.asList("foo", "bar"));
        container.end();
    }
}