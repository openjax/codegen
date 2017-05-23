/* Copyright (c) 2014 lib4j
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

package org.libx4j.maven.plugin.codegen;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.safris.commons.lang.Paths;
import org.safris.commons.search.ISTEnumGenerator;

@Mojo(name = "istenum", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
@Execute(goal = "istenum")
public final class ISTEnumGeneratorMojo extends AbstractMojo {
  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  @Parameter(defaultValue = "${mojoExecution}", readonly = true)
  private MojoExecution execution;

  @Parameter(property = "maven.test.skip", defaultValue = "false")
  private boolean mavenTestSkip;

  @Parameter(property = "file")
  private File file;

  @Parameter(property = "dir")
  private String dir;

  @Parameter(property = "className")
  private String className;

  @Parameter(property = "inheritsFrom")
  private String inheritsFrom;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (mavenTestSkip && execution.getLifecyclePhase() != null && execution.getLifecyclePhase().contains("test"))
      return;

    final File destDir = Paths.isAbsolute(dir) ? new File(dir) : new File(project.getBuild().getDirectory(), dir);
    try {
      ISTEnumGenerator.generate(className, inheritsFrom, new File(destDir, className.replace('.', '/') + ".java"), file);
    }
    catch (final IOException e) {
      throw new MojoExecutionException(e.getMessage(), e);
    }

    project.addTestCompileSourceRoot(destDir.getAbsolutePath());
    project.addCompileSourceRoot(destDir.getAbsolutePath());
  }
}