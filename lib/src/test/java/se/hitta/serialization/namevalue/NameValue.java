package se.hitta.serialization.namevalue;

import java.io.IOException;

import se.hitta.serialization.AbstractSerializationTest;
import se.hitta.serialization.context.ContainerContext;
import se.hitta.serialization.context.RootContext;

public final class NameValue
{
    public static final class StringString extends NameValueCase
    {
        @Override
        public void writeNameValue(final ContainerContext context) throws IOException
        {
            context.writeNameValue("foo", "bar");
        }
    }

    public static final class StringInteger extends NameValueCase
    {
        @Override
        public void writeNameValue(final ContainerContext context) throws IOException
        {
            context.writeNameValue("foo", 1);
        }
    }

    public static final class StringBoolean extends NameValueCase
    {
        @Override
        public void writeNameValue(final ContainerContext context) throws IOException
        {
            context.writeNameValue("foo", true);
        }
    }

    public static final class StringFloat extends NameValueCase
    {
        @Override
        public void writeNameValue(final ContainerContext context) throws IOException
        {
            context.writeNameValue("foo", 156.3f);
        }
    }

    public static final class StringDouble extends NameValueCase
    {
        @Override
        public void writeNameValue(final ContainerContext context) throws IOException
        {
            context.writeNameValue("foo", 12.34d);
        }
    }
    
    static abstract class NameValueCase extends AbstractSerializationTest
    {
        @Override
        public final void write(final RootContext context) throws IOException
        {
            final ContainerContext container = context.startContainer("root");
            writeNameValue(container);
            container.endContainer();
        }

        abstract void writeNameValue(ContainerContext context) throws IOException;
    }
}
