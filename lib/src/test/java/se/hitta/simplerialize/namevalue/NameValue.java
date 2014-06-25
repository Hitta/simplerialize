package se.hitta.simplerialize.namevalue;

import java.io.IOException;

import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

public final class NameValue
{
    public static final class StringString extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNameValue("foo", "bar");
        }
    }

    public static final class StringBoolean extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNameValue("foo", true);
        }
    }
    
	public static final class StringShort extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
        	Short s = 7;
            context.writeNameValue("foo", s);
        }
    }
	
    public static final class StringInteger extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNameValue("foo", 1);
        }
    }

    public static final class StringLong extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNameValue("foo", 234234L);
        }
    }
    
    public static final class StringFloat extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNameValue("foo", 156.3f);
        }
    }

    public static final class StringDouble extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNameValue("foo", 12.34d);
        }
    }

    public static final class StringNull extends NameValueCase
    {
        @Override
        public void writeNameValue(final Serializer context) throws IOException
        {
            context.writeNullValue("foo");
        }
    }
    
    static abstract class NameValueCase extends AbstractSerializationTest
    {
        @Override
        public final void write(final Serializer serializer) throws IOException
        {
            serializer.startContainer("root");
            writeNameValue(serializer);
            serializer.endContainer();
        }

        abstract void writeNameValue(Serializer context) throws IOException;
    }
}
