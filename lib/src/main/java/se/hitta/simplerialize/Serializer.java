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
package se.hitta.simplerialize;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

import com.fasterxml.jackson.core.type.TypeReference;

import se.hitta.simplerialize.implementations.JacksonJsonSerializer;


/**
 * The interface for all concrete {@link Serializer} implementations.
 * 
 * The general usage pattern of a {@link Serializer} is<br/>
 * <br/>
 * <code>
 * // Create a mapper<br/>
 * {@link AdapterMapper} mapper = ...;<br/>
 * // Register your {@link SerializationAdapter}s<br/>
 * mapper.register(new FooAdapter(), Foo.class);<br/>
 * // Create your serializer<br/>
 * {@link Serializer} serializer = ...;<br/>
 * // Get down to business<br/>
 * serializer.start().writeWithAdapter(new Foo()).finish();<br/>
 * </code>
 */
public interface Serializer extends Flushable, Closeable
{
    /**
     * Start output, only call once and <strong>don't ever</strong> call it from
     * within a {@link SerializationAdapter}
     * 
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer start() throws IOException;

    /**
     * Write each primitive returned from the supplied {@link Iterable}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachPrimitive(String container, Iterable<?> elements) throws IOException;

    /**
     * Write each primitive returned from the supplied {@link Iterator}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachPrimitives(String container, Iterator<?> elements) throws IOException;

    /**
     * Write each object of the supplied {@link Iterable} with their respective
     * {@link SerializationAdapter}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachComplex(String container, Iterable<?> elements) throws IOException;
    
    /**
     * Write each object of the supplied {@link Iterable} with their respective
     * {@link SerializationAdapter}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @param outputEmpty Generates an array (empty) even when there are no elements. Only eligible for {@link JacksonJsonSerializer}
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachComplex(String container, Iterable<?> elements, boolean outputEmpty) throws IOException;
    
    /**
     * Write each element of the supplied {@link Iterator}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachComplex(String container, Iterator<?> elements) throws IOException;

    /**
     * Write each object of the supplied {@link Iterable} with their respective
     * {@link SerializationAdapter}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @param outputEmpty Generates an array (empty) even when there are no elements. Only eligible for {@link JacksonJsonSerializer}
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachComplex(String container, Iterator<?> elements, boolean outputEmpty) throws IOException;

    /**
     * Write each object of the supplied {@link Iterable} with the supplied
     * {@link SerializationAdapter}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @param adapter The {@link SerializationAdapter} to use 
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachComplex(String container, Iterable<?> elements, SerializationAdapter adapter) throws IOException;

    /**
     * Write each object of the supplied {@link Iterator} with the supplied
     * {@link SerializationAdapter}.
     * 
     * @param container Name of the container for the elements
     * @param elements The {@link Iterator} whose elements to write
     * @param adapter The {@link SerializationAdapter} to use 
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachComplex(String container, Iterator<?> elements, SerializationAdapter adapter) throws IOException;

    /**
     * Start a container. For XML, usually an element. For JSON, usually an
     * object.
     * 
     * @param name Name of the container for the elements
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer startContainer(final String name) throws IOException;

    /**
     * End (close) a container.
     * 
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer endContainer() throws IOException;

    /**
     * If the supplied {@link com.google.common.base.Optional} is known write it using an adapter
     * otherwise do nothing.
     * 
     * @param target The {@link com.google.common.base.Optional} whose elements to serialize
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeWithAdapter(com.google.common.base.Optional<?> target) throws IOException;

    /**
     * If the supplied {@link java.util.Optional} is known write it using an adapter
     * otherwise do nothing.
     *
     * @param target The {@link java.util.Optional} whose elements to serialize
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeWithAdapter(java.util.Optional<?> target) throws IOException;

    /**
     * Write the supplied target object using the adapter found by this
     * {@link Serializer}'s underlying {@link AdapterMapper}.
     * 
     * @param target The object to serialize
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    <T> Serializer writeWithAdapter(final T target) throws IOException;
    
    /**
     * Write the supplied target object using the adapter found by this
     * {@link Serializer}'s underlying {@link AdapterMapper}.
     * 
     * @param target The object to serialize
     * @param typeToken The {@link TypeReference} that the adapter is registered for.
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    <T> Serializer writeWithAdapter(final T target, final TypeReference<T> typeReference) throws IOException;
    
    /**
     * Write the supplied target object using the adapter found for the supplied
     * {@link Class}.
     * 
     * @param <T>
     * @param adapterClass The {@link Class} for which to find an
     * {@link SerializationAdapter} which should be used to serialize the
     * {@code target} object
     * @param target The object to serialize
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    <T> Serializer writeWithAdapter(final Class<T> adapterClass, final T target) throws IOException;

    /**
     * Write a {@link String} name with a {@link String} value.
     * 
     * @param name 
     * @param value
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeNameValue(String name, CharSequence value) throws IOException;

    /**
     * Write a {@link String} name value pair with a {@link Boolean} value.
     * @param name
     * @param value
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeNameValue(String name, Boolean value) throws IOException;

    
    /**
     * Write a {@link String} name value pair with a {@link Number} value. Supported sublasses are: <br>
     * <ul>
     * <li>{@link Short}</li>
     * <li>{@link Integer}</li>
     * <li>{@link Long}</li>
     * <li>{@link Float}</li>
     * <li>{@link Double}</li>
     * </ul>
     * @param <T>
     * @param name
     * @param value
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    <T extends Number>Serializer writeNameValue(final String name, final T value) throws IOException;
    
     /**
     * If the supplied {@link com.google.common.base.Optional} is known, write a {@link String} name and
     * the value of the {@link com.google.common.base.Optional}. Otherwise, do nothing.
     *
     * @param name
     * @param value
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeNameValue(String name, com.google.common.base.Optional<?> value) throws IOException;

     /**
     * If the supplied {@link java.util.Optional} is known, write a {@link String} name and
     * the value of the {@link java.util.Optional}. Otherwise, do nothing.
     *
     * @param name
     * @param value
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeNameValue(String name, java.util.Optional<?> value) throws IOException;

     /**
     * Write a {@link String} name and null value.
     *
     * @param name
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer writeNullValue(String name) throws IOException;

    /**
     * Print the output of this {@link Serializer} to the supplied
     * {@link Appendable}. This a debugging tool and <strong>will not
     * work</strong> if your serializing to an {@link OutputStream} which should
     * be your general case.
     * 
     * @param target The {@link Appendable} to write to
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    void printTo(Appendable target) throws IOException;

    /**
     * You may use this, but we really think you shouldn't.
     *  
     * @param target
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    @Deprecated
    Serializer writeObject(Object target) throws IOException;

    /**
     * Write each primitive returned from the supplied {@link Iterable} and further nested {@link Iterable}.
     *
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachNestedPrimitive(final String container, final Iterable<?> elements) throws IOException;

    /**
     * Write each primitive returned from the supplied {@link Iterable} and nested {@link java.util.Iterator} or {@link Iterable} .
     *
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachNestedPrimitive(final String container, final Iterator<?> elements) throws IOException;

    /**
     * Write each complex object returned from the supplied {@link Iterable} and further nested {@link Iterable}.
     *
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachNestedComplex(String container, Iterable<?> elements) throws IOException;

    /**
     * Write each complex object returned from the supplied {@link java.util.Iterator} and further nested {@link java.util.Iterator}.
     *
     * @param container Name of the container for the elements
     * @param elements The {@link Iterable} whose elements to write
     * @return this {@link Serializer} instance to allow call chaining
     * @throws IOException if there's either a format problem (ie your usage of
     * the library produced illegal XML or JSON) or if an {@link IOException}
     * occurs when writing to the underlying {@link OutputStream} or
     * {@link Writer}.
     */
    Serializer eachNestedComplex(String container, Iterator<?> elements) throws IOException;

}