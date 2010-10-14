package se.hitta.serialization;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdapterMapper
{
    private static final Logger log = LoggerFactory.getLogger(AdapterMapper.class);
    private final Map<Class<?>, Adapter<?>> adapterMappings = new HashMap<Class<?>, Adapter<?>>();

    public AdapterMapper()
    {
        register(String.class, new StringAdapter());
        register(Integer.class, new IntegerAdapter());
        register(Object.class, new ObjectAdapter());
    }

    public AdapterMapper register(final Class<?> clazz, final Adapter<?> adapter)
    {
        this.adapterMappings.put(clazz, adapter);
        return this;
    }

    /**
     * 1. Check for any explicit adapter for this object's class
     * 2. Check for any adapter on any of this obeject's interfaces
     * 3. Propagate upwards to the superclass
     * 
     * FIXME: Implement resolver cache
     */
    public final Adapter resolveAdapter(final Class<?> clazz)
    {
        final Adapter adapter = traverseAndFindAdapter(clazz);
        if(adapter == null)
        {
            log.warn("No adapter found for " + clazz);
            return new ObjectAdapter();
        }
        else
        {
            log.debug("Found adapter " + adapter + " for " + clazz);
            return adapter;
        }
    }

    public final Adapter traverseAndFindAdapter(final Class<?>... classes)
    {
        for(final Class<?> clazz : classes)
        {
            if(this.adapterMappings.containsKey(clazz))
            {
                return this.adapterMappings.get(clazz);
            }
            else if(clazz != null)
            {
                final Adapter<?> adapter = traverseAndFindAdapter(clazz.getInterfaces());
                return (adapter == null) ? traverseAndFindAdapter(clazz.getSuperclass()) : adapter;
            }
        }
        return null;
    }
}