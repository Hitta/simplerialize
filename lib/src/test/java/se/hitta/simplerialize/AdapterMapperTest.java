package se.hitta.simplerialize;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import se.hitta.simplerialize.AdapterMapper;
import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;
import se.hitta.simplerialize.adapters.DefaultAdapterMapper;
import se.hitta.simplerialize.adapters.NullAdapter;

public class AdapterMapperTest
{
    @Test
    public void findsAdaperRegisteredOnClass()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, ClassWithInterface.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(ClassWithInterface.class));
    }

    @Test
    public void findsAdapterRegisteredOnSuperclass()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, ClassWithInterface.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(Subclass.class));
    }

    @Test
    public void findsAdapterRegisteredOnClassInterface()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, Interface.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(ClassWithInterface.class));
    }

    @Test
    public void findsAdapterRegisteredOnSuperclassInterface()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(this.dummyAdapterInstance, Interface.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(Subclass.class));
    }

    @Test
    public void findsSuperclassAdapterBeforeInterfaceAdapter()
    {
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(new DummyAdapter(), Interface.class);
        mapper.register(this.dummyAdapterInstance, ClassWithInterface.class);
        assertSame(this.dummyAdapterInstance, mapper.resolveAdapter(Subclass.class));
    }

    public void skippedClassReturnesNullAdapter()
    {
        assertSame(NullAdapter.instance, new DefaultAdapterMapper().skip(Subclass.class).resolveAdapter(Subclass.class));
    }

    public class ClassWithInterface implements Interface
    {};

    public class Subclass extends ClassWithInterface
    {};

    public interface Interface
    {};

    private final SerializationAdapter<Object> dummyAdapterInstance = new DummyAdapter();

    static class DummyAdapter implements SerializationAdapter<Object>
    {
        @Override
        public void write(Object target, Serializer serializer) throws IOException
        {}
    };
}