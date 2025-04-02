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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Generated;

import org.libj.lang.Strings;

public final class Templates {
  private static final String GENERATED = "(value=\"" + Templates.class.getName() + "\", date=\"" + LocalDateTime.now().toString() + "\")";
  private static final String[] prefixes = {"static", "java", "javax", "org", "com"};

  private static final Comparator<String> importsComparator = (o1, o2) -> {
    if (o1 == null)
      return o2 == null ? 0 : -1;

    if (o2 == null)
      return 1;

    for (final String prefix : prefixes) { // [A]
      if (o1.startsWith(prefix))
        return o2.startsWith(prefix) ? o1.compareTo(o2) : -1;

      if (o2.startsWith(prefix))
        return 1;
    }

    return o1.compareTo(o2);
  };

  private Templates() {
  }

  private static String importsToString(final ArrayList<String> imports) {
    final int size = imports.size();
    if (size == 0)
      return "";

    imports.sort(importsComparator);

    String group = null;
    final StringBuilder builder = new StringBuilder();
    for (int i = 0, i$ = imports.size(); i < i$; ++i) { // [RA]
      if (i > 0)
        builder.append('\n');

      final String imp = imports.get(i);
      final int dot = imp.indexOf('.');
      if (dot != -1) {
        final String basePackage = imp.substring(0, dot);
        if (i > 0 && !basePackage.equals(group))
          builder.append('\n');

        group = basePackage;
      }

      builder.append("import ").append(imp).append(';');
    }

    return builder.toString();
  }

  private static boolean isBoundaryChar(final String str, final int i, final boolean isStart) {
    if (isStart ? i == 0 : str.length() == i)
      return true;

    final char ch = str.charAt(i + (isStart ? -1 : 0));
    return Character.isWhitespace(ch) || ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '[' || ch == ']' || ch == '<' || ch == '>' || ch == ',' || ch == ';' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '&' || ch == '!' || ch == '%' || ch == '.' || ch == '@';
  }

  private static int matches(final String line, final int fromIndex, final String ... words) {
    for (final String word : words) { // [A]
      final int i = line.indexOf(word, fromIndex);
      if (i != -1 && isBoundaryChar(line, i, true) && isBoundaryChar(line, i + word.length(), false))
        return i;
    }

    return -1;
  }

  public static String render(String str, final Map<String,String> paramToType) {
    if (paramToType.size() > 0) {
      for (final Map.Entry<String,String> entry : paramToType.entrySet()) { // [S]
        final String value = entry.getValue() != null ? entry.getValue() : "";
        str = str.replace("<" + entry.getKey() + ">", value);
      }
    }

    return str;
  }

  // FIXME: It's possible that this method will return a position inside a
  // FIXME: comment, if any of the words it's looking for are matched there.
  // FIXME: Need to detect that we're in a comment, and gloss past it.
  private static int getClassDeclarationStart(final String str, final int fromIndex) {
    int index = -1;
    for (int i = fromIndex, from = fromIndex; i >= 0; --i) { // [N]
      final char ch = str.charAt(i);
      if (Character.isWhitespace(ch) || i == 0) {
        if (i != from - 1) {
          final String word = str.substring(i + 1, from);
          if ("public".equals(word) || "abstract".equals(word) || "final".equals(word))
            index = i + 1;
          else if (index != -1)
            break;
        }

        from = i;
      }
    }

    return index == -1 ? fromIndex : index;
  }

  private static void filterForImports(final String line, final Set<String> shortNames, final HashSet<String> seenImports) {
    if (shortNames.size() > 0)
      for (final String shortName : shortNames) // [S]
        if (matches(line, 0, shortName) != -1)
          seenImports.add(shortName);
  }

  // FIXME: This is a broken implementation.
  // FIXME: It does not properly handle multi-line "{@link " clauses.
  private static void findImportInComment(final String line, final HashSet<String> seenImports) {
    if (!line.startsWith("*"))
      return;

    int start = 0;
    while (true) {
      int index = line.indexOf("{@link ", start);
      if (index == -1)
        index = line.indexOf("{@see ", start);

      if (index == -1)
        break;

      for (int i = index + 7, stage = 0; i <= line.length(); ++i) { // [N]
        final char ch = i == line.length() ? '\0' : line.charAt(i);
        if (stage == 0) {
          if (!Character.isWhitespace(ch)) {
            stage = 1;
            start = i;
          }
        }
        else if (stage == 1) {
          if (Character.isWhitespace(ch) || ch == '}' || ch == '\0') {
            seenImports.add(line.substring(start, i));
            start = i + 1;
            break;
          }
        }
      }
    }
  }

  public static String render(final String template, final Map<String,String> paramToType, HashSet<String> imports) throws IOException {
    if (imports == null)
      imports = new HashSet<>();

    final StringBuilder builder = new StringBuilder();
    try (final BufferedReader fin = new BufferedReader(new StringReader(template))) {
      int packageEnd = 0;
      int importStart = -1;
      int importEnd = -1;
      int classStart = -1;
      LinkedHashMap<String,String> shortNameToFullName = null;
      final HashSet<String> seenImports = new HashSet<>();
      String line;
      boolean hasGenerated = false;
      String packageNameDot = null;
      for (int l = 0; (line = fin.readLine()) != null; ++l) { // [ST]
        if (l > 0)
          builder.append('\n');

        line = render(line, paramToType);
        builder.append(line);

        if (classStart == -1) {
          line = line.trim();
          findImportInComment(line, seenImports);
          if (matches(line, 0, "@Generated") != -1)
            hasGenerated = true;

          int classIndex = matches(line, 0, "class", "interface", "@interface");
          if (classIndex == -1)
            continue;

          if (line.startsWith("*") || line.startsWith("//"))
            continue;

          classIndex += builder.length() - line.length();
          final String header = builder.toString();
          classStart = getClassDeclarationStart(header, classIndex);
          final int packageIndex = matches(header, 0, "package");
          if (packageIndex != -1) {
            packageEnd = header.indexOf(';', packageIndex + 8) + 1;
            packageNameDot = header.substring(packageIndex + 8, packageEnd - 1) + ".";
          }

          for (int start, end = 0; (start = matches(header, end, "import")) != -1;) { // [X]
            if (importStart == -1)
              importStart = start;

            end = header.indexOf(';', start + 7);
            imports.add(header.substring(start + 7, end++).trim());
            importEnd = end;
          }

          final ArrayList<String> list = new ArrayList<>(imports);
          list.sort(importsComparator);

          shortNameToFullName = new LinkedHashMap<>();
          for (int i = 0, i$ = list.size(); i < i$; ++i) { // [RA]
            final String name = list.get(i);
            shortNameToFullName.put(name.substring(name.lastIndexOf('.') + 1), name);
          }
        }

        if (shortNameToFullName == null)
          throw new IllegalStateException("Should not get here");

        filterForImports(line, shortNameToFullName.keySet(), seenImports);
      }

      if (shortNameToFullName == null)
        throw new IllegalStateException("Should not get here");

      final int serialVersionUIDIndex = builder.indexOf("<serialVersionUID>");
      if (serialVersionUIDIndex != -1) {
        final long serialVersionUID = Strings.hashAsLong(builder.substring(classStart));
        builder.replace(serialVersionUIDIndex, serialVersionUIDIndex + 18, serialVersionUID + "L");
      }

      shortNameToFullName.put(Generated.class.getSimpleName(), Generated.class.getName());
      seenImports.add(Generated.class.getSimpleName());
      if (!hasGenerated)
        builder.insert(classStart, "@" + Generated.class.getSimpleName() + GENERATED + "\n");

      shortNameToFullName.keySet().retainAll(seenImports);
      if (packageNameDot != null) {
        final Iterator<String> iterator = shortNameToFullName.values().iterator();
        while (iterator.hasNext()) {
          final String name = iterator.next();
          if (name.startsWith(packageNameDot) && name.indexOf('.', packageNameDot.length() + 1) == -1)
            iterator.remove();
        }
      }

      final String importString = importsToString(new ArrayList<>(shortNameToFullName.values()));
      if (importStart != -1) {
        builder.replace(importStart, importEnd, importString);
      }
      else {
        if (packageEnd > 0) {
          builder.insert(packageEnd, "\n\n");
          packageEnd += 2;
        }

        builder.insert(packageEnd, importString);
      }
    }

    return builder.toString();
  }
}