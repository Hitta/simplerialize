package se.hitta.serialization.basics;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.SerializationRootContext;

public final class PrimitiveAdapters
{
    public static class Booleans extends PrimitivesTest
    {
        public Booleans()
        {
            super(Arrays.asList(true, false));
        }
    }

    public static class Integers extends PrimitivesTest
    {
        public Integers()
        {
            super(Arrays.asList(1, -1, 0));
        }
    }

    public static class Floats extends PrimitivesTest
    {
        public Floats()
        {
            super(Arrays.asList(1.23f, -12.3f, 0f));
        }
    }
    
    public static class Doubles extends PrimitivesTest
    {
        public Doubles()
        {
            super(Arrays.asList(1.23d, -12.3d, 0d));
        }
    }
    
    public static class ByteBuffers extends PrimitivesTest
    {
        public ByteBuffers()
        {
            super(Arrays.asList(ByteBuffer.wrap("123".getBytes()), ByteBuffer.wrap("Räksmörgås".getBytes())));
        }
    }

    static class PrimitivesTest extends AbstractSerializationTest
    {
        private final List<?> values;

        public PrimitivesTest(final List<?> values)
        {
            this.values = values;
        }

        @Override
        public void write(final SerializationRootContext serializer) throws Exception
        {
            serializer.beneath("root").eachPrimitive(this.values);
        }

    }
}