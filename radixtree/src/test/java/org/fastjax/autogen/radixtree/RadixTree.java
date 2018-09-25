package org.fastjax.autogen.radixtree;

public enum RadixTree {
  ROMANE("romane", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2}, {0, 1}, {0, 1}, {0}}),
  ROMANUS("romanus", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2}, {0, 1}, {0, 1}, {1}}),
  ROMULUS("romulus", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {0, 1, 2}, {0, 1, 2}, {2}}),
  RUBENS("rubens", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4}, {3}}),
  RUBER("ruber", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4}, {4}}),
  RUBICON("rubicon", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {5, 6}, {5, 6}, {5}}),
  RUBICUNDUS("rubicundus", new int[][] {{0, 1, 2, 3, 4, 5, 6}, {3, 4, 5, 6}, {3, 4, 5, 6}, {5, 6}, {5, 6}, {6}});

  private static final int[] root = new int[] {0, 1, 2, 3, 4, 5, 6};
  public final java.lang.String token;
  protected final int[][] tree;

  RadixTree(final java.lang.String token, final int[][] tree) {
    this.token = token;
    this.tree = tree;
  }

  public static RadixTree findNext(final RadixTree word, int position, final char ch) {
    if (position == 0) {
      final int found = org.fastjax.autogen.radixtree.RadixTreeEnumUtil.binarySearch(RadixTree.values(), RadixTree.root, ch, position);
      return found < 0 ? null : RadixTree.values()[found];
    }

    if (position <= word.tree.length) {
      final int[] tree = word.tree[position - 1];
      final int found = org.fastjax.autogen.radixtree.RadixTreeEnumUtil.binarySearch(RadixTree.values(), tree, ch, position);
      return found < 0 ? null : RadixTree.values()[tree[found]];
    }

    return word.token.length() <= position || word.token.charAt(position) != ch ? null : word;
  }

  @java.lang.Override
  public java.lang.String toString() {
    return token;
  }
}