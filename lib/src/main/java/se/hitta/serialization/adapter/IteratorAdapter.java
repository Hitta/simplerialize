package se.hitta.serialization.adapter;

import java.util.Iterator;

import se.hitta.serialization.Serializer;

final class IteratorAdapter implements SerializationAdapter<Iterator<?>>
{
    @Override
    public void write(final Iterator<?> target, final Serializer serializer) throws Exception
    {
        while(target.hasNext())
        {
            serializer.writeWithAdapter(target.next());
        }
    }
}