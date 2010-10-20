package se.hitta.serialization.capable;

import se.hitta.serialization.XmlSerializer;

public interface XmlSerializationCapable
{
    void write(XmlSerializer serializer) throws Exception;
}
