package se.hitta.serialization;

import com.natpryce.maybe.Maybe;

public interface XmlSerializer extends Serializer
{

    XmlSerializer startElement(String name) throws Exception;

    XmlSerializer endElement() throws Exception;

    XmlSerializer startDocument() throws Exception;

    XmlSerializer writeAttribute(String name, Maybe<?> value) throws Exception;

    XmlSerializer writeAttribute(String name, Object value) throws Exception;

    @Override
    XmlSerializer writeWithAdapter(Object target) throws Exception;

    @Override
    XmlSerializer writeWithAdapter(Maybe<?> target) throws Exception;

    XmlSerializer writeElement(String name, String content) throws Exception;

    XmlSerializer writeRepeating(String elementName, Iterable<?> elements) throws Exception;

    XmlSerializer writePrimitive(Object target) throws Exception;

}
