package se.hitta.serialization.capable;


public interface SerializationCapable<T> extends JsonSerializationCapable<T>, XmlSerializationCapable<T>
{}