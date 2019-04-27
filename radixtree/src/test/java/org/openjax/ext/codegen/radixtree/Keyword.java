package org.openjax.standard.codegen.radixtree;

public enum Keyword {
  $INTERFACE("@interface", new int[][] {{0}}),
  ABSTRACT("abstract", new int[][] {{1, 2}, {1}}),
  ASSERT("assert", new int[][] {{1, 2}, {2}}),
  BOOLEAN("boolean", new int[][] {{3, 4, 5}, {3}}),
  BREAK("break", new int[][] {{3, 4, 5}, {4}}),
  BYTE("byte", new int[][] {{3, 4, 5}, {5}}),
  CASE("case", new int[][] {{6, 7, 8, 9, 10, 11}, {6, 7}, {6}}),
  CATCH("catch", new int[][] {{6, 7, 8, 9, 10, 11}, {6, 7}, {7}}),
  CHAR("char", new int[][] {{6, 7, 8, 9, 10, 11}, {8}}),
  CLASS("class", new int[][] {{6, 7, 8, 9, 10, 11}, {9}}),
  CONST("const", new int[][] {{6, 7, 8, 9, 10, 11}, {10, 11}, {10, 11}, {10}}),
  CONTINUE("continue", new int[][] {{6, 7, 8, 9, 10, 11}, {10, 11}, {10, 11}, {11}}),
  DEFAULT("default", new int[][] {{12, 13, 14}, {12}}),
  DO("do", new int[][] {{12, 13, 14}, {13, 14}}),
  DOUBLE("double", new int[][] {{12, 13, 14}, {13, 14}}),
  ELSE("else", new int[][] {{15, 16, 17}, {15}}),
  ENUM("enum", new int[][] {{15, 16, 17}, {16}}),
  EXTENDS("extends", new int[][] {{15, 16, 17}, {17}}),
  FALSE("false", new int[][] {{18, 19, 20, 21, 22}, {18}}),
  FINAL("final", new int[][] {{18, 19, 20, 21, 22}, {19, 20}, {19, 20}, {19, 20}, {19, 20}}),
  FINALLY("finally", new int[][] {{18, 19, 20, 21, 22}, {19, 20}, {19, 20}, {19, 20}, {19, 20}}),
  FLOAT("float", new int[][] {{18, 19, 20, 21, 22}, {21}}),
  FOR("for", new int[][] {{18, 19, 20, 21, 22}, {22}}),
  GOTO("goto", new int[][] {{23}}),
  IF("if", new int[][] {{24, 25, 26, 27, 28, 29}, {24}}),
  IMPLEMENTS("implements", new int[][] {{24, 25, 26, 27, 28, 29}, {25, 26}, {25, 26}, {25}}),
  IMPORT("import", new int[][] {{24, 25, 26, 27, 28, 29}, {25, 26}, {25, 26}, {26}}),
  INSTANCEOF("instanceof", new int[][] {{24, 25, 26, 27, 28, 29}, {27, 28, 29}, {27}}),
  INT("int", new int[][] {{24, 25, 26, 27, 28, 29}, {27, 28, 29}, {28, 29}}),
  INTERFACE("interface", new int[][] {{24, 25, 26, 27, 28, 29}, {27, 28, 29}, {28, 29}}),
  LONG("long", new int[][] {{30}}),
  NATIVE("native", new int[][] {{31, 32, 33}, {31}}),
  NEW("new", new int[][] {{31, 32, 33}, {32}}),
  NULL("null", new int[][] {{31, 32, 33}, {33}}),
  PACKAGE("package", new int[][] {{34, 35, 36, 37}, {34}}),
  PRIVATE("private", new int[][] {{34, 35, 36, 37}, {35, 36}, {35}}),
  PROTECTED("protected", new int[][] {{34, 35, 36, 37}, {35, 36}, {36}}),
  PUBLIC("public", new int[][] {{34, 35, 36, 37}, {37}}),
  RETURN("return", new int[][] {{38}}),
  SHORT("short", new int[][] {{39, 40, 41, 42, 43, 44}, {39}}),
  STATIC("static", new int[][] {{39, 40, 41, 42, 43, 44}, {40, 41}, {40}}),
  STRICTFP("strictfp", new int[][] {{39, 40, 41, 42, 43, 44}, {40, 41}, {41}}),
  SUPER("super", new int[][] {{39, 40, 41, 42, 43, 44}, {42}}),
  SWITCH("switch", new int[][] {{39, 40, 41, 42, 43, 44}, {43}}),
  SYNCHRONIZED("synchronized", new int[][] {{39, 40, 41, 42, 43, 44}, {44}}),
  THIS("this", new int[][] {{45, 46, 47, 48, 49, 50}, {45, 46, 47}, {45}}),
  THROW("throw", new int[][] {{45, 46, 47, 48, 49, 50}, {45, 46, 47}, {46, 47}, {46, 47}, {46, 47}}),
  THROWS("throws", new int[][] {{45, 46, 47, 48, 49, 50}, {45, 46, 47}, {46, 47}, {46, 47}, {46, 47}}),
  TRANSIENT("transient", new int[][] {{45, 46, 47, 48, 49, 50}, {48, 49, 50}, {48}}),
  TRUE("true", new int[][] {{45, 46, 47, 48, 49, 50}, {48, 49, 50}, {49}}),
  TRY("try", new int[][] {{45, 46, 47, 48, 49, 50}, {48, 49, 50}, {50}}),
  VOID("void", new int[][] {{51, 52}, {51, 52}, {51}}),
  VOLATILE("volatile", new int[][] {{51, 52}, {51, 52}, {52}}),
  WHILE("while", new int[][] {{53}});

  private static final int[] root = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
  private final java.lang.String token;
  final int[][] tree;

  Keyword(final java.lang.String token, final int[][] tree) {
    this.token = token;
    this.tree = tree;
  }

  public static Keyword findNext(final Keyword previous, final int position, final char ch) {
    if (position == 0) {
      final int index = org.openjax.standard.codegen.radixtree.RadixTreeEnumUtil.binarySearch(Keyword.values(), Keyword.root, ch, position);
      return index < 0 ? null : Keyword.values()[index];
    }

    if (position <= previous.tree.length) {
      final int[] tree = previous.tree[position - 1];
      final int index = org.openjax.standard.codegen.radixtree.RadixTreeEnumUtil.binarySearch(Keyword.values(), tree, ch, position);
      return index < 0 ? null : Keyword.values()[tree[index]];
    }

    return previous.token.length() <= position || previous.token.charAt(position) != ch ? null : previous;
  }

  @java.lang.Override
  public java.lang.String toString() {
    return token;
  }
}