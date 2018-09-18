/* Copyright (c) 2014 FastJAX
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

package org.fastjax.autogen.radixtree;

import java.io.File;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;

public class RadixTreeEnumGeneratorTest {
  @Test
  public void testGenerate() throws Exception {
    final String packageName = getClass().getPackageName();
    final String className = packageName + ".Keywords";
    final File outFile = new File("target/generated-test-sources/radixtree/" + className.replace('.', '/') + ".java");
    RadixTreeEnumGenerator.generate(className, null, outFile, Thread.currentThread().getContextClassLoader().getResource("keywords.txt"));

    final String expected = "package " + packageName + ";\n\npublic enum Keywords {\n  ABSTRACT(\"abstract\", new int[][] {{0, 1}, {0}}),\n  ASSERT(\"assert\", new int[][] {{0, 1}, {1}}),\n  BOOLEAN(\"boolean\", new int[][] {{2, 3, 4}, {2}}),\n  BREAK(\"break\", new int[][] {{2, 3, 4}, {3}}),\n  BYTE(\"byte\", new int[][] {{2, 3, 4}, {4}}),\n  CASE(\"case\", new int[][] {{5, 6, 7, 8, 9, 10}, {5, 6}, {5}}),\n  CATCH(\"catch\", new int[][] {{5, 6, 7, 8, 9, 10}, {5, 6}, {6}}),\n  CHAR(\"char\", new int[][] {{5, 6, 7, 8, 9, 10}, {7}}),\n  CLASS(\"class\", new int[][] {{5, 6, 7, 8, 9, 10}, {8}}),\n  CONST(\"const\", new int[][] {{5, 6, 7, 8, 9, 10}, {9, 10}, {9, 10}, {9}}),\n  CONTINUE(\"continue\", new int[][] {{5, 6, 7, 8, 9, 10}, {9, 10}, {9, 10}, {10}}),\n  DEFAULT(\"default\", new int[][] {{11, 12, 13}, {11}}),\n  DO(\"do\", new int[][] {{11, 12, 13}, {12, 13}}),\n  DOUBLE(\"double\", new int[][] {{11, 12, 13}, {12, 13}}),\n  ELSE(\"else\", new int[][] {{14, 15, 16}, {14}}),\n  ENUM(\"enum\", new int[][] {{14, 15, 16}, {15}}),\n  EXTENDS(\"extends\", new int[][] {{14, 15, 16}, {16}}),\n  FALSE(\"false\", new int[][] {{17, 18, 19, 20, 21}, {17}}),\n  FINAL(\"final\", new int[][] {{17, 18, 19, 20, 21}, {18, 19}, {18, 19}, {18, 19}, {18, 19}}),\n  FINALLY(\"finally\", new int[][] {{17, 18, 19, 20, 21}, {18, 19}, {18, 19}, {18, 19}, {18, 19}}),\n  FLOAT(\"float\", new int[][] {{17, 18, 19, 20, 21}, {20}}),\n  FOR(\"for\", new int[][] {{17, 18, 19, 20, 21}, {21}}),\n  GOTO(\"goto\", new int[][] {{22}}),\n  IF(\"if\", new int[][] {{23, 24, 25, 26, 27, 28}, {23}}),\n  IMPLEMENTS(\"implements\", new int[][] {{23, 24, 25, 26, 27, 28}, {24, 25}, {24, 25}, {24}}),\n  IMPORT(\"import\", new int[][] {{23, 24, 25, 26, 27, 28}, {24, 25}, {24, 25}, {25}}),\n  INSTANCEOF(\"instanceof\", new int[][] {{23, 24, 25, 26, 27, 28}, {26, 27, 28}, {26}}),\n  INT(\"int\", new int[][] {{23, 24, 25, 26, 27, 28}, {26, 27, 28}, {27, 28}}),\n  INTERFACE(\"interface\", new int[][] {{23, 24, 25, 26, 27, 28}, {26, 27, 28}, {27, 28}}),\n  LONG(\"long\", new int[][] {{29}}),\n  NATIVE(\"native\", new int[][] {{30, 31, 32}, {30}}),\n  NEW(\"new\", new int[][] {{30, 31, 32}, {31}}),\n  NULL(\"null\", new int[][] {{30, 31, 32}, {32}}),\n  PACKAGE(\"package\", new int[][] {{33, 34, 35, 36}, {33}}),\n  PRIVATE(\"private\", new int[][] {{33, 34, 35, 36}, {34, 35}, {34}}),\n  PROTECTED(\"protected\", new int[][] {{33, 34, 35, 36}, {34, 35}, {35}}),\n  PUBLIC(\"public\", new int[][] {{33, 34, 35, 36}, {36}}),\n  RETURN(\"return\", new int[][] {{37}}),\n  SHORT(\"short\", new int[][] {{38, 39, 40, 41, 42, 43}, {38}}),\n  STATIC(\"static\", new int[][] {{38, 39, 40, 41, 42, 43}, {39, 40}, {39}}),\n  STRICTFP(\"strictfp\", new int[][] {{38, 39, 40, 41, 42, 43}, {39, 40}, {40}}),\n  SUPER(\"super\", new int[][] {{38, 39, 40, 41, 42, 43}, {41}}),\n  SWITCH(\"switch\", new int[][] {{38, 39, 40, 41, 42, 43}, {42}}),\n  SYNCHRONIZED(\"synchronized\", new int[][] {{38, 39, 40, 41, 42, 43}, {43}}),\n  THIS(\"this\", new int[][] {{44, 45, 46, 47, 48, 49}, {44, 45, 46}, {44}}),\n  THROW(\"throw\", new int[][] {{44, 45, 46, 47, 48, 49}, {44, 45, 46}, {45, 46}, {45, 46}, {45, 46}}),\n  THROWS(\"throws\", new int[][] {{44, 45, 46, 47, 48, 49}, {44, 45, 46}, {45, 46}, {45, 46}, {45, 46}}),\n  TRANSIENT(\"transient\", new int[][] {{44, 45, 46, 47, 48, 49}, {47, 48, 49}, {47}}),\n  TRUE(\"true\", new int[][] {{44, 45, 46, 47, 48, 49}, {47, 48, 49}, {48}}),\n  TRY(\"try\", new int[][] {{44, 45, 46, 47, 48, 49}, {47, 48, 49}, {49}}),\n  VOID(\"void\", new int[][] {{50, 51}, {50, 51}, {50}}),\n  VOLATILE(\"volatile\", new int[][] {{50, 51}, {50, 51}, {51}}),\n  WHILE(\"while\", new int[][] {{52}});\n\n  private static final int[] root = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52};\n  public final java.lang.String token;\n  protected final int[][] tree;\n\n  Keywords(final java.lang.String token, final int[][] tree) {\n    this.token = token;\n    this.tree = tree;\n  }\n\n  public static Keywords findNext(final Keywords word, int position, final char ch) {\n    if (position == 0) {\n      final int found = " + packageName + ".RadixTreeEnumUtil.binarySearch(Keywords.values(), Keywords.root, ch, position);\n      return found < 0 ? null : Keywords.values()[found];\n    }\n\n    if (position <= word.tree.length) {\n      final int[] tree = word.tree[position - 1];\n      final int found = " + packageName + ".RadixTreeEnumUtil.binarySearch(Keywords.values(), tree, ch, position);\n      return found < 0 ? null : Keywords.values()[tree[found]];\n    }\n\n    return word.token.length() <= position || word.token.charAt(position) != ch ? null : word;\n  }\n\n  @java.lang.Override\n  public java.lang.String toString() {\n    return token;\n  }\n}";
    final String actual = new String(Files.readAllBytes(outFile.toPath()));
    Assert.assertEquals(expected, actual);
  }
}