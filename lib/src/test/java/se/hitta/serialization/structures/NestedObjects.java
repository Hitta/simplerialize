package se.hitta.serialization.structures;

import java.util.Arrays;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationCapable;
import se.hitta.serialization.SerializationRootContext;

public final class NestedObjects extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationRootContext serializer) throws Exception
    {
        final SerializationContainerContext container = serializer.startContainer("container");
        serializer.writeWithAdapter(new First());
        container.end();
    }

    static final class First implements SerializationCapable
    {
        private final Second second = new Second();

        @Override
        public void write(final SerializationRootContext serializer) throws Exception
        {
            final SerializationContainerContext container = serializer.startContainer("first");
            serializer.writeWithAdapter(this.second);
            container.end();
        }
    }

    static final class Second implements SerializationCapable
    {
        @Override
        public void write(final SerializationRootContext serializer) throws Exception
        {
            final SerializationContainerContext container = serializer.startContainer("second");
            serializer.beneath("third").eachComplex(Arrays.asList(new Third(), new Third()));
            container.end();
        }
    }

    static final class Third implements SerializationCapable
    {
        private static int i = 0;

        @Override
        public void write(final SerializationRootContext serializer) throws Exception
        {
            serializer.writeNameValue("item" + (i++), "value");
        }
    }
}