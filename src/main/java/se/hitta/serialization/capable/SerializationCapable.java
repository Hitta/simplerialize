package se.hitta.serialization.capable;

import se.hitta.serialization.Serializer;

public interface SerializationCapable
{
    public void write(Serializer serializer) throws Exception;
}