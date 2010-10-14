package se.hitta.serialization.adapter;

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.XmlSerializer;

final class IterableAdapter implements Adapter<Iterable<?>>
{
    @Override
    public void writeJson(final Iterable<?> target, final JsonSerializer serializer) throws Exception
    {
        for(final Object entry : target)
        {
            serializer.writeWithAdapter(entry);
        }
    }

    @Override
    public void writeXml(final Iterable<?> target, final XmlSerializer serializer) throws Exception
    {
        for(final Object entry : target)
        {
            serializer.writeWithAdapter(entry);
        }
    }
}