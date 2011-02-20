package se.hitta.serialization.cases;

import se.hitta.serialization.Serializer;

public final class Simple extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws Exception
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.startContainer("element").writeNameValue("with", "attribute").endContainer();
        serializer.endContainer();
    }
}