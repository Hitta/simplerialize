package se.hitta.simplerialize.basics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

public final class PrimitiveAdapters
{
    public static class Booleans extends PrimitivesTest
    {
        public Booleans()
        {
            super(true, false);
        }
    }

    public static class Integers extends PrimitivesTest
    {
        public Integers()
        {
            super(1, -1, 0);
        }
    }

    public static class Floats extends PrimitivesTest
    {
        public Floats()
        {
            super(1.23f, -12.3f, 0f);
        }
    }

    public static class Doubles extends PrimitivesTest
    {
        public Doubles()
        {
            super(1.23d, -12.3d, 0d);
        }
    }

    public static class ByteBuffers extends PrimitivesTest
    {
        private static final String SWEDISH_FOR_SHRIMP_SANDWICH = "Räksmörgås";

        public ByteBuffers()
        {
            super(ByteBuffer.wrap("123".getBytes()), ByteBuffer.wrap(SWEDISH_FOR_SHRIMP_SANDWICH.getBytes()));
        }
    }

    static class PrimitivesTest extends AbstractSerializationTest
    {
        private final Object[] values;

        public PrimitivesTest(final Object... values)
        {
            this.values = values;
        }

        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.eachPrimitive("root", Arrays.asList(this.values));
        }
    }
}