# Jakarta Validation API

This repository contains the Java Jakarta Validation 3.0 API.

Jakarta Validation defines a metadata model and API for JavaBean and method validation.

You can learn more about it here:
* Official website: <https://beanvalidation.org/>
* Latest draft of the spec: <https://beanvalidation.org/latest-draft/spec/>
* Reference implementation: <https://github.com/hibernate/hibernate-validator/>

## System requirements

JDK 8.

## Licensing

The Jakarta Validation API is provided and distributed under the Apache Software License 2.0.
Refer to [license.txt](https://github.com/jakartaee/validation/blob/master/license.txt) for more information.

## Build from Source

You can build the Jakarta Validation API from source by cloning the git repository https://github.com/jakartaee/validation
You will also need a JDK 8 and Maven 3 (>= 3.0.3). With these prerequisites in place you can compile the source via:

    mvn clean install

## Contribute

Want to join us? You can find all the relevant information about contributing to Jakarta Validation on the [website](https://beanvalidation.org/contribute/).

## Continuous Integration

The official Continuous Integration service for the project is hosted on [ci.eclipse.org](https://ci.eclipse.org/validation/).

## Publishing the Schemas
The schema files should be published to the https://github.com/jakartaee/jakarta.ee website repository by following the
procedure described at https://eclipse-ee4j.github.io/jakartaee-platform/publish-xml-schemas. The location for
the schema files in the repository is the static/xml/ns/validation directory.
