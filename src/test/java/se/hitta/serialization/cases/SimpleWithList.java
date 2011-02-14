package se.hitta.serialization.cases;

import se.hitta.serialization.Serializer;

public final class SimpleWithList extends AbstractSerializationTest
{
    @Override
    public void write(final Serializer serializer) throws Exception
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.writeWithAdapter(new Simple());
        serializer.writeWithAdapter(new List());
        serializer.endContainer();
    }
}