package se.hitta.simplerialize.adapters;

import java.io.IOException;

import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

final class CharSequenceAdapter implements SerializationAdapter<CharSequence>
{
    @SuppressWarnings("deprecation")
    @Override
    public void write(final CharSequence target, final Serializer serializer) throws IOException
    {
        serializer.writeObject(target);
    }
}