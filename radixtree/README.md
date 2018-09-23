# RadixTree

**Generate a [Radix Tree][radix-tree] of keywords as a Java `Enum` for time-optimized lookup operations.**

## Usage

This module takes a list of keywords as an input, and produces a Java `Enum` that allows incremental, optimized lookup operations.

With the generated `Enum`, a program can thereafter perform lookups for matching keywords, character by character. Each next character narrows the search space of the matching enums by stepping deeper into the radix tree.

<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Patricia_trie.svg/320px-Patricia_trie.svg.png"/>
</p>

## Performance

lookup operations are performed incrementally. Each next character narrows the search space of the matching enums by stepping deeper into the radix tree. At each character input, a binary search is performed for the terms that have been narrowed by the previous character. Each Radix Tree `Enum` therefore has different specific performance, because it is based on the chosen keywords.

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
  ᵣ                   ᵣ
  ∑ O(log nᵢ) = O(log ∏ nᵢ) ≈ O(log n₀)
 ⁱ⁼⁰                 ⁱ⁼⁰
```

### Small Lists

For small lists of keywords, the same rules apply as for large lists. For small lists, however, Big-O complexity approaches constant time even faster, resulting in the same estimate:

```
  O(log n₀)
```

## Usage

TODO

### License

This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.

[radix-tree]: https://en.wikipedia.org/wiki/Radix_tree