package se.hitta.simplerialize.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.SerializationCapable;
import se.hitta.simplerialize.Serializer;

public final class NestedObjects extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer("container");
        serializer.writeWithAdapter(new First());
        serializer.endContainer();
    }

    static final class First implements SerializationCapable
    {
        private final Second second = new Second();

        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.startContainer("first");
            serializer.writeWithAdapter(this.second);
            serializer.endContainer();
        }
    }

    static final class Second implements SerializationCapable
    {
        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.startContainer("second");
            serializer.eachComplex("third", Arrays.asList(new Third(), new Third()));
            serializer.endContainer();
        }
    }

    static final class Third implements SerializationCapable
    {
        private static int i = 0;

        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.writeNameValue("item" + (i++), "value");
        }
    }
}