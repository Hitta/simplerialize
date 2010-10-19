package se.hitta.serialization.capable;

import se.hitta.serialization.XmlSerializer;

public interface XmlSerializationCapable<T>
{
    void write(XmlSerializer serializer) throws Exception;
}
