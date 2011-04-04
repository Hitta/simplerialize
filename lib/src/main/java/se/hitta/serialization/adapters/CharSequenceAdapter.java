package se.hitta.serialization.adapters;

import java.io.IOException;

import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;

final class CharSequenceAdapter implements SerializationAdapter<CharSequence>
{
    @SuppressWarnings("deprecation")
    @Override
    public void write(final CharSequence target, final Serializer serializer) throws IOException
    {
        serializer.writeObject(target);
    }
}