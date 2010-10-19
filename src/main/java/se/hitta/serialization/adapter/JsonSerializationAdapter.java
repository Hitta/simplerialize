package se.hitta.serialization.adapter;

import se.hitta.serialization.JsonSerializer;

public interface JsonSerializationAdapter<T>
{
    void writeJson(T target, JsonSerializer serializer) throws Exception;
}