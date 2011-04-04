package se.hitta.serialization.adapters;

import java.io.IOException;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;

final class BooleanAdapter implements SerializationAdapter<Boolean>
{
    @SuppressWarnings("deprecation")
    @Override
    public void write(final Boolean target, final Serializer serializer) throws IOException
    {
        serializer.writeObject(target);
    }
}