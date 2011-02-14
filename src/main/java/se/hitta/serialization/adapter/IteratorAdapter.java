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
            /* 
             * FIXME:
             * 
             * Primitives are written as object. All other types are encapsulated
             * to produce valid JSON arrays.
             * 
             * This hack clearly illustrates that I'm in over my head with
             * the level of abstraction. However this works and I'll let ii remain
             * until I either wise up or get help with finding out where my
             * implementation / abstraction is broken.
             * 
             * 
             * Mårten - 2011-02-14
             */
            final Object value = target.next();
            if(isPrimitive(value))
            {
                serializer.writeObject(value);
            }
            else
            {
                serializer.start().writeWithAdapter(value).endContainer();
            }
        }
    }

    private boolean isPrimitive(final Object value)
    {
        return value instanceof CharSequence || value instanceof Number || value instanceof Boolean;
    }
}