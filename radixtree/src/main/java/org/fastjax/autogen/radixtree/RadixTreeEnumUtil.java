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

public final class RadixTreeEnumUtil {
  public static int binarySearch(final Enum<?>[] full, final int[] partial, final char key, final int index) {
    int low = 0;
    int high = partial.length - 1;

    while (low <= high) {
      int mid = (low + high) >>> 1;
      final String name = full[partial[mid]].toString();
      char midVal = index < name.length() ? name.charAt(index) : ' ';

      if (midVal < key)
        low = mid + 1;
      else if (midVal > key)
        high = mid - 1;
      else
        return mid; // key found
    }

    return -(low + 1); // key not found.
  }

  private RadixTreeEnumUtil() {
  }
}