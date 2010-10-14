package se.hitta.serialization;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class AdapterMapperTest
{
    @Test
    public void simpleClassToAdapterMapping()
    {
        final AdapterMapper mapper = new AdapterMapper();
        mapper.register(A.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(A.class));
    }

    @Test
    public void superClassToAdapterMapping()
    {
        final AdapterMapper mapper = new AdapterMapper();
        mapper.register(A.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(B.class));
    }

    @Test
    public void interfaceToAdapterMapping()
    {
        final AdapterMapper mapper = new AdapterMapper();
        mapper.register(I.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(C.class));
    }

    @Test
    public void superClassInterfaceToAdapterMapping()
    {
        final AdapterMapper mapper = new AdapterMapper();
        mapper.register(I.class, this.dummyAdapterInstance);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(D.class));
    }

    @Test
    public void superClassHasPrecedenceOverSuperClassInterface()
    {
        final AdapterMapper mapper = new AdapterMapper();
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

    private final Adapter<Object> dummyAdapterInstance = new DummyAdapter();

    class DummyAdapter implements Adapter<Object>
    {
        @Override
        public void write(Object target, Serializer serializer)
        {}

        @Override
        public void write(String name, Object target, Serializer serializer) throws Exception
        {}
    };
}