/* Copyright (c) 2019 OpenJAX
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

package org.openjax.codegen.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bean for the "parameters" configuration for {@link TemplateMojo}.
 */
public class Parameters {
  private Map<String,String> types;
  private List<String> imports;

  /**
   * Returns the "types" map.
   *
   * @return The "types" map.
   */
  public Map<String,String> getTypes() {
    return this.types;
  }

  /**
   * Sets the "types" map.
   *
   * @param types The "types" map to set.
   */
  public void setTypes(final Map<String,String> types) {
    this.types = types;
  }

  /**
   * Returns the "imports" list.
   *
   * @return The "imports" list.
   */
  public List<String> getImports() {
    return this.imports;
  }

  /**
   * Sets the "imports" list.
   *
   * @param imports The "imports" list to set.
   */
  public void setImports(final List<String> imports) {
    this.imports = imports;
  }

  public void remap(final Map<String,String> map) {
    final Map<String,String> result = new HashMap<>();
    OUT:
    for (final Map.Entry<String,String> entry : types.entrySet()) {
      for (final Map.Entry<String,String> rule : map.entrySet()) {
        if (entry.getKey().equals(rule.getValue())) {
          result.put(rule.getKey(), entry.getValue());
          continue OUT;
        }
      }

      result.put(entry.getKey(), entry.getValue());
    }

    this.types = result;
  }
}