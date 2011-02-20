Use case:
* API neutral serialization of POJOs to XML and JSON with good performance
* Only serialization - no deserialization (ie no XML/JSON -> POJO) this is ONE WAY only
* Schemaless

This library offers:
* Transparent serialization to JSON and XML
* A fairly compact output regardless of format
* The underlying serializers are streaming

This library DOES NOT:
* Allow customization of output formats
* Claim to be consistent for each and all use cases, it may fail horribly for your particular case
