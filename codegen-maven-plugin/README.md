# CodeGen Maven Plugin

[![Build Status](https://travis-ci.org/openjax/codegen.svg?1)](https://travis-ci.org/openjax/codegen)
[![Coverage Status](https://coveralls.io/repos/github/openjax/codegen/badge.svg?1)](https://coveralls.io/github/openjax/codegen)
[![Javadocs](https://www.javadoc.io/badge/org.openjax.codegen/codegen-maven-plugin.svg?1)](https://www.javadoc.io/doc/org.openjax.codegen/codegen-maven-plugin)
[![Released Version](https://img.shields.io/maven-central/v/org.openjax.codegen/codegen-maven-plugin.svg?1)](https://mvnrepository.com/artifact/org.openjax.codegen/codegen-maven-plugin)

The CodeGen Plugin is used to execute auto-generation tools during the Maven build lifecycle, in a phase such as `generate-sources` or `generate-test-sources`.

### Goals Overview

The CodeGen Plugin has one goal. The goals are bound to their proper phases within the Maven Lifecycle, and can be automatically executed during their respective phases with the use of `<extensions>true</extensions>` in the plugin descriptor.

* [`codegen:radixtree`](#codegenradixtree) is bound to the `generate-sources` phase, and is used to generate `RadixTreeEnum` source files.

### Usage

#### `codegen:radixtree`

The `codegen:radixtree` goal is bound to the `generate-sources` phase, and is used to generate `RadixTreeEnum` source files.

### Executing the CodeGen Plugin

To execute the plugin from the command line, the following command can be used:

```bash
mvn org.openjax.codegen:codegen-maven-plugin:<goal> -DinFile=<inFile> -DclassName=<className> -DdestDir=[destDir] -DinheritsFrom=[inheritsFrom]
```

### Configuring the CodeGen Plugin

To configure the plugin in your POM, the following is an example of the plugin descriptor:

```xml
<plugin>
  <groupId>org.openjax.codegen</groupId>
  <artifactId>codegen-maven-plugin</artifactId>
  <version>0.2.3</version>
  <executions>
    <execution>
      <goals>
        <goal>radixtree</goal>
      </goals>
      <phase>generate-sources</phase>
      <configuration>
        <inFile>src/test/resources/keywords.txt</inFile>
        <destDir>${project.build.directory}/generated-sources/radixtree</destDir>
        <className>org.example.Keyword</className>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Configuration Parameters

| **Configuration**                    | **Property**           | **Type**          | **Use**            | **Description**                                                                   |
|:-------------------------------------|:-----------------------|:------------------|:-------------------|:----------------------------------------------------------------------------------|
| <samp>inFile¹</samp>                 | inFile                 | String            | Required           | File containing sorted newline-delimited list of keywords.                        |
| <samp>destDir¹</samp><br>&nbsp;      | destDir<br>&nbsp;      | String<br>&nbsp;  | Optional<br>&nbsp; | Destination directory of generated enum.<br>**Default:** `${project.basedir}`     |
| <samp>className¹</samp>              | className              | String            | Required           | Class name of generated enum.                                                     |
| <samp>inheritsFrom¹</samp><br>&nbsp; | inheritsFrom<br>&nbsp; | String<br>&nbsp;  | Optional<br>&nbsp; | Interface class name the generated enum must inherit from.<br>**Default:** `null` |

## Contributing

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.