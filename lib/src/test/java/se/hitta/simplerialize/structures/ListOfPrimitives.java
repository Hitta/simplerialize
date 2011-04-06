package se.hitta.simplerialize.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

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