package se.hitta.simplerialize.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.SerializationCapable;
import se.hitta.simplerialize.Serializer;

public final class ListOfObjects extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("container");
        {
            serializer.eachComplex("item", Arrays.asList(new Item(0), new Item(1)));
        }
        serializer.endContainer();
    }

    final class Item implements SerializationCapable
    {
        private final int number;

        public Item(final int number)
        {
            this.number = number;
        }

        @Override
        public void write(final Serializer serializer) throws IOException
        {
        	serializer.writeNameValue("name", "value" + this.number);
        }
    }
}