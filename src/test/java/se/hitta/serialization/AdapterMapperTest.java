package se.hitta.serialization;

import static org.junit.Assert.assertSame;

import org.junit.Test;

import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.adapter.SerializationAdapter;
import se.hitta.serialization.adapter.DefaultAdapterMapper;

public class AdapterMapperTest
{
    @Test
    public void simpleClassToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(A.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(A.class));
    }

    @Test
    public void superClassToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(A.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(B.class));
    }

    @Test
    public void interfaceToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(I.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(C.class));
    }

    @Test
    public void superClassInterfaceToAdapterMapping()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(I.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(D.class));
    }

    @Test
    public void superClassHasPrecedenceOverSuperClassInterface()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(I.class, new DummyAdapter());
        mapper.register(C.class, this.dummyAdapterInstance);
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

    class DummyAdapter implements SerializationAdapter<Object>
    {
        @Override
        public void write(Object target, JsonSerializer serializer) throws Exception
        {}

        @Override
        public void write(Object target, XmlSerializer serializer) throws Exception
        {}
    };
}