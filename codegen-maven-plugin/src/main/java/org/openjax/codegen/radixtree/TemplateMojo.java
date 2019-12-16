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

package org.openjax.codegen.radixtree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.openjax.codegen.template.Templates;

/**
 * Maven MOJO for {@link Templates}.
 */
@Mojo(name="template", defaultPhase=LifecyclePhase.GENERATE_SOURCES)
@Execute(goal="template")
public final class TemplateMojo extends AbstractMojo {
  @Parameter(property="templates", required=true)
  private List<File> templates;

  @Parameter(property="destDir", required=true)
  private File destDir;

  @Parameter(property="parameters", required=true)
  private Map<String,Parameters> parameters;

  @Parameter(defaultValue="${project}", readonly=true, required=true)
  private MavenProject project;

  @Parameter(property="overwrite")
  private boolean overwrite = true;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      getLog().info("overwrite=" + overwrite);
      for (final File template : templates) {
        getLog().info("Template: " + template.getPath());
        for (final Map.Entry<String,Parameters> entry : parameters.entrySet()) {
          final Parameters parameters = entry.getValue();
          final String rendered = Templates.render(template, parameters.getTypes(), parameters.getImports() == null ? null : new HashSet<>(parameters.getImports()));
          final String fileName = Templates.render(template.getName(), parameters.getTypes());
          final File outFile = new File(destDir, fileName);
          getLog().info("Writing \"" + entry.getKey() + "\": " + outFile.getPath());
          if (outFile.exists() && !overwrite) {
            final String content = new String(Files.readAllBytes(outFile.toPath()));
            if (!content.equals(rendered))
              throw new MojoExecutionException("Content mismatch for: " + outFile.getAbsolutePath());
          }
          else {
            destDir.mkdirs();
            Files.write(outFile.toPath(), rendered.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
          }
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