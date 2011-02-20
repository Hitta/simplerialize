package se.hitta.serialization.comparison.serialization;

import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.adapter.DefaultAdapterMapper;

public final class SerializeWithEmbeddedAdapterTest extends AbstractSerializationTest
{
    @Override
    public AdapterMapper getMapper()
    {
        return new DefaultAdapterMapper();
    }
}