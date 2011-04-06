package se.hitta.simplerialize.adapters;

import java.io.IOException;

import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

final class NumberAdapter implements SerializationAdapter<Number>
{
    @SuppressWarnings("deprecation")
    @Override
    public void write(final Number target, final Serializer serializer) throws IOException
    {
        serializer.writeObject(target);
    }
}