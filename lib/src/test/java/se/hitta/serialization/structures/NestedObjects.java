package se.hitta.serialization.structures;

import java.io.IOException;
import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class NestedObjects extends AbstractSerializationTest
{
    @Override
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext container = serializer.startContainer("container");
        serializer.writeWithAdapter(new First());
        container.endContainer();
    }

    static final class First implements SerializationCapable
    {
        private final Second second = new Second();

        @Override
        public void write(final RootContext serializer) throws IOException
        {
            final ContainerContext container = serializer.startContainer("first");
            serializer.writeWithAdapter(this.second);
            container.endContainer();
        }
    }

    static final class Second implements SerializationCapable
    {
        @Override
        public void write(final RootContext serializer) throws IOException
        {
            final ContainerContext container = serializer.startContainer("second");
            serializer.beneath("third").eachComplex(Arrays.asList(new Third(), new Third()));
            container.endContainer();
        }
    }

    static final class Third implements SerializationCapable
    {
        private static int i = 0;

        @Override
        public void write(final RootContext serializer) throws IOException
        {
            serializer.writeNameValue("item" + (i++), "value");
        }
    }
}