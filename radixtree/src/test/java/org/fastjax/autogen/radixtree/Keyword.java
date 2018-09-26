package org.fastjax.autogen.radixtree;

public enum Keyword {
  ABSTRACT("abstract", new int[][] {{0, 1}, {0}}),
  ASSERT("assert", new int[][] {{0, 1}, {1}}),
  BOOLEAN("boolean", new int[][] {{2, 3, 4}, {2}}),
  BREAK("break", new int[][] {{2, 3, 4}, {3}}),
  BYTE("byte", new int[][] {{2, 3, 4}, {4}}),
  CASE("case", new int[][] {{5, 6, 7, 8, 9, 10}, {5, 6}, {5}}),
  CATCH("catch", new int[][] {{5, 6, 7, 8, 9, 10}, {5, 6}, {6}}),
  CHAR("char", new int[][] {{5, 6, 7, 8, 9, 10}, {7}}),
  CLASS("class", new int[][] {{5, 6, 7, 8, 9, 10}, {8}}),
  CONST("const", new int[][] {{5, 6, 7, 8, 9, 10}, {9, 10}, {9, 10}, {9}}),
  CONTINUE("continue", new int[][] {{5, 6, 7, 8, 9, 10}, {9, 10}, {9, 10}, {10}}),
  DEFAULT("default", new int[][] {{11, 12, 13}, {11}}),
  DO("do", new int[][] {{11, 12, 13}, {12, 13}}),
  DOUBLE("double", new int[][] {{11, 12, 13}, {12, 13}}),
  ELSE("else", new int[][] {{14, 15, 16}, {14}}),
  ENUM("enum", new int[][] {{14, 15, 16}, {15}}),
  EXTENDS("extends", new int[][] {{14, 15, 16}, {16}}),
  FALSE("false", new int[][] {{17, 18, 19, 20, 21}, {17}}),
  FINAL("final", new int[][] {{17, 18, 19, 20, 21}, {18, 19}, {18, 19}, {18, 19}, {18, 19}}),
  FINALLY("finally", new int[][] {{17, 18, 19, 20, 21}, {18, 19}, {18, 19}, {18, 19}, {18, 19}}),
  FLOAT("float", new int[][] {{17, 18, 19, 20, 21}, {20}}),
  FOR("for", new int[][] {{17, 18, 19, 20, 21}, {21}}),
  GOTO("goto", new int[][] {{22}}),
  IF("if", new int[][] {{23, 24, 25, 26, 27, 28}, {23}}),
  IMPLEMENTS("implements", new int[][] {{23, 24, 25, 26, 27, 28}, {24, 25}, {24, 25}, {24}}),
  IMPORT("import", new int[][] {{23, 24, 25, 26, 27, 28}, {24, 25}, {24, 25}, {25}}),
  INSTANCEOF("instanceof", new int[][] {{23, 24, 25, 26, 27, 28}, {26, 27, 28}, {26}}),
  INT("int", new int[][] {{23, 24, 25, 26, 27, 28}, {26, 27, 28}, {27, 28}}),
  INTERFACE("interface", new int[][] {{23, 24, 25, 26, 27, 28}, {26, 27, 28}, {27, 28}}),
  LONG("long", new int[][] {{29}}),
  NATIVE("native", new int[][] {{30, 31, 32}, {30}}),
  NEW("new", new int[][] {{30, 31, 32}, {31}}),
  NULL("null", new int[][] {{30, 31, 32}, {32}}),
  PACKAGE("package", new int[][] {{33, 34, 35, 36}, {33}}),
  PRIVATE("private", new int[][] {{33, 34, 35, 36}, {34, 35}, {34}}),
  PROTECTED("protected", new int[][] {{33, 34, 35, 36}, {34, 35}, {35}}),
  PUBLIC("public", new int[][] {{33, 34, 35, 36}, {36}}),
  RETURN("return", new int[][] {{37}}),
  SHORT("short", new int[][] {{38, 39, 40, 41, 42, 43}, {38}}),
  STATIC("static", new int[][] {{38, 39, 40, 41, 42, 43}, {39, 40}, {39}}),
  STRICTFP("strictfp", new int[][] {{38, 39, 40, 41, 42, 43}, {39, 40}, {40}}),
  SUPER("super", new int[][] {{38, 39, 40, 41, 42, 43}, {41}}),
  SWITCH("switch", new int[][] {{38, 39, 40, 41, 42, 43}, {42}}),
  SYNCHRONIZED("synchronized", new int[][] {{38, 39, 40, 41, 42, 43}, {43}}),
  THIS("this", new int[][] {{44, 45, 46, 47, 48, 49}, {44, 45, 46}, {44}}),
  THROW("throw", new int[][] {{44, 45, 46, 47, 48, 49}, {44, 45, 46}, {45, 46}, {45, 46}, {45, 46}}),
  THROWS("throws", new int[][] {{44, 45, 46, 47, 48, 49}, {44, 45, 46}, {45, 46}, {45, 46}, {45, 46}}),
  TRANSIENT("transient", new int[][] {{44, 45, 46, 47, 48, 49}, {47, 48, 49}, {47}}),
  TRUE("true", new int[][] {{44, 45, 46, 47, 48, 49}, {47, 48, 49}, {48}}),
  TRY("try", new int[][] {{44, 45, 46, 47, 48, 49}, {47, 48, 49}, {49}}),
  VOID("void", new int[][] {{50, 51}, {50, 51}, {50}}),
  VOLATILE("volatile", new int[][] {{50, 51}, {50, 51}, {51}}),
  WHILE("while", new int[][] {{52}});

  private static final int[] root = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52};
  private final java.lang.String token;
  final int[][] tree;

  Keyword(final java.lang.String token, final int[][] tree) {
    this.token = token;
    this.tree = tree;
  }

  public static Keyword findNext(final Keyword previous, final int position, final char ch) {
    if (position == 0) {
      final int index = org.fastjax.autogen.radixtree.RadixTreeEnumUtil.binarySearch(Keyword.values(), Keyword.root, ch, position);
      return index < 0 ? null : Keyword.values()[index];
    }

    if (position <= previous.tree.length) {
      final int[] tree = previous.tree[position - 1];
      final int index = org.fastjax.autogen.radixtree.RadixTreeEnumUtil.binarySearch(Keyword.values(), tree, ch, position);
      return index < 0 ? null : Keyword.values()[tree[index]];
    }

    return previous.token.length() <= position || previous.token.charAt(position) != ch ? null : previous;
  }

  @java.lang.Override
  public java.lang.String toString() {
    return token;
  }
}