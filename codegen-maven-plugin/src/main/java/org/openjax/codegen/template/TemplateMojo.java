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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Maven MOJO for {@link Templates}.
 */
/**
 *
 */
@Mojo(name="template", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
@Execute(goal="template")
public final class TemplateMojo extends AbstractMojo {
  private static class Raster {
    private static final Pattern pattern = Pattern.compile("<.>");

    final Path path;
    int variables;
    String name;
    String content;
    List<String> keys;

    private Raster(final Raster copy, final String key) {
      this.path = copy.path;
      this.variables = copy.variables;
      this.name = copy.name;
      this.content = copy.content;
      this.keys = copy.keys != null ? new ArrayList<>(copy.keys) : new ArrayList<>();
      this.keys.add(key);
    }

    private Raster(final File file) throws IOException {
      this.path = file.toPath();
      this.name = file.getName();
      this.content = new String(Files.readAllBytes(path));

      final Set<String> set = new HashSet<>();
      final Matcher matcher = pattern.matcher(name);
      while (matcher.find())
        set.add(matcher.group().substring(1, 2));

      this.variables = set.size();
    }
  }

  private static boolean shouldSkip(final List<String> keys, final List<List<String>> skips) {
    for (final List<String> skip : skips)
      if (keys.equals(skip))
        return true;

    return false;
  }

  @Parameter(property="templates", required=true)
  private List<File> templates;

  @Parameter(property="destDir", required=true)
  private File destDir;

  @Parameter(property="parameters", required=true)
  private Map<String,Parameters> parameters;

  @Parameter(property="skips", required=false)
  private List<String> skips;

  @Parameter(property="alias", required=false)
  private Map<String,String> alias;

  @Parameter(defaultValue="${project}", readonly=true, required=true)
  private MavenProject project;

  @Parameter(property="overwrite")
  private boolean overwrite = true;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      final List<List<String>> skips = new ArrayList<>();
      for (final String skip : this.skips)
        skips.add(Arrays.asList(skip.split(",")));

      getLog().info("overwrite=" + overwrite);
      final List<Raster> ins = new ArrayList<>(templates.size());
      for (final File template : templates)
        ins.add(new Raster(template));

      final List<Raster> outs = new ArrayList<>();
      final List<Raster> outs2 = new ArrayList<>();
      do {
        for (final Raster in : ins) {
          for (final Map.Entry<String,Parameters> entry : parameters.entrySet()) {
            final Raster out = new Raster(in, entry.getKey());
            final Parameters params = entry.getValue();

            out.content = Templates.render(out.content, params.getTypes(), params.getImports() == null ? null : new HashSet<>(params.getImports()));
            out.name = Templates.render(out.name, params.getTypes());
            (out.variables-- == 1 ? outs : outs2).add(out);
          }
        }

        if (outs2.isEmpty())
          break;

        ins.clear();
        ins.addAll(outs2);
        outs2.clear();
        for (final Parameters params : parameters.values())
          params.remap(alias);
      }
      while (true);

      for (final Raster out : outs) {
        final File outFile = new File(destDir, out.name);
        if (shouldSkip(out.keys, skips)) {
          getLog().info("Skipping \"" + out.keys + "\"");
          continue;
        }

        getLog().info("Writing \"" + out.keys + "\": " + outFile.getPath());
        if (outFile.exists() && !overwrite) {
          final String content = new String(Files.readAllBytes(outFile.toPath()));
          if (!content.equals(out.content))
            throw new MojoExecutionException("Content mismatch for: " + outFile.getAbsolutePath());
        }
        else {
          destDir.mkdirs();
          Files.write(outFile.toPath(), out.content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
      }

      // FIXME: Should this be differentiated? Like in GeneratorMojo?
      project.addTestCompileSourceRoot(destDir.getAbsolutePath());
      project.addCompileSourceRoot(destDir.getAbsolutePath());
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getClass().getSimpleName() + ": " + e.getMessage(), e);
    }
  }
}