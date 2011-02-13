package se.hitta.serialization.adapter;

import java.nio.ByteBuffer;

import se.hitta.serialization.Serializer;
import sun.misc.BASE64Encoder;

final class Base64EncodingAdapter implements SerializationAdapter<ByteBuffer>
{
    private final BASE64Encoder encoder = new BASE64Encoder();
    
    @Override
    public void write(final ByteBuffer target, final Serializer serializer) throws Exception
    {
        serializer.writePrimitive(this.encoder.encode(target));
    }
}