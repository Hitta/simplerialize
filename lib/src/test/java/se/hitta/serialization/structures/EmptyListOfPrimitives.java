package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Collections;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.Serializer;

public final class EmptyListOfPrimitives extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("container");
        serializer.eachPrimitive("items", Collections.emptyList());
        serializer.endContainer();
    }
}