package org.openjax.codegen.radixtree;

public enum RadixTree {
  ROMANE("romane", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2}, {0, 1}, {0, 1}, {0}}),
  ROMANUS("romanus", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2}, {0, 1}, {0, 1}, {1}}),
  ROMULUS("romulus", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2}, {2}}),
  RUBENS("rubens", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4}, {3}}),
  RUBER("ruber", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4}, {4}}),
  RUBICON("rubicon", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {5, 6}, {5, 6}, {5}}),
  RUBICUNDUS("rubicundus", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {5, 6}, {5, 6}, {6}});

  private static final int[] root = new int[] {0, 1, 2, 3, 4, 5, 6};
  private static final RadixTree[] values = values();
  private final java.lang.String token;
  final int[][] tree;

  RadixTree(final java.lang.String token, final int[][] tree) {
    this.token = token;
    this.tree = tree;
  }

  public static RadixTree findNext(final RadixTree previous, final int position, final char ch) {
    if (position == 0) {
      final int index = org.openjax.codegen.radixtree.RadixTreeEnumUtil.binarySearch(values, RadixTree.root, ch, position);
      return index < 0 ? null : values[index];
    }

    if (position <= previous.tree.length) {
      final int[] tree = previous.tree[position - 1];
      final int index = org.openjax.codegen.radixtree.RadixTreeEnumUtil.binarySearch(values, tree, ch, position);
      return index < 0 ? null : values[tree[index]];
    }

    return previous.token.length() <= position || previous.token.charAt(position) != ch ? null : previous;
  }

  @java.lang.Override
  public java.lang.String toString() {
    return token;
  }
}