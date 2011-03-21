package se.hitta.serialization.structures;

import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationContext;

public final class ListOfObjects extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        final InsideContainer container = serializer.startContainer("container");
        container.beneath("list").writeRepeating(Arrays.asList(new Item(), new Item()));
        container.end();
    }

    int i = 0;

    final class Item implements SerializationCapable
    {
        @Override
        public void write(final SerializationContext serializer) throws Exception
        {
            serializer.writeNameValue("item" + ListOfObjects.this.i++, "value");
        }
    }
}