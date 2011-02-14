package se.hitta.serialization;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.adapter.DefaultAdapterMapper;
import se.hitta.serialization.adapter.SerializationAdapter;
import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

import com.natpryce.maybe.Maybe;

public final class TheUglyButAlmightAdHocTest
{
    @Test
    public void comeAtMeBro() throws Exception
    {
        final StringWriter writer = new StringWriter();
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(new TargetAdapter(), Target.class);
        mapper.register(new NestedTargetAdapter(), NestedTarget.class);
        serializeJson(writer, mapper);
        writer.write('\n');
        serializeXml(writer, mapper);
        System.err.println(writer.toString());
    }

    private void serializeXml(final StringWriter writer, final AdapterMapper mapper) throws Exception
    {
        final Serializer serializer = new WoodstoxXmlSerializer(writer, mapper).start();
        final SerializationAdapter<Target> adapter = mapper.resolveAdapter(Target.class);
        adapter.write(new Target(), serializer);
        serializer.finish();
    }

    private void serializeJson(final StringWriter writer, final AdapterMapper mapper) throws Exception
    {
        final Serializer serializer = new JacksonJsonSerializer(writer, mapper).start();
        mapper.resolveAdapter(Target.class).write(new Target(), serializer);
        serializer.finish();
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
        NestedTarget nested = new NestedTarget();
        Iterable<NestedTarget> multiple_nested = Arrays.asList(new NestedTarget(), new NestedTarget());
    }

    public static class NestedTarget
    {
        String str = "hepp";
        Collection<Integer> ints = Arrays.asList(1, 2, 3);
    }

    public static final class TargetAdapter implements SerializationAdapter<Target>
    {
        @Override
        public void write(final Target target, final Serializer serializer) throws Exception
        {
            serializer.startContainer("yeah");
            serializer.writeNameValue("def", Maybe.definitely("howdy"));
            serializer.writeNameValue("unk", Maybe.unknown());
            serializer.writeNameValue("str", target.str);
            serializer.writeNameValue("bool", target.bool);
            serializer.startContainer("nested").writeWithAdapter(target.nested).endContainer();
            serializer.writeRepeating("repeating", target.multiple_nested);
            serializer.endContainer();
        }

    }

    public static final class NestedTargetAdapter implements SerializationAdapter<NestedTarget>
    {
        @Override
        public void write(final NestedTarget target, final Serializer serializer) throws Exception
        {
            serializer.writeNameValue("nestedTarget", "foo");
        }
    }
}