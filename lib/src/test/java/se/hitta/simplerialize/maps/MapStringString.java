package se.hitta.simplerialize.maps;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

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
        serializer.eachComplex("entry", map.entrySet());
        serializer.endContainer();
    }
}