package se.hitta.serialization;

import com.natpryce.maybe.Maybe;

public interface Serializer
{
    public void done() throws Exception;

    public Serializer writeWithAdapter(Maybe<?> target) throws Exception;

    public Serializer writeWithAdapter(Object target) throws Exception;

}
