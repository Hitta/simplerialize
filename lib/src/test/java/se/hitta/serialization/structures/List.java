package se.hitta.serialization.structures;

import java.util.Arrays;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationContext;

public final class List extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        serializer.startContainer("container");
        serializer.writeRepeating("list", Arrays.asList(new Item(), new Item()));
        serializer.endContainer();
    }

    int i = 0;

    final class Item implements SerializationCapable
    {
        @Override
        public void write(final SerializationContext serializer) throws Exception
        {
            serializer.writeNameValue("item" + List.this.i++, "value");
        }
    }
}