package se.hitta.serialization.structures;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationContext;

public final class Simple extends AbstractSerializationTest
{
    @Override
    public void write(final SerializationContext serializer) throws Exception
    {
        final InsideContainer root = serializer.startContainer(getClass().getSimpleName());
        final InsideContainer element = serializer.startContainer("element");
        element.writeNameValue("with", "attribute");
        element.end();
        root.end();
    }
}