/* Copyright (c) 2014 OpenJAX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.openjax.codegen.radixtree;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.libj.lang.Identifiers;

/**
 * Generator for Radix Trees.
 */
public final class RadixTreeEnumGenerator {
  private static final Pattern whitespacePattern = Pattern.compile("\\s+");
  private static final class Word {
    private final String name;
    private final String identifier;
    private final int[][] tree;

    private Word(final String name) {
      this.name = name;
      this.identifier = Identifiers.toIdentifier(name.toUpperCase(), '_', '$');
      this.tree = new int[name.length() + 1][];
    }

    @Override
    public String toString() {
      return identifier;
    }
  }

  public static void generate(final String className, final File outFile, final Reader reader) throws IOException {
    generate(className, null, outFile, reader);
  }

  public static void generate(final String className, final String inheritsFrom, final File outFile, final Reader reader) throws IOException {
    final File parentFile = outFile.getParentFile();
    if (!parentFile.exists() && !parentFile.mkdirs())
      throw new IllegalStateException("Unable to create output path: " + parentFile.getAbsolutePath());

    final StringBuilder builder = new StringBuilder();
    for (int ch; (ch = reader.read()) != -1; builder.append((char)ch));

    final String in = whitespacePattern.matcher(builder.toString()).replaceAll(" ");
    final String[] tokens = in.split(" ");
    RadixTreeEnumGenerator.generate(className, inheritsFrom, outFile, tokens);
  }

  public static void generate(final String className, final File outFile, final String[] tokens) throws IOException {
    generate(className, null, outFile, tokens);
  }

  public static void generate(final String className, final String inheritsFrom, final File outFile, final String[] tokens) throws IOException {
    final RadixTreeEnumGenerator generator = new RadixTreeEnumGenerator(className, inheritsFrom, tokens);
    generator.print(outFile);
  }

  private final String pkg;
  private final String enumName;
  private final String inheritsFrom;
  private final Word[] words;

  private RadixTreeEnumGenerator(final String className, final String inheritsFrom, final String[] tokens) {
    final int lastDot = className.lastIndexOf('.');
    this.pkg = lastDot == -1 ? null : className.substring(0, lastDot);
    this.enumName = lastDot == -1 ? className : className.substring(lastDot + 1);
    this.inheritsFrom = inheritsFrom;
    this.words = new Word[tokens.length];
    Arrays.sort(tokens);
    for (int i = 0; i < tokens.length; ++i)
      words[i] = new Word(tokens[i]);

    root = new int[tokens.length];
    for (int i = 0; i < root.length; ++i)
      root[i] = i;

    init(root, 0);
  }

  protected final int[] root;

  protected void init(final int[] keywords, final int depth) {
    traverse(keywords, depth);
    for (final int keyword : keywords) {
      final int[][] tree = words[keyword].tree;
      if (tree[depth] != null)
        init(tree[depth], depth + 1);
    }
  }

  private void traverse(final int[] keywords, final int depth) {
    if (keywords.length <= 1)
      return;

    int l = 0;
    while (l < keywords.length) {
      final String name = words[keywords[l]].name;
      final int[] indices = recurse(keywords, l, depth < name.length() ? name.charAt(depth) : '\0', depth, 0);
      if (indices == null)
        break;

      for (final int index : indices)
        words[index].tree[depth] = indices;

      l += indices.length;
    }
  }

  private int[] recurse(final int[] keywords, final int index, final char ch, final int depth, final int size) {
    final String name = words[keywords[index]].name;
    if (name.length() <= depth || ch != name.charAt(depth))
      return 0 < size ? new int[size] : null;

    final int[] array = index + 1 < keywords.length ? recurse(keywords, index + 1, ch, depth, size + 1) : new int[size + 1];
    array[size] = keywords[index];
    return array;
  }

  public void print(final File file) throws IOException {
    final StringBuilder outer = new StringBuilder();
    StringBuilder x = null;
    StringBuilder y = null;
    for (final Word word : words) {
      if (x == null)
        x = new StringBuilder();
      else
        x.setLength(0);

      for (int i = 0; i < word.tree.length; ++i) {
        if (y == null)
          y = new StringBuilder();
        else
          y.setLength(0);

        if (word.tree[i] != null)
          for (int j = 0; j < word.tree[i].length; ++j)
            y.append(", ").append(word.tree[i][j]);

        if (y.length() >= 2)
          x.append(", {").append(y.substring(2)).append('}');
      }

      outer.append(",\n  ").append(word.toString().toUpperCase()).append("(\"").append(word.name).append("\", new int[][] {").append(x.substring(2)).append("})");
    }

    final StringBuilder code = new StringBuilder();
    if (pkg != null)
      code.append("package ").append(pkg).append(";\n\n");

    code.append("public enum ").append(enumName);
    code.append(inheritsFrom != null ? " implements " + inheritsFrom + " {\n" : " {\n");
    code.append(outer.substring(2)).append(";\n\n");
    code.append("  private static final int[] root = new int[] {");
    final StringBuilder root = new StringBuilder();
    for (int i = 0; i < words.length; ++i)
      root.append(", ").append(i);

    code.append(root.substring(2)).append("};\n");
    code.append("  private final ").append(String.class.getName()).append(" token;\n");
    code.append("  final int[][] tree;\n\n");
    code.append("  ").append(enumName).append("(final ").append(String.class.getName()).append(" token, final int[][] tree) {\n");
    code.append("    this.token = token;\n");
    code.append("    this.tree = tree;\n");
    code.append("  }\n\n");

    code.append("  public static ").append(enumName).append(" findNext(final ").append(enumName).append(" previous, final int position, final char ch) {\n");
    code.append("    if (position == 0) {\n");
    code.append("      final int index = ").append(RadixTreeEnumUtil.class.getName()).append(".binarySearch(").append(enumName).append(".values(), ").append(enumName).append(".root, ch, position);\n");
    code.append("      return index < 0 ? null : ").append(enumName).append(".values()[index];\n");
    code.append("    }\n\n");
    code.append("    if (position <= previous.tree.length) {\n");
    code.append("      final int[] tree = previous.tree[position - 1];\n");
    code.append("      final int index = ").append(RadixTreeEnumUtil.class.getName()).append(".binarySearch(").append(enumName).append(".values(), tree, ch, position);\n");
    code.append("      return index < 0 ? null : ").append(enumName).append(".values()[tree[index]];\n");
    code.append("    }\n\n");
    code.append("    return previous.token.length() <= position || previous.token.charAt(position) != ch ? null : previous;\n");
    code.append("  }\n\n");

    code.append("  @").append(Override.class.getName()).append('\n');
    code.append("  public ").append(String.class.getName()).append(" toString() {\n");
    code.append("    return token;\n");
    code.append("  }\n");
    code.append('}');

    Files.write(file.toPath(), code.toString().getBytes());
  }
}