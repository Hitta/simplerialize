package se.hitta.serialization.adapter;


public interface SerializationAdapter<T> extends JsonSerializationAdapter<T>, XmlSerializationAdapter<T>
{}