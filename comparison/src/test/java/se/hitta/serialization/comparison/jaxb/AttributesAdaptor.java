package se.hitta.serialization.comparison.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class AttributesAdaptor extends XmlAdapter<Attribute[], Map<String, String>>
{

    @Override
    public Attribute[] marshal(final Map<String, String> v) throws Exception
    {
        final List<Attribute> list = new ArrayList<Attribute>();
        for(final Map.Entry<String, String> e : v.entrySet())
        {
            list.add(new Attribute(e.getKey(), e.getValue()));
        }
        return list.toArray(new Attribute[0]);
    }

    @Override
    public Map<String, String> unmarshal(Attribute[] v) throws Exception
    {
        throw new UnsupportedOperationException();
    }
}
