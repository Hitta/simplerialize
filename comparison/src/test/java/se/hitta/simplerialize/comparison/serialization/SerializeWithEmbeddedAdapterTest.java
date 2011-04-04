package se.hitta.simplerialize.comparison.serialization;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.adapters.DefaultAdapterMapper;

public final class SerializeWithEmbeddedAdapterTest extends AbstractSerializationTest
{
    @Override
    public AdapterMapper getMapper()
    {
        return new DefaultAdapterMapper();
    }
}