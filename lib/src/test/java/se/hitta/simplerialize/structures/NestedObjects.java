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
            serializer.eachComplex("third", Arrays.asList(new Third(0), new Third(1)));
            serializer.endContainer();
        }
    }

    static final class Third implements SerializationCapable
    {
        private final int number;

        public Third(final int number)
        {
            this.number = number;
        }

        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.writeNameValue("item" + this.number, "value");
        }
    }
}