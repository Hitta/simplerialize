package se.hitta.simplerialize.comparison;

import java.io.IOException;
import java.io.Writer;

public final class JsonDelegate implements Delegate
{
    private final AbstractTest target;

    public JsonDelegate(final AbstractTest target)
    {
        this.target = target;
    }

    @Override
    public void execute() throws Exception
    {
        this.target.serializeJsonTo(new Writer()
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