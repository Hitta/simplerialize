package se.hitta.serialization;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Serializer
{
    private static final Logger log = LoggerFactory.getLogger(Serializer.class);

    private final AdapterMapper mapper;

    public Serializer(final AdapterMapper mapper)
    {
        this.mapper = mapper;
    }

    public abstract Serializer write(String name, String value) throws Exception;

    public abstract Serializer write(String name, boolean value) throws Exception;

    public abstract Serializer write(String name, double value) throws Exception;

    public abstract Serializer write(String name, int value) throws Exception;
    
    public abstract Serializer write(String name, Integer value) throws Exception;

    public abstract Serializer writeStructureStart(String value) throws Exception;

    public abstract Serializer writeStructureEnd() throws Exception;

    public abstract Serializer done() throws Exception;

    public abstract Serializer writeArrayStart(String name) throws Exception;

    public abstract Serializer writeArrayEnd() throws Exception;

    public final Serializer write(final String name, final Object value) throws Exception
    {
        log.debug("Resolving adapter for " + value + " with name " + name);
        final Adapter adapter = this.mapper.resolveAdapter(value.getClass());
        adapter.write(name, value, this);
        return this;
    }

    public final Serializer write(final Object value) throws Exception
    {
        log.debug("Resolving adapter for " + value);
        this.mapper.resolveAdapter(value.getClass()).write(value, this);
        return this;
    }

    public abstract Serializer write(final String value) throws Exception;

    public abstract Serializer write(final Integer value) throws Exception;

    public abstract Serializer write(final int value) throws Exception;

    public abstract Serializer write(final double value) throws Exception;

    public abstract Serializer write(final boolean value) throws Exception;

    public void write(final String container, final String entry, final Collection<?> entries) throws Exception
    {
        writeStructureStart(container);
        write(entry, entries);
        writeStructureEnd();
    }
}