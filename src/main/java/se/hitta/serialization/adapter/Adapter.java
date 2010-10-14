package se.hitta.serialization.adapter;

import se.hitta.serialization.JsonSerializer;
import se.hitta.serialization.XmlSerializer;


public interface Adapter<T>
{
    void writeJson(T target, JsonSerializer serializer) throws Exception;

    void writeXml(T target, XmlSerializer serializer) throws Exception;
}