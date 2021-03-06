package se.hitta.simplerialize.comparison.simplerialize;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.adapters.DefaultAdapterMapper;
import se.hitta.simplerialize.comparison.SampleObject;

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