package se.hitta.serialization.structures;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;

public final class Simple extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationRootContext serializer) throws Exception
    {
        final SerializationContainerContext root = serializer.startContainer(getClass().getSimpleName());
        final SerializationContainerContext element = serializer.startContainer("element");
        element.writeNameValue("with", "attribute");
        element.end();
        root.end();
    }
}