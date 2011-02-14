package se.hitta.serialization;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.adapter.DefaultAdapterMapper;
import se.hitta.serialization.adapter.SerializationAdapter;

public class AdapterMapperTest
{
    @Test
    public void simpleClassToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, A.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(A.class));
    }

    @Test
    public void superClassToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, A.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(B.class));
    }

    @Test
    public void interfaceToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, I.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(C.class));
    }

    @Test
    public void superClassInterfaceToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, I.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(D.class));
    }

    @Test
    public void superClassHasPrecedenceOverSuperClassInterface()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(new DummyAdapter(), I.class);
        mapper.register(this.dummyAdapterInstance, C.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(D.class));
    }

    public class A
    {};

    public class B extends A
    {};

    public interface I
    {};

    public class C implements I
    {};

    public class D extends C
    {};

    private final SerializationAdapter<Object> dummyAdapterInstance = new DummyAdapter();

    static class DummyAdapter implements SerializationAdapter<Object>
    {
        @Override
        public void write(Object target, Serializer serializer) throws Exception
        {}
    };
}