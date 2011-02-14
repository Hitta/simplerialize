package se.hitta.serialization.adapter;

import se.hitta.serialization.Serializer;

final class PrimitiveAdapters
{
    public PrimitiveAdapters(final AdapterMapper mapper)
    {
        mapper.register(new SerializationAdapter<String>()
        {
            @Override
            public void write(final String target, final Serializer serializer) throws Exception
            {
                serializer.writePrimitive(target);
            }
        }, String.class);
        mapper.register(new SerializationAdapter<Number>()
        {
            @Override
            public void write(final Number target, final Serializer serializer) throws Exception
            {
                serializer.writeObject(target);
            }
        }, Number.class, Float.class, Integer.class, Short.class, Long.class);
        mapper.register(new SerializationAdapter<Byte>()
        {
            @Override
            public void write(final Byte target, final Serializer serializer) throws Exception
            {
                serializer.writeObject(target);
            }
        }, Byte.class);
        mapper.register(new SerializationAdapter<Boolean>()
        {
            @Override
            public void write(final Boolean target, final Serializer serializer) throws Exception
            {
                serializer.writePrimitive(target);
            }
        }, Boolean.class);
    }
}