package se.hitta.serialization.adapters;

import java.io.IOException;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;

final class NumberAdapter implements SerializationAdapter<Number>
{
    @SuppressWarnings("deprecation")
    @Override
    public void write(final Number target, final Serializer serializer) throws IOException
    {
        serializer.writeObject(target);
    }
}