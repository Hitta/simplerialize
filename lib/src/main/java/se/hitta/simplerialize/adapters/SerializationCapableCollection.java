package se.hitta.simplerialize.adapters;

import java.io.IOException;
import java.util.Collection;

import se.hitta.simplerialize.SerializationCapable;
import se.hitta.simplerialize.Serializer;

public class SerializationCapableCollection<T> implements SerializationCapable {
    private final Collection<T> collection;
    private final String typeName;

    public SerializationCapableCollection(final Collection<T> collection, final String typeName) {
        this.collection = collection;
        this.typeName = typeName;
    }

    @Override
    public void write(final Serializer serializer) throws IOException {
        serializer.startContainer(this.typeName + "s");
        serializer.writeNameValue("included", collection.size());
        serializer.eachComplex(typeName, collection);
        serializer.endContainer();
    }
}
