package se.hitta.serialization.comparison;

import java.io.IOException;
import java.io.Writer;

public final class XmlDelegate implements Delegate
{
    private final AbstractTest target;

    public XmlDelegate(final AbstractTest target)
    {
        this.target = target;
    }

    @Override
    public void execute() throws Exception
    {
        this.target.serializeXmlTo(new Writer()
        {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException
            {}

            @Override
            public void flush() throws IOException
            {}

            @Override
            public void close() throws IOException
            {}
        });
    }

    @Override
    public final String toString()
    {
        return this.target.getClass().getSimpleName();
    }
}