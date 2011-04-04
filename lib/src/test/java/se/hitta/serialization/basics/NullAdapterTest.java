package se.hitta.serialization.basics;

import java.io.IOException;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.SerializationAdapter;
import se.hitta.serialization.Serializer;
import se.hitta.serialization.adapters.DefaultAdapterMapper;

public final class NullAdapterTest extends AbstractSerializationTest
{
    @Override
    public AdapterMapper createMapper()
    {
        return new DefaultAdapterMapper().skip(String.class);
    }

    @Override
    public void write(final Serializer context) throws IOException
    {
        context.startContainer("root").writeWithAdapter("foo").endContainer();
    }

    static final class CharSequenceAdapter implements SerializationAdapter<CharSequence>
    {
        @Override
        public void write(final CharSequence target, final Serializer context) throws IOException
        {
            context.writeNameValue("value", target);
        }
    }
}