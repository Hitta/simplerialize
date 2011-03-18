package se.hitta.serialization.structures;

import java.util.Arrays;

import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationContext;

public final class NestedObjects extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        serializer.startContainer("container").writeWithAdapter(new First()).endContainer();
    }

    static final class First implements SerializationCapable
    {
        private final Second second = new Second();

        @Override
        public void write(final SerializationContext serializer) throws Exception
        {
            serializer.startContainer("first").writeWithAdapter(this.second).endContainer();
        }
    }

    static final class Second implements SerializationCapable
    {
        @Override
        public void write(final SerializationContext serializer) throws Exception
        {
            serializer.startContainer("second");
            serializer.writeRepeating("third", Arrays.asList(new Third(), new Third()));
            serializer.endContainer();
        }
    }

    static final class Third implements SerializationCapable
    {
        private static int i = 0;

        @Override
        public void write(SerializationContext serializer) throws Exception
        {
            serializer.writeNameValue("item" + (i++), "value");
        }
    }
}