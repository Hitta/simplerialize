package se.hitta.serialization;

import com.natpryce.maybe.Maybe;

public interface JsonSerializer extends Serializer
{
    JsonSerializer startObject(String string) throws Exception;

    JsonSerializer startObject() throws Exception;

    JsonSerializer endObject() throws Exception;

    JsonSerializer writeField(String name, String value) throws Exception;

    JsonSerializer writeField(String name, Boolean value) throws Exception;

    JsonSerializer writeField(String name, Short value) throws Exception;

    JsonSerializer writeField(String name, Integer value) throws Exception;

    JsonSerializer writeField(String name, Long value) throws Exception;

    JsonSerializer writeField(String name, Float value) throws Exception;

    JsonSerializer writeField(String name, Double value) throws Exception;

    JsonSerializer writeField(String name, boolean value) throws Exception;

    JsonSerializer writeField(String name, int value) throws Exception;

    JsonSerializer writeField(String name, double value) throws Exception;

    JsonSerializer writeField(String name, float value) throws Exception;

    JsonSerializer writeField(String name, Maybe<?> value) throws Exception;

    JsonSerializer writeWithAdapter(Maybe<?> target) throws Exception;

    JsonSerializer writeWithAdapter(Object target) throws Exception;

    JsonSerializer startArray() throws Exception;

    JsonSerializer endArray() throws Exception;

    JsonSerializer writePrimitive(Object target) throws Exception;

    JsonSerializer startArray(String name) throws Exception;

    JsonSerializer writeRepeating(String elementName, Iterable<?> elements) throws Exception;

    JsonSerializer writeArray(Iterable<?> elements) throws Exception;

    JsonSerializer writeArrayField(String name, Iterable<?> elements) throws Exception;

    JsonSerializer writeField(String n) throws Exception;
}
