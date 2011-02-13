package se.hitta.serialization.cases;

import java.util.Arrays;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.capable.SerializationCapable;

public final class ListOfStuff extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws Exception
    {
        serializer.startContainer("container");
        serializer.writeRepeating("list", Arrays.asList(new Item(), new Item()));
        serializer.endContainer();
    }

    int i = 0;

    final class Item implements SerializationCapable
    {
        @Override
        public void write(final Serializer serializer) throws Exception
        {
            serializer.writeNameValue("item" + (ListOfStuff.this.i++), "value");
        }
    }
}