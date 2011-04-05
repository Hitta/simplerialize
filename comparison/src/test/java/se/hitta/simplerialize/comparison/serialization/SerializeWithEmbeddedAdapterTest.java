package se.hitta.simplerialize.comparison.serialization;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.adapters.DefaultAdapterMapper;

public final class SerializeWithEmbeddedAdapterTest extends AbstractSerializationTest
{
    @Override
    public AdapterMapper getMapper()
    {
        return new DefaultAdapterMapper();
    }
}