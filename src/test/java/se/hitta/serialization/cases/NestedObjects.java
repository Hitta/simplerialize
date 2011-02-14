package se.hitta.serialization.cases;

import java.util.Arrays;
import java.util.Iterator;

import se.hitta.serialization.Serializer;
import se.hitta.serialization.capable.SerializationCapable;

public final class NestedObjects extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws Exception
    {
        serializer.startContainer("container").writeWithAdapter(new First()).endContainer();
    }

    static final class First implements SerializationCapable
    {
        private final Second second = new Second();

        @Override
        public void write(final Serializer serializer) throws Exception
        {
            serializer.startContainer("first").writeWithAdapter(this.second).endContainer();
        }
    }

    static final class Second implements SerializationCapable
    {
        @Override
        public void write(final Serializer serializer) throws Exception
        {
            serializer.startContainer("second").writeRepeating("stuff", new aBunchOfStuff()).endContainer();
        }
    }

    static final class aBunchOfStuff implements Iterable<String>
    {
        @Override
        public Iterator<String> iterator()
        {
            return Arrays.asList("a", "b").iterator();
        }
    }
}