package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.Serializer;

public final class ListOfObjects extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("container");
        serializer.eachComplex("list", Arrays.asList(new Item(), new Item()));
        serializer.endContainer();
    }

    int i = 0;

    final class Item implements SerializationCapable
    {
        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.writeNameValue("item" + ListOfObjects.this.i++, "value");
        }
    }
}