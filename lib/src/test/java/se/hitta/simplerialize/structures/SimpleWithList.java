package se.hitta.simplerialize.structures;

import java.io.IOException;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

public final class SimpleWithList extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.writeWithAdapter(new Simple());
        serializer.writeWithAdapter(new ListOfObjects());
        serializer.endContainer();
    }
}