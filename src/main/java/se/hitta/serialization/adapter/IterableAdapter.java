package se.hitta.serialization.adapter;

import se.hitta.serialization.Serializer;

final class IterableAdapter implements SerializationAdapter<Iterable<?>>
{
    @Override
    public void write(final Iterable<?> target, final Serializer serializer) throws Exception
    {
        for(final Object entry : target)
        {
            serializer.writeWithAdapter(entry);
        }
    }
}