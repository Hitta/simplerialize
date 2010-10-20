package se.hitta.serialization;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import com.natpryce.maybe.Maybe;

import se.hitta.serialization.adapter.AdapterMapper;
import se.hitta.serialization.adapter.SerializationAdapter;
import se.hitta.serialization.adapter.DefaultAdapterMapper;
import se.hitta.serialization.json.JacksonJsonSerializer;
import se.hitta.serialization.xml.WoodstoxXmlSerializer;

public final class WalkingSkeleton
{
    @Test
    public void x() throws Exception
    {
        final StringWriter writer = new StringWriter();
        final AdapterMapper mapper = new DefaultAdapterMapper();
        mapper.register(Target.class, new TargetAdapter());
        mapper.register(NestedTarget.class, new NestedTargetAdapter());
        serializeJson(writer, mapper);
        writer.write('\n');
        serializeXml(writer, mapper);
        System.err.println(writer.toString());
    }

    private void serializeXml(final StringWriter writer, final AdapterMapper mapper) throws Exception
    {
        final XmlSerializer serializer = new WoodstoxXmlSerializer(writer, mapper);
        serializer.startDocument();
        mapper.resolveAdapter(Target.class).writeXml(new Target(), serializer);
        serializer.done();
    }

    private void serializeJson(final StringWriter writer, final AdapterMapper mapper) throws Exception
    {
        final JsonSerializer serializer = new JacksonJsonSerializer(writer, mapper);
        mapper.resolveAdapter(Target.class).writeJson(new Target(), serializer);
        serializer.done();
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
        public void writeJson(final Target target, final JsonSerializer serializer) throws Exception
        {
            serializer.startObject();
            serializer.writeField("defin", Maybe.definitely("howdy"));
            serializer.writeField("unkn", Maybe.unknown());
            serializer.writeField("str", target.str);
            serializer.writeField("bool", target.bool);
            serializer.writeField("nested");
            serializer.writeWithAdapter(target.nested);
            serializer.startObject("mult");
            serializer.writeRepeating("m", target.multiple_nested);
            serializer.endObject();
            serializer.writeArrayField("arr", target.multiple_nested);
            serializer.endObject();
        }

        @Override
        public void writeXml(final Target target, final XmlSerializer serializer) throws Exception
        {
            serializer.startElement("target");
            serializer.writeAttribute("defin", Maybe.definitely("howdy"));
            serializer.writeAttribute("unkn", Maybe.unknown());
            serializer.writeAttribute("str", target.str);
            serializer.writeAttribute("bool", target.bool);
            serializer.writeWithAdapter(target.nested);
            serializer.startElement("mult").writeRepeating("n", target.multiple_nested).endElement();
            serializer.endElement();
        }
    }

    public static final class NestedTargetAdapter implements SerializationAdapter<NestedTarget>
    {
        @Override
        public void writeJson(NestedTarget target, JsonSerializer serializer) throws Exception
        {
            serializer.startObject();
            serializer.writeField("str", target.str);
            serializer.startArray("ints");
            serializer.writeWithAdapter(target.ints);
            serializer.endArray();
            serializer.endObject();
        }

        @Override
        public void writeXml(NestedTarget target, XmlSerializer serializer) throws Exception
        {
            serializer.startElement("nested");
            serializer.writeAttribute("str", target.str);
            serializer.startElement("ints-adapter").writeWithAdapter(target.ints).endElement();
            serializer.startElement("ints-repeating").writeRepeating("int", target.ints).endElement();
            serializer.endElement();
        }
    }
}