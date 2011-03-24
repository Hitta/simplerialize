package se.hitta.serialization.maps;

import java.util.HashMap;
import java.util.Map;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationContainerContext;
import se.hitta.serialization.SerializationRootContext;

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
    public void write(final SerializationRootContext serializer) throws Exception
    {
        final SerializationContainerContext container = serializer.startContainer(getClass().getSimpleName());
        container.writeRepeating(map);
        container.end();
    }
}