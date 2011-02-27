package se.hitta.serialization;

import java.util.Map;
import java.util.Map.Entry;

import se.hitta.serialization.adapter.AdapterMapper;

import com.natpryce.maybe.Maybe;

public abstract class AbstractSerializer implements Serializer
{
    final AdapterMapper mapper;

    public AbstractSerializer(final AdapterMapper mapper) throws Exception
    {
        this.mapper = mapper;
    }

    @Override
    public final Serializer writeWithAdapter(final Maybe<?> target) throws Exception
    {
        if(target != null && target.isKnown())
        {
            writeWithAdapter(target.value());
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> Serializer writeWithAdapter(final T target) throws Exception
    {
        final Class<T> type = (Class<T>)target.getClass();
        this.mapper.resolveAdapter(type).write(target, this);
        return this;
    }

    @Override
    public Serializer writeRepeating(final String name, final Map<?, ?> elements) throws Exception
    {
        startContainer(name).writeRepeating(elements).endContainer();
        return this;
    }

    @Override
    public Serializer writeRepeating(final Map<?, ?> elements) throws Exception
    {
        for(final Entry<?, ?> element : elements.entrySet())
        {
            writeNameValue(element.getKey().toString(), element.getValue().toString());
        }
        return this;
    }
}