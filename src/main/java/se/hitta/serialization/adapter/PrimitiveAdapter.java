package se.hitta.serialization.adapter;

import se.hitta.serialization.Serializer;

final class PrimitiveAdapter implements SerializationAdapter<Object>
{
    @Override
    public void write(final Object target, final Serializer serializer) throws Exception
    {
        serializer.writeNameValue("foo", target.toString());
        //serializer.writePrimitive(target);
    }
}