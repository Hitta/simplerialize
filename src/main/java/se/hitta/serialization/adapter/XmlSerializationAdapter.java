package se.hitta.serialization.adapter;

import se.hitta.serialization.XmlSerializer;

public interface XmlSerializationAdapter<T>
{
    void write(T target, XmlSerializer serializer) throws Exception;
}