
### About
This is an opinionated library with a narrow use case. Please treat it as such.

#### This library offers
* API neutral serialization to XML and JSON
* Only serialization - _no deserialization_
* Serialization specification in code (_no schemas_ - neither compilation nor generation)
* Compact output regardless of format
* Good performant streaming serialization (thanks to [Jackson](http://jackson.codehaus.org/) and [Woodstox](http://woodstox.codehaus.org/))
* Non-customizable output

#### Beware that this library may
* ...fail horribly for your particular case
* ...provide you with an inconsistent model depending on context and usage

In other words, _don't trust it_ until you've made it prove itself for _your specific_ use case - write tests.

### Example

#### The following...
        Serializer serializer = (json) ? new JacksonJsonSerializer(...) : new WoodstoxXmlSerializer(...)
        serializer.start();
        serializer.startContainer("root");
        {
            serializer.eachComplex("objects", Collections.singletonMap("foo", "bar").entrySet());
            serializer.eachPrimitive("primitives", Arrays.asList("1", 1, true));
            serializer.startContainer("a");
            {
                serializer.startContainer("b");
                {
                    serializer.writeNameValue("name", "value");
                }
                serializer.endContainer();
            }
            serializer.endContainer();
        }
        serializer.endContainer();
        serializer.close();

### ...will result in this...
JSON:

        {
            "root":
            {
                "objects":
                [
                    {"foo":"bar"}
                ],
                "primitives": [ "1", 1, true ],
                "a":
                {
                    "b": { "name":"value" }
                }
            }
        }

XML:

        <?xml version='1.0' encoding='UTF-8'?>
        <root>
            <objects foo="bar" />
            <primitives>
                <value>1</value>
                <value>1</value>
                <value>true</value>
            </primitives>
            <a>
                <b name="value" />
            </a>
        </root>


#### Exeptions:
Everything throws IOException which either means you screwed up your serialization causing invalid XML or JSON or the underlying serialization library encountered an error when writing to the underlying writer.

#### Dependencies
* SLF4J: We depend on [SLF4J](http://www.slf4j.org/) so that you can plug in whatever logging you see fit
* [Jackson](http://jackson.codehaus.org/) and [Woodstox](http://woodstox.codehaus.org/) for the actual serialization
* [Maybe](https://github.com/npryce/maybe-java) we use [Nat Pryce's](http://www.natpryce.com/) Maybe type quite extensively and thus this library provides API methods that accepts maybes. Currently there's no Maven distribution of this library so we simply bundled the jar, for now

### Background and rationale

#### Our scenario
We have a lot of HTTP based API services. These are almost entirely accessed using GET with various path and query parameters. They produce their response in either XML or JSON depending on the clients accept header or file extension (.xml or .json). The response is often a subset of the POJOs and their fields so we must explicitly define what to serialize. Also the POJOs are usually defined in libraries where no serialization logic belongs. These services need to serialize transparently to JSON and XML, be efficient and they _never_ need to deserialize XML or JSON input back to POJOs.

#### XML and JSON serialization in Java
There are lots of good and great Java libraries out there for serializing POJOs to XML or JSON. We're using, have used or have tried [Jaxb](http://jaxb.java.net/), [Jackson](http://jackson.codehaus.org/), [Woodstox](http://woodstox.codehaus.org/), [Simple](http://simple.sourceforge.net/), [XStream](http://xstream.codehaus.org/), [org.json](http://www.json.org/java/index.html), [VTD-XML](http://vtd-xml.sourceforge.net/) and [Gson](http://code.google.com/p/google-gson/).

#### The problem
We're not interested in de-serialization. Period. A few of our services accept small JSON inputs - we handle that input "manually", often with [Gson](http://code.google.com/p/google-gson/). Not requiring deserialization makes the quirks in [insert-library-name-here] a much bigger _pain_. For example we don't want to add private no-arg constructors to our immutable POJOs. Then when you've started to irritate yourself on that you can also choose to irritate yourself on the annotations, or the lack of annotations, or the inability to serialize [insert-collection-class-here] without having to write an adapter - whichever fuels your rage. And when you've become agitated enough you may write a library such as this, for fun and dubious profit.