package se.hitta.serialization.comparison.simple;

import java.util.Map;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root(name = "root")
public final class SampleObject
{
    @ElementMap(key = "key", value = "value", attribute = true, inline = true)
    public final Map<String, String> attributes;

    public SampleObject(final Map<String, String> attributes)
    {
        this.attributes = attributes;
    }
}