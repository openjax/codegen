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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TemplatesTest {
  @Test
  public void test() throws IOException {
    final String one = process("One.java");
    final String two = process("Two.java");
    assertEquals(one, two);
  }

  public String process(final String fileName) throws IOException {
    final URL url = ClassLoader.getSystemClassLoader().getResource(fileName);
    final Map<String,String> paramToType = new HashMap<>();
    paramToType.put("t", "byte");
    paramToType.put("T", "Byte");

    final Set<String> imports = new HashSet<>();
    imports.add("java.util.Objects");
    return Templates.render(new String(Files.readAllBytes(new File(url.getFile()).toPath())), paramToType, imports);
  }
}