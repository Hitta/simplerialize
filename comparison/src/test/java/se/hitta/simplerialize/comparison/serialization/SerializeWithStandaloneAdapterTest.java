package se.hitta.simplerialize.comparison.serialization;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.adapters.DefaultAdapterMapper;

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