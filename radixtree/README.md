# CodeGen RadixTree

[![Build Status](https://travis-ci.org/openjax/codegen.svg?1)](https://travis-ci.org/openjax/codegen)
[![Coverage Status](https://coveralls.io/repos/github/openjax/codegen/badge.svg?1)](https://coveralls.io/github/openjax/codegen)
[![Javadocs](https://www.javadoc.io/badge/org.openjax.codegen/exec.svg?1)](https://www.javadoc.io/doc/org.openjax.codegen/exec)
[![Released Version](https://img.shields.io/maven-central/v/org.openjax.codegen/exec.svg?1)](https://mvnrepository.com/artifact/org.openjax.codegen/exec)
![Snapshot Version](https://img.shields.io/nexus/s/org.openjax.codegen/exec?label=maven-snapshot&server=https%3A%2F%2Foss.sonatype.org)

## Introduction

OpenJAX CodeGen RadixTree generates a [Radix Tree][radix-tree] of keywords as a Java `Enum` for time-optimized lookup operations.

This module takes a list of keywords as an input, and produces a Java `Enum` that allows incremental, optimized lookup operations.

With the generated `Enum`, a program can thereafter perform lookups for matching keywords, character by character. Each next character narrows the search space of the matching enums by stepping deeper into the radix tree.

<a name="illustration">
  <p align="center">
    <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Patricia_trie.svg/320px-Patricia_trie.svg.png"/>
  </p>
</a>

## Performance

Lookup operations are performed incrementally. Each next character narrows the search space of the matching enums by stepping deeper into the radix tree. At each character input, a binary search is performed for the terms that have been narrowed by the previous character. Each Radix Tree `Enum` therefore has different specific performance, because it is based on the chosen keywords.

```
  Let: nᵢ = average number of keyword nodes at depth 𝖎
  Let: 𝖗  = average number of keyword characters (i.e. average depth of the radix tree)
```

The first character lookup performs in `O(log n₀)` time, where `n₀` is the number of children of the node matched from the 1st character.<br>
The next character lookup performs in `O(log n₁)` time, where `n₁` is the number of children of the node matched from the 2nd character.<br>
The next character lookup performs in `O(log n₂)` time, where `n₂` is the number of children of the node matched from the 3rd character.<br>
The next character lookup performs in `O(log n₃)` time, and so on...

### Large Lists

For large lists of keywords, each character lookup after the first is performed in `O(log nᵣ₊₁)` time. Each next lookup reduces with the subsequent lookup by the square, on average. As 𝖎 approaches 𝖗, Big-O complexity approaches constant time. The performance of whole-word lookups for large lists can be expressed as:

```
                                                        ᵣ
  O(log n₀) + O(log n₁) + O(log n₂) + ... + O(log nᵣ) = ∑ O(log nᵢ)
                                                       ⁱ⁼⁰
```

We can infer that:

```
  O(log n₀) > O(log n₁) > O(log n₂) > ... > O(log nᵣ)
```

Which allows us to estimate:

```
  ᵣ
  ∑ O(log nᵢ) < 𝖗 * O(log n₀)
 ⁱ⁼⁰
```

Since `𝖗` is a constant, it can be removed.

```
  ᵣ
  ∑ O(log nᵢ) ≈ O(log n₀)
 ⁱ⁼⁰
```

### Small Lists

For small lists of keywords, the same rules apply as for large lists. For small lists, however, Big-O complexity approaches constant time even faster, resulting in the same estimate:

```
  O(log n₀)
```

## Usage

### Generation of `RadixTreeEnum`
Suppose you want to create a `RadixTreeEnum` from the keywords in the [illustration above](#illustration).

```java
  String className = "Keyword";
  File outFile = new File(className + ".java");
  String[] keywords = new String[] {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus"};
  RadixTreeEnumGenerator.generate(className, outFile, keywords);
```

The `RadixTreeEnumGenerator.generate(...)` method will build the `RadixTreeEnum`, and will write it to `Keyword.java`.

### Lookup into `RadixTreeEnum`

Suppose you want to look up the `Keyword` matching the string `"rubens"`:

```java
  String string = "rubens";
  Keyword word = null;
  for (int i = 0; i < string.length(); ++i) {
    char ch = string.charAt(i);
    word = Keyword.findNext(word, i, ch);
    System.out.println(ch + ": " + word);
    if (word == null)
      break; // The tree does not contain the string
  }
```

This code shows how the generated `Keyword` enum can be used to perform lookups for matching values, character-by-character. The output of this code will be:

```
r: rubens
u: rubens
b: ruber
e: ruber
n: rubens
s: rubens
```

The output shows that `Keyword.RUBENS` was in fact matched from the first character lookup, which supports the `O(log n₀)` [performance estimate](#performance).

### `codegen-maven-plugin`

The [`codegen-maven-plugin`](/../../../codegen-maven-plugin) can be used to generate RadixTree enums during the build lifecycle, in a phase such as `generate-sources`.

## Contributing

Pull requests are welcome. For major changes, please [open an issue](../../issues) first to discuss what you would like to change.

Please make sure to update tests as appropriate.

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[radix-tree]: https://en.wikipedia.org/wiki/Radix_tree