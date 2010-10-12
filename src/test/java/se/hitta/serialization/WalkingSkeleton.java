package se.hitta.serialization;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

public final class WalkingSkeleton
{
    @Test
    public void x() throws Exception
    {
        final AdapterMapper mapper = new AdapterMapper();
        mapper.register(Target.class, new TargetAdapter());
        final StringWriter writer = new StringWriter();
        new JacksonJsonSerializer(mapper, writer).write(new Target()).done();
        writer.write('\n');
        new WoodstoxXmlSerializer(mapper, writer).write(new Target()).done();
        System.err.println(writer.toString());
    }

    public static class Target
    {
        String str = "string";
        String nullStr = null;
        int integer = 1;
        double dbl = 1.23;
        boolean bool = false;
        Collection<String> stringList = Arrays.asList("hej", "svejs", "kaka");
        Collection<Integer> integerList = Arrays.asList(1, 2, 3);
    }

    public static final class TargetAdapter implements Adapter<Target>
    {
        @Override
        public void write(final Target target, final Serializer serializer) throws Exception
        {
            serializer.writeStructureStart("sample");
            serializer.write("str", target.str);
            serializer.write("null-string", target.nullStr);
            serializer.write("int", target.integer);
            serializer.write("double", target.dbl);
            serializer.write("bool", target.bool);
            serializer.writeArray("strList", target.stringList);
            serializer.writeArray("integerList", target.integerList);
            serializer.writeStructureEnd();
        }
    }
}