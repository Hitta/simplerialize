package se.hitta.serialization.adapter;

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.XmlSerializer;

final class IterableAdapter implements SerializationAdapter<Iterable<?>>
{
    @Override
    public void write(final Iterable<?> target, final JsonSerializer serializer) throws Exception
    {
        for(final Object entry : target)
        {
            serializer.writeWithAdapter(entry);
        }
    }

    @Override
    public void write(final Iterable<?> target, final XmlSerializer serializer) throws Exception
    {
        for(final Object entry : target)
        {
            serializer.writeWithAdapter(entry);
        }
    }
}