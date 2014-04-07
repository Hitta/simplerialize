package se.hitta.simplerialize.structures;

import com.google.common.collect.ImmutableList;
import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.Serializer;

import java.io.IOException;

public final class NestedListOfStrings extends AbstractSerializationTest {
    @Override
    public void write(final Serializer serializer) throws IOException {

        final Iterable iterable =
                ImmutableList.of(
                        ImmutableList.of(
                                ImmutableList.of(
                                        ImmutableList.of(
                                                ImmutableList.of("foo", "bar", "A", "B", "C")
                                        )
                                )
                        )
                );
        serializer.startContainer("container");
        serializer.eachNestedPrimitive("item", iterable);
        serializer.endContainer();
    }

}