package se.hitta.simplerialize.adapters;

import java.io.IOException;

import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

final class BooleanAdapter implements SerializationAdapter<Boolean>
{
    @SuppressWarnings("deprecation")
    @Override
    public void write(final Boolean target, final Serializer serializer) throws IOException
    {
        serializer.writeObject(target);
    }
}