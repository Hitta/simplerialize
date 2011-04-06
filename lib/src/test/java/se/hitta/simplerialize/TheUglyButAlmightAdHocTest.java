package se.hitta.simplerialize;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.natpryce.maybe.Maybe;

/**
 * This was one of the first proof of concept tests written to simply see if
 * the serialization worked (ie didn't throw any exceptions).
 */
public final class TheUglyButAlmightAdHocTest
{
    @Test
    public void comeAtMeBro() throws Exception
    {
        final Serializer serializer = Util.createCompositeSerializer();
        serializer.start().writeWithAdapter(new Target()).flush();
    }

    public static class Target implements SerializationCapable
    {
        String str = "string";
        String nullStr = null;
        int integer = 1;
        double dbl = 1.23;
        boolean bool = false;
        Collection<String> stringList = Arrays.asList("hej", "svejs", "kaka");
        Collection<Integer> integerList = Arrays.asList(1, 2, 3);
        NestedTarget nested = new NestedTarget();
        Iterable<NestedTarget> multiple_nested = Arrays.asList(new NestedTarget(), new NestedTarget());

        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.startContainer("yeah");
            {
                serializer.writeNameValue("def", Maybe.definitely("howdy"));
                serializer.writeNameValue("unk", Maybe.unknown());
                serializer.writeNameValue("str", this.str);
                serializer.writeNameValue("bool", this.bool);
                serializer.startContainer("nested");
                {
                    serializer.writeWithAdapter(this.nested);
                }
                serializer.endContainer();
                serializer.eachComplex("repeating", this.multiple_nested);
            }
            serializer.endContainer();
        }
    }

    public static class NestedTarget implements SerializationCapable
    {
        String str = "hepp";
        Collection<Integer> ints = Arrays.asList(1, 2, 3);

        @Override
        public void write(final Serializer serializer) throws IOException
        {
            serializer.startContainer("nested");
            serializer.writeNameValue("nestedTarget", "foo");
            serializer.endContainer();
        }
    }
}