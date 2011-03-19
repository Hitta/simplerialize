package se.hitta.serialization.maps;

import java.util.HashMap;
import java.util.Map;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationContext;

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
    public void write(final SerializationContext serializer) throws Exception
    {
        final InsideContainer container = serializer.startContainer(getClass().getSimpleName());
        container.writeRepeating(map);
        container.end();
    }
}