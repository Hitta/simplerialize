package se.hitta.serialization.structures;

import java.io.IOException;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class Simple extends AbstractSerializationTest
{
    @Override
    public void write(final RootContext serializer) throws IOException
    {
        final ContainerContext root = serializer.startContainer(getClass().getSimpleName());
        final ContainerContext element = serializer.startContainer("element");
        element.writeNameValue("with", "attribute");
        element.endContainer();
        root.endContainer();
    }
}