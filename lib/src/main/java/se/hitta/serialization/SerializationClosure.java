package se.hitta.serialization;

public interface SerializationClosure<T>
{
    public void write(SerializationContext serializer, T element) throws Exception;
}