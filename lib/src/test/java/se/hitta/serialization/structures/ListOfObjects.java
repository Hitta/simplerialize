package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class ListOfObjects extends AbstractSerializationTest
{
    @Override
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer("container");
        container.beneath("list").eachComplex(Arrays.asList(new Item(), new Item()));
        container.endContainer();
    }

    int i = 0;

    final class Item implements SerializationCapable
    {
        @Override
        public void write(final RootContext serializer) throws IOException
        {
            serializer.writeNameValue("item" + ListOfObjects.this.i++, "value");
        }
    }
}