### About

#### FAQ
* Q: Why haven't you implemented [insert-whatever-thing-you-think-this-sort-of-library-should-support-here]
* A: Because your need isn't ours, thus: fork + fix + pull request
* Q: [insert-any-differing-opinion-about-this-library-here]
* A: Fork + fix + pull request
* Q: This library is crap because [insert-any-relevant-or-non-relevant-religious-stance-here]
* A: kthxbye

#### Use case:
* API neutral streaming serialization of POJOs to XML and JSON
* Only serialization - _no deserialization_
* Serialization specification in code (_no schemas_ - neither compilation nor generation)

#### This library DOES NOT...
* ...allow customization of output formats
* ...claim to be consistent for each and all use cases
** It may _fail horribly_ for your particular case
** It may provide you with an inconsistent model depending on context and usage
** In other words, don't trust it until you've made it prove itself for _your specific_ use case - write tests

#### This library offers:
* A naive implementation of a transparent API for serialization to JSON or XML
* A fairly compact output regardless of format
* Good performant streaming serialization (thanks to [Jackson](http://jackson.codehaus.org/) and [Woodstox](http://woodstox.codehaus.org/))

#### Exeptions:
Everything throws IOException which either means you screwed up your serialization causing invalid XML or JSON or the underlying serialization library encountered an error when writing to the underlying writer.

#### Dependencies
* Maybe: Internally at [Hitta.se](http://www.hitta.se/) we make heavy use of [Nat Pryce](http://www.natpryce.com/)'s [Maybe type](https://github.com/npryce/maybe-java). This is reflected in it being a dependency in this project for now. We might consider removing it.

### Background and rationale

#### Our scenario
We have a lot of HTTP based API services. These are almost entirely accessed using GET with various path and query parameters. They produce a response in either XML or JSON depending on the clients accept header or file extension (.xml and .json). They need to serialize transparently to JSON and XML, they need to be efficient and they _never_ need to deserialize XML or JSON input to POJOs.

#### XML and JSON serialization in Java
There are lots of good and great Java libraries out there for serializing POJOs to XML or JSON. We're using, have used or have tried [Jaxb](http://jaxb.java.net/), [Jackson](http://jackson.codehaus.org/), [Woodstox](http://woodstox.codehaus.org/), [Simple](http://simple.sourceforge.net/), [XStream](http://xstream.codehaus.org/), [org.json](http://www.json.org/java/index.html) and [Gson](http://code.google.com/p/google-gson/).

#### The problem
We're not interested in de-serialization. Period. No, really, we're not. A few of our services accept small JSON inputs - we handle that input "manually", often with [Gson](http://code.google.com/p/google-gson/).
Not requiring deserialization makes the quirks in [insert-library-name-here] a much bigger _pain_. For example we don't want to add private no-arg constructors to our immutable POJOs. Then when you've started to irritate yourself on that you can also choose to irritated yourself on the annotations, or the lack of annotations, or the inability to serialize [insert-collection-class-here] without having to write an adapter - whichever fuels your rage.

#### What we wanted
* Something that serializes to XML and JSON with the same API
* The serialization specification should be in code. Either in the object itself or in a adapter/mapper style object.

#### What we've found
Zip, zero, nada. So if you really want us too feel like idiots, please do point us to an existing solution.