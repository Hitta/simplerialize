package se.hitta.simplerialize.comparison.serialization;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.adapters.DefaultAdapterMapper;

public final class SerializeWithStandaloneAdapterTest extends AbstractSerializationTest
{
    @Override
    public AdapterMapper getMapper()
    {
        final DefaultAdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(new RootAdapter(), SampleObject.class);
        return mapper;
    }
}