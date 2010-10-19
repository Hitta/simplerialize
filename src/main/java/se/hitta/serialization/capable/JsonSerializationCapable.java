package se.hitta.serialization.capable;

import se.hitta.serialization.JsonSerializer;

public interface JsonSerializationCapable<T>
{
    void write(JsonSerializer serializer) throws Exception;
}
