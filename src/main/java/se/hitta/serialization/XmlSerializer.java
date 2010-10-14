package se.hitta.serialization;


public interface XmlSerializer extends Serializer
{

    XmlSerializer startElement(String name) throws Exception;

    XmlSerializer endElement() throws Exception;

    XmlSerializer startDocument() throws Exception;

    XmlSerializer writeAttribute(String name, Object value) throws Exception;

    XmlSerializer writeWithAdapter(Object target) throws Exception;

    XmlSerializer writeElement(String name, String content) throws Exception;

    XmlSerializer writeRepeating(String elementName, Iterable<?> elements) throws Exception;

    XmlSerializer writePrimitive(Object target) throws Exception;

}
