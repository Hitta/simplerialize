package se.hitta.serialization.namevalue;

import se.hitta.serialization.SerializationContext;
import se.hitta.serialization.structures.AbstractSerializationTest;

public final class NameValue
{
    public static final class StringString extends NameValueCase
    {
        @Override
        public void writeNameValue(final SerializationContext context) throws Exception
        {
            context.writeNameValue("foo", "bar");
        }
    }

    public static final class StringInteger extends NameValueCase
    {
        @Override
        public void writeNameValue(final SerializationContext context) throws Exception
        {
            context.writeNameValue("foo", 1);
        }
    }

    public static final class StringBoolean extends NameValueCase
    {
        @Override
        public void writeNameValue(final SerializationContext context) throws Exception
        {
            context.writeNameValue("foo", true);
        }
    }

    public static final class StringFloat extends NameValueCase
    {
        @Override
        public void writeNameValue(final SerializationContext context) throws Exception
        {
            context.writeNameValue("foo", 156.3f);
        }
    }

    public static final class StringDouble extends NameValueCase
    {
        @Override
        public void writeNameValue(final SerializationContext context) throws Exception
        {
            context.writeNameValue("foo", 12.34d);
        }
    }
    
    static abstract class NameValueCase extends AbstractSerializationTest
    {
        @Override
        public final void write(final SerializationContext context) throws Exception
        {
            context.startContainer("root");
            writeNameValue(context);
            context.endContainer();
        }

        abstract void writeNameValue(SerializationContext context) throws Exception;
    }
}
