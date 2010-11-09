package se.hitta.serialization.adapter;

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.XmlSerializer;

final class ObjectAdapter implements SerializationAdapter<Object>
{
    @Override
    public void write(final Object target, final JsonSerializer serializer) throws Exception
    {
        serializer.writePrimitive(target);
    }

    @Override
    public void write(final Object target, final XmlSerializer serializer) throws Exception
    {
        serializer.writePrimitive(target);
    }
}