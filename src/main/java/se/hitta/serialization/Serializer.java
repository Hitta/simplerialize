package se.hitta.serialization;

import com.natpryce.maybe.Maybe;

public interface Serializer
{
    public Serializer start() throws Exception;

    public void finish() throws Exception;

    public Serializer startContainer(final String name) throws Exception;

    public Serializer endContainer() throws Exception;

    public Serializer writeWithAdapter(Maybe<?> target) throws Exception;

    //public Serializer writeWithAdapter(Object target) throws Exception;
    public <T> Serializer writeWithAdapter(final T target) throws Exception;

    public Serializer writeRepeating(String name, Iterable<?> elements) throws Exception;

    // TODO: Hide from clients?
    public Serializer writePrimitive(Object target) throws Exception;

    public Serializer writeNameValue(String name, String value) throws Exception;

    public Serializer writeNameValue(String name, Boolean value) throws Exception;

    public Serializer writeNameValue(String name, Short value) throws Exception;

    public Serializer writeNameValue(String name, Integer value) throws Exception;

    public Serializer writeNameValue(String name, Long value) throws Exception;

    public Serializer writeNameValue(String name, Float value) throws Exception;

    public Serializer writeNameValue(String name, Double value) throws Exception;

    public Serializer writeNameValue(String name, boolean value) throws Exception;

    public Serializer writeNameValue(String name, int value) throws Exception;

    public Serializer writeNameValue(String name, double value) throws Exception;

    public Serializer writeNameValue(String name, float value) throws Exception;

    public Serializer writeNameValue(String name, Maybe<?> value) throws Exception;

}
