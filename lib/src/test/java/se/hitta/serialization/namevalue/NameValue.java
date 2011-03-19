package se.hitta.serialization.namevalue;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.InsideContainer;
import se.hitta.serialization.SerializationContext;

public final class NameValue
{
    public static final class StringString extends NameValueCase
    {
        @Override
        public void writeNameValue(final InsideContainer context) throws Exception
        {
            context.writeNameValue("foo", "bar");
        }
    }

    public static final class StringInteger extends NameValueCase
    {
        @Override
        public void writeNameValue(final InsideContainer context) throws Exception
        {
            context.writeNameValue("foo", 1);
        }
    }

    public static final class StringBoolean extends NameValueCase
    {
        @Override
        public void writeNameValue(final InsideContainer context) throws Exception
        {
            context.writeNameValue("foo", true);
        }
    }

    public static final class StringFloat extends NameValueCase
    {
        @Override
        public void writeNameValue(final InsideContainer context) throws Exception
        {
            context.writeNameValue("foo", 156.3f);
        }
    }

    public static final class StringDouble extends NameValueCase
    {
        @Override
        public void writeNameValue(final InsideContainer context) throws Exception
        {
            context.writeNameValue("foo", 12.34d);
        }
    }
    
    static abstract class NameValueCase extends AbstractSerializationTest
    {
        @Override
        public final void write(final SerializationContext context) throws Exception
        {
            final InsideContainer container = context.startContainer("root");
            writeNameValue(container);
            container.end();
        }

        abstract void writeNameValue(InsideContainer context) throws Exception;
    }
}
