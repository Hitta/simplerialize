package se.hitta.serialization;

import com.natpryce.maybe.Maybe;

public interface Serializer
{
    public Serializer start() throws Exception;
    public void finish() throws Exception;

    public Serializer startContainer(final String name) throws Exception;
    public Serializer endContainer() throws Exception;

    public Serializer writeWithAdapter(Maybe<?> target) throws Exception;
    public <T> Serializer writeWithAdapter(final T target) throws Exception;

    public Serializer writeRepeating(String name, Iterable<?> elements) throws Exception;

    // TODO: Hide from clients?
    public Serializer writeObject(Object target) throws Exception;

    public Serializer writeNameValue(String name, String value) throws Exception;
    public Serializer writeNameValue(String name, Boolean value) throws Exception;
    public Serializer writeNameValue(String name, Short value) throws Exception;
    public Serializer writeNameValue(String name, Integer value) throws Exception;
    public Serializer writeNameValue(String name, Long value) throws Exception;
    public Serializer writeNameValue(String name, Float value) throws Exception;
    public Serializer writeNameValue(String name, Double value) throws Exception;
    public Serializer writeNameValue(String name, Maybe<?> value) throws Exception;
    
    public Serializer writePrimitive(String value) throws Exception;
    public Serializer writePrimitive(Boolean value) throws Exception;
    public Serializer writePrimitive(Short value) throws Exception;
    public Serializer writePrimitive(Integer value) throws Exception;
    public Serializer writePrimitive(Long value) throws Exception;
    public Serializer writePrimitive(Float value) throws Exception;
    public Serializer writePrimitive(Double value) throws Exception;
    public Serializer writePrimitive(Maybe<?> value) throws Exception;
}
