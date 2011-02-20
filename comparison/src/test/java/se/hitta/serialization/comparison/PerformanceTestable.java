package se.hitta.serialization.comparison;

import java.io.Writer;

public interface PerformanceTestable
{
    void serializeTo(Writer outputStream) throws Exception;
}
