<img src="http://safris.org/logo.png" align="right" />
# codegen-maven-plugin [![CohesionFirst](http://safris.org/cf2.svg)](https://cohesionfirst.com/)
> Maven Plugin for general code generation tools

## Introduction

The `codegen-maven-plugin` plugin is used for general code generation tools.

## Goals Overview

* [`codegen:istenum`](https://github.com/SevaSafris/java/new/master/maven/plugin/codegen-maven-plugin#codegenistenum) constructs an Incremental Search Tree enum class.

## Usage

### `codegen:istenum`

The `codegen:istenum` goal is bound to the `generate-sources` phase, and constructs a class with a statically defined Incremental Search Tree enum from a file with a sorted newline-delimited list of keywords.

#### Example 1

```xml
<plugin>
  <groupId>org.safris.maven.plugin</groupId>
  <artifactId>codegen-maven-plugin</artifactId>
  <version>1.0.1</version>
  <executions>
    <execution>
      <goals>
        <goal>istenum</goal>
      </goals>
      <configuration>
        <file>${basedir}/src/test/resources/keywords.txt</file>
        <dir>${project.build.directory}/generated-test-sources/istenum</dir>
        <className>org.mycompany.Keywords</className>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Configuration Parameters

| Name            | Type    | Use      | Description                                                                 |
|:----------------|:--------|:---------|:----------------------------------------------------------------------------|
| /`file`         | String  | Required | File containing sorted newline-delimited list of seed keywords.             |
| /`dir`          | String  | Required | Destination directory of generated enum.                                    |
| /`className`    | String  | Required | Name of class of generated enum.                                            |
| /`inheritsFrom` | String  | Optional | Name of class of the generated enum must inherit from. **Default:** `null`. |

## License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.
