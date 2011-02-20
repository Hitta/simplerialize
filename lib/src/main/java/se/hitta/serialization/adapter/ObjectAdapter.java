package se.hitta.serialization.adapter;

import se.hitta.serialization.Serializer;

final class ObjectAdapter implements SerializationAdapter<Object>
{
    @Override
    public void write(final Object target, final Serializer serializer) throws Exception
    {
        serializer.writePrimitive(target);
    }
}