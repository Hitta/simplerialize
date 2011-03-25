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
package se.hitta.serialization.context;

import java.io.IOException;

import se.hitta.serialization.AdapterMapper;
import se.hitta.serialization.Serializer;

import com.natpryce.maybe.Maybe;

public interface ContainerContext extends RootContext
{
    /**
     * End (close) a container.
     * 
     * @return
     * @throws Exception
     */
    public RootContext endContainer() throws IOException;

    /**
     * Write the supplied target object using an adapter.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public ContainerContext writeWithAdapter(Maybe<?> target) throws IOException;

    /**
     * Write the supplied target object using the adapter found by this {@link Serializer}'s underlying {@link AdapterMapper}.
     * 
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public <T> ContainerContext writeWithAdapter(final T target) throws IOException;

    /**
     * Write the supplied target object using the adapter found for the supplied {@link Class}.
     * 
     * @param <T>
     * @param adapterClass
     * @param target
     * @return
     * @throws Exception
     */
    @Override
    public <T> ContainerContext writeWithAdapter(final Class<T> adapterClass, final T target) throws IOException;
}