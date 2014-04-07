package se.hitta.simplerialize.structures;

import com.google.common.collect.ImmutableList;
import se.hitta.simplerialize.AbstractSerializationTest;
import se.hitta.simplerialize.SerializationCapable;
import se.hitta.simplerialize.Serializer;

import java.io.IOException;

public final class NestedListOfObjects extends AbstractSerializationTest {
    @Override
    public void write(final Serializer serializer) throws IOException {

        final Iterable iterable =
                ImmutableList.of(
                        ImmutableList.of(
                                ImmutableList.of(
                                        ImmutableList.of(
                                                ImmutableList.of(
                                                        ComplexObject.of(0),
                                                        ComplexObject.of(1),
                                                        ComplexObject.of(2),
                                                        ComplexObject.of(3),
                                                        ComplexObject.of(4)
                                                )
                                        )
                                )
                        )
                );

        serializer.startContainer("container");
        serializer.eachNestedComplex("item", iterable);
        serializer.endContainer();
    }

    public static final class ComplexObject implements SerializationCapable {

        public static final ComplexObject of(int i) {
            return new ComplexObject(i);
        }

        public final int number;

        public ComplexObject(final int number) {
            this.number = number;
        }

        @Override
        public void write(final Serializer serializer) throws IOException {
            serializer.writeNameValue("number", this.number);
        }
    }

}