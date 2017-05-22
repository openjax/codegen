<img src="https://www.cohesionfirst.org/logo.png" align="right">

## codegen-maven-plugin<br>![mvn-plugin][mvn-plugin] <a href="https://www.cohesionfirst.org/"><img src="https://img.shields.io/badge/CohesionFirst%E2%84%A2--blue.svg"></a>
> Maven Plugin for general code-generation tools

### Introduction

The `codegen-maven-plugin` plugin is used for general code-generation tools.

### Goals Overview

* [`codegen:istenum`](#codegenistenum) constructs an Incremental Search Tree enum class.

### Usage

#### `codegen:istenum`

The `codegen:istenum` goal is bound to the `generate-sources` phase, and constructs a class with a statically defined Incremental Search Tree enum from a file with a sorted newline-delimited list of keywords.

##### Example 1

```xml
<plugin>
  <groupId>org.lib4jx.maven.plugin</groupId>
  <artifactId>codegen-maven-plugin</artifactId>
  <version>1.0.1</version>
  <executions>
    <execution>
      <goals>
        <goal>istenum</goal>
      </goals>
      <configuration>
        <file>src/test/resources/keywords.txt</file>
        <dir>generated-test-sources/istenum</dir>
        <className>org.mycompany.Keywords</className>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Configuration Parameters

| Name            | Type    | Use      | Description                                                                 |
|:----------------|:--------|:---------|:----------------------------------------------------------------------------|
| `/file`         | String  | Required | File containing sorted newline-delimited list of seed keywords.             |
| `/dir`          | String  | Required | Destination directory of generated enum.                                    |
| `/className`    | String  | Required | Name of class of generated enum.                                            |
| `/inheritsFrom` | String  | Optional | Name of class of the generated enum must inherit from. **Default:** `null`. |

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[mvn-plugin]: https://img.shields.io/badge/mvn-plugin-lightgrey.svg