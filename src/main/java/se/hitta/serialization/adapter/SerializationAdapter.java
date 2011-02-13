package se.hitta.serialization.adapter;

import se.hitta.serialization.Serializer;

public interface SerializationAdapter<T>
{
    public void write(T target, Serializer serializer) throws Exception;
}