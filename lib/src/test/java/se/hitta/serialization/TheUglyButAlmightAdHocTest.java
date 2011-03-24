package se.hitta.serialization;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import se.hitta.serialization.adapters.DefaultAdapterMapper;
import se.hitta.serialization.implementations.JacksonJsonSerializer;
import se.hitta.serialization.implementations.WoodstoxXmlSerializer;

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
        final Serializer serializer = new WoodstoxXmlSerializer(writer, mapper);
        final SerializationRootContext context = serializer.start();
        final SerializationAdapter<Target> adapter = mapper.resolveAdapter(Target.class);
        adapter.write(new Target(), context);
        serializer.finish();
    }

    private void serializeJson(final StringWriter writer, final AdapterMapper mapper) throws Exception
    {
        final Serializer serializer = new JacksonJsonSerializer(writer, mapper);
        final SerializationRootContext context = serializer.start();
        mapper.resolveAdapter(Target.class).write(new Target(), context);
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
        public void write(final Target target, final SerializationRootContext serializer) throws Exception
        {
            final SerializationContainerContext root = serializer.startContainer("yeah");
            serializer.writeNameValue("def", Maybe.definitely("howdy"));
            serializer.writeNameValue("unk", Maybe.unknown());
            serializer.writeNameValue("str", target.str);
            serializer.writeNameValue("bool", target.bool);
            serializer.startContainer("nested").writeWithAdapter(target.nested).end();
            serializer.beneath("repeating").eachComplex(target.multiple_nested);
            root.end();
        }
    }

    public static final class NestedTargetAdapter implements SerializationAdapter<NestedTarget>
    {
        @Override
        public void write(final NestedTarget target, final SerializationRootContext serializer) throws Exception
        {
            final SerializationContainerContext root = serializer.startContainer("nested");
            root.writeNameValue("nestedTarget", "foo");
            root.end();
        }
    }
}