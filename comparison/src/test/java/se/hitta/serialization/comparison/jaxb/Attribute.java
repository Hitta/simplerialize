package se.hitta.serialization.comparison.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Attribute
{
    @XmlAttribute
    public final String name;

    @XmlAttribute
    public final String value;

    public Attribute(final String name, final String value)
    {
        this.name = name;
        this.value = value;
    }

    // JAXB cruft
    private Attribute()
    {
        this.name = this.value = null;
    }
}