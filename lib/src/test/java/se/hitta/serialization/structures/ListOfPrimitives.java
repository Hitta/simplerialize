package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.Serializer;

public final class ListOfPrimitives extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("container");
        serializer.eachPrimitive("items", Arrays.asList("foo", "bar"));
        serializer.endContainer();
    }
}