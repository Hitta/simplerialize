package se.hitta.serialization;


public interface Adapter<T>
{
    void write(T target, Serializer writer) throws Exception;
}