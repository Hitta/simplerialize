package se.hitta.serialization;

import java.util.Collection;

public abstract class Serializer
{
    private final AdapterMapper mapper;

    public Serializer(final AdapterMapper mapper)
    {
        this.mapper = mapper;
    }

    public abstract Serializer write(String name, String value) throws Exception;

    public abstract Serializer write(String name, boolean value) throws Exception;

    public abstract Serializer write(String name, double value) throws Exception;

    public abstract Serializer write(String name, int value) throws Exception;

    public abstract Serializer writeStructureStart(String value) throws Exception;

    public abstract Serializer writeStructureEnd() throws Exception;

    public abstract Serializer done() throws Exception;

    public Serializer writeArray(final String name, final Collection<?> list) throws Exception
    {
        writeArrayStart(name);
        for(final Object entry : list)
        {
            this.mapper.resolveAdapter(entry.getClass()).write(entry, this);
        }
        writeArrayEnd();
        return this;
    }

    public abstract Serializer writeArrayStart(String name) throws Exception;

    public abstract Serializer writeArrayEnd() throws Exception;

    public final Serializer write(Object value) throws Exception
    {
        this.mapper.resolveAdapter(value.getClass()).write(value, this);
        return this;
    }

    public abstract Serializer write(final String value) throws Exception;

    public abstract Serializer write(final Integer value) throws Exception;

    public abstract Serializer write(final int value) throws Exception;

    public abstract Serializer write(final double value) throws Exception;

    public abstract Serializer write(final boolean value) throws Exception;
}