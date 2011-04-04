package se.hitta.serialization.maps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.Serializer;

public final class MapStringString extends AbstractSerializationTest
{
    private static final Map<String, String> map;
    
    static
    {
        map = new HashMap<String, String>();
        map.put("foo", "bar");
        map.put("x", "y");
        map.put("yin", "yang");
    }

    @Override
    public void write(final Serializer serializer) throws IOException
    {
        serializer.startContainer(getClass().getSimpleName());
        serializer.eachComplex("map", map.entrySet());
        serializer.endContainer();
    }
}