# AutoGen Maven Plugin

**Auto-generate code during the Maven build lifecycle**

The AutoGen Plugin is used to execute auto-generation tools defined in [AutoGen](..) during the Maven build lifecycle, in a phase such as `generate-sources` or `generate-test-sources`.

### Goals Overview

The AutoGen Plugin has one goal. The goals are bound to their proper phases within the Maven Lifecycle, and can be automatically executed during their respective phases with the use of `<extensions>true</extensions>` in the plugin descriptor.

* [`autogen:radixtree`](#autogenradixtree) is bound to the `generate-sources` phase, and is used to generate `RadixTreeEnum` source files.

### Usage

#### `autogen:radixtree`

The `autogen:radixtree` goal is bound to the `generate-sources` phase, and is used to generate `RadixTreeEnum` source files.

### Executing the AutoGen Plugin

To execute the plugin from the command line, the following command can be used:

```bash
mvn org.fastjax.autogen:autogen-maven-plugin:<goal> -DinFile=<inFile> -DclassName=<className> -DoutDir=[outDir] -DinheritsFrom=[inheritsFrom]
```

### Configuring the AutoGen Plugin

To configure the plugin in your POM, the following is an example of the plugin descriptor:

```xml
<plugin>
  <groupId>org.fastjax.autogen</groupId>
  <artifactId>autogen-maven-plugin</artifactId>
  <version>0.2.3-SNAPSHOT</version>
  <executions>
    <execution>
      <goals>
        <goal>radixtree</goal>
      </goals>
      <phase>generate-sources</phase>
      <configuration>
        <inFile>src/test/resources/keywords.txt</inFile>
        <outDir>generated-sources/radixtree</outDir>
        <className>org.example.Keyword</className>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Configuration Parameters

| **Configuration**          | **Property**           | **Type**          | **Use**            | **Description**                                                                   |
|:---------------------------|:-----------------------|:------------------|:-------------------|:----------------------------------------------------------------------------------|
| `<inFile>`                 | inFile                 | String            | Required           | File containing sorted newline-delimited list of keywords.                        |
| `<outDir>`<br>&nbsp;       | outDir<br>&nbsp;       | String<br>&nbsp;  | Optional<br>&nbsp; | Destination directory of generated enum.<br>**Default:** `project.basedir`        |
| `<className>`              | className              | String            | Required           | Class name of generated enum.                                                     |
| `<inheritsFrom>`<br>&nbsp; | inheritsFrom<br>&nbsp; | String<br>&nbsp;  | Optional<br>&nbsp; | Interface class name the generated enum must inherit from.<br>**Default:** `null` |

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg