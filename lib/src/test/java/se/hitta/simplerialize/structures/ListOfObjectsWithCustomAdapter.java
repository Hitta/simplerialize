package se.hitta.simplerialize.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

public final class ListOfObjectsWithCustomAdapter extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("container");
        {
            serializer.eachComplex("item", Arrays.asList(new Item(0), new Item(1)), new SerializationAdapter<Item>() {

                @Override
                public void write(Item item, Serializer serializer) throws IOException {
                    serializer.writeNameValue("name", "value" + item.number);
                }
            });
        }
        serializer.endContainer();
    }

    final class Item
    {
        public final int number;

        public Item(final int number)
        {
            this.number = number;
        }
    }
}