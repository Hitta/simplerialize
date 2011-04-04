/*
 * Copyright 2011 Hittapunktse AB (http://www.hitta.se/)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.hitta.simplerialize.adapters;

import java.io.IOException;

import se.hitta.simplerialize.SerializationAdapter;
import se.hitta.simplerialize.Serializer;

/**
 * {@link SerializationAdapter} singleton for skipped classes. It's a
 * {@code null} implementation in the sense that is won't generate any output.
 */
public final class NullAdapter implements SerializationAdapter<Object>
{
    /**
     * The {@link NullAdapter} instance.
     */
    public static final SerializationAdapter<?> instance = new NullAdapter();

    private NullAdapter()
    {}

    /*
     * (non-Javadoc)
     * @see se.hitta.serialization.SerializationAdapter#write(java.lang.Object, se.hitta.serialization.Serializer)
     */
    @Override
    public void write(final Object target, final Serializer serializer) throws IOException
    {}
}