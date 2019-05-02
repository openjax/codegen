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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RadixTreeEnumGeneratorTest {
  private static final Logger logger = LoggerFactory.getLogger(RadixTreeEnumGeneratorTest.class);
  private static final File destDir = new File("target/generated-test-sources/radixtree");

  private static void assertSource(final Class<?> enumClass, final File outFile) throws IOException {
    final String expected = new String(Files.readAllBytes(new File("src/test/java", enumClass.getName().replace('.', '/') + ".java").toPath()));
    final String actual = new String(Files.readAllBytes(outFile.toPath()));
    assertEquals(expected, actual);
  }

  private static void assertLookup(final Enum<?> keyword, final String string) {
    assertLookup(keyword.getClass(), keyword, string);
  }

  private static void assertLookup(final Class<?> enumClass, final Enum<?> keyword, final String string) {
    try {
      final Method findNext = enumClass.getMethod("findNext", enumClass, int.class, char.class);
      Enum<?> word = null;
      for (int i = 0; i < string.length(); ++i) {
        final char ch = string.charAt(i);
        word = (Enum<?>)findNext.invoke(null, word, i, ch);
        logger.info(ch + ": " + word);
        if (word == null)
          break;
      }

      assertEquals(keyword, word);
    }
    catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  @Test
  public void testRadixTree() throws IOException {
    final String packageName = getClass().getPackage().getName();
    final String className = packageName + ".RadixTree";
    final File outFile = new File(destDir, className.replace('.', '/') + ".java");
    outFile.getParentFile().mkdirs();
    RadixTreeEnumGenerator.generate(className, outFile, new String[] {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus"});

    assertSource(RadixTree.class, outFile);

    assertLookup(RadixTree.RUBENS, "rubens");
    assertLookup(RadixTree.ROMULUS, "romulus");
    assertLookup(RadixTree.RUBICUNDUS, "rubicundus");

    assertLookup(RadixTree.class, null, "rubber");
  }

  @Test
  public void testKeywords() throws IOException {
    final String packageName = getClass().getPackage().getName();
    final String className = packageName + ".Keyword";
    final File outFile = new File(destDir, className.replace('.', '/') + ".java");
    outFile.getParentFile().mkdirs();
    RadixTreeEnumGenerator.generate(className, outFile, new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("keywords.txt")));

    assertSource(Keyword.class, outFile);

    assertLookup(Keyword.ABSTRACT, "abstract");
    assertLookup(Keyword.STRICTFP, "strictfp");
    assertLookup(Keyword.WHILE, "while");

    assertLookup(Keyword.class, null, "willy");
  }
}