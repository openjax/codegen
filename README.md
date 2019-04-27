# OpenJAX Extensions CodeGen

> Auto-generated code patterns for better language cohesion, and faster runtime performance

[![Build Status](https://travis-ci.org/openjax/ext-codegen.png)](https://travis-ci.org/openjax/ext-codegen)
[![Coverage Status](https://coveralls.io/repos/github/openjax/ext-codegen/badge.svg)](https://coveralls.io/github/openjax/ext-codegen)
[![Javadocs](https://www.javadoc.io/badge/org.openjax.ext.codegen/codegen.svg)](https://www.javadoc.io/doc/org.openjax.ext.codegen/codegen)
[![Released Version](https://img.shields.io/maven-central/v/org.openjax.ext.codegen/codegen.svg)](https://mvnrepository.com/artifact/org.openjax.ext.codegen/codegen)

CodeGen is a collection of modules that utilize the [Code Generation][codegen] process to obtain better language cohesion, and faster runtime performance.

## Modules

* **[radixtree][radixtree]**: Generate a [Radix Tree][radix-tree] of keywords as a Java `Enum` for time-optimized lookup operations.
* **[codegen-maven-plugin][maven-plugin]**: Maven plugin for CodeGen patterns in this project.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[radixtree]: /radixtree
[maven-plugin]: /maven-plugin

[codegen]: https://en.wikipedia.org/wiki/Code_generation_(compiler)
[radix-tree]: https://en.wikipedia.org/wiki/Radix_tree