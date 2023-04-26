package com.devonfw.tools.ide.url.updater;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.devonfw.tools.ide.url.model.folder.UrlRepository;
import com.devonfw.tools.ide.url.updater.aws.AwsUrlUpdater;
import com.devonfw.tools.ide.url.updater.az.AzureUrlUpdater;
import com.devonfw.tools.ide.url.updater.cobigen.CobigenUrlUpdater;
import com.devonfw.tools.ide.url.updater.docker.DockerDesktopUrlUpdater;
import com.devonfw.tools.ide.url.updater.docker.DockerRancherDesktopUrlUpdater;
import com.devonfw.tools.ide.url.updater.dotnet.DotNetUrlUpdater;
import com.devonfw.tools.ide.url.updater.eclipse.EclipseCppUrlUpdater;
import com.devonfw.tools.ide.url.updater.eclipse.EclipseJavaUrlUpdater;
import com.devonfw.tools.ide.url.updater.gcviewer.GcViewerUrlUpdater;
import com.devonfw.tools.ide.url.updater.gh.GhUrlUpdater;
import com.devonfw.tools.ide.url.updater.graalvm.GraalVmUrlUpdater;
import com.devonfw.tools.ide.url.updater.gradle.GradleUrlUpdater;
import com.devonfw.tools.ide.url.updater.helm.HelmUrlUpdater;
import com.devonfw.tools.ide.url.updater.intellij.IntellijCommunityUrlUpdater;
import com.devonfw.tools.ide.url.updater.intellij.IntellijUltimateUrlUpdater;
import com.devonfw.tools.ide.url.updater.java.JavaUrlUpdater;
import com.devonfw.tools.ide.url.updater.jenkins.JenkinsUrlUpdater;
import com.devonfw.tools.ide.url.updater.kotlinc.KotlincNativeUrlUpdater;
import com.devonfw.tools.ide.url.updater.kotlinc.KotlincUrlUpdater;
import com.devonfw.tools.ide.url.updater.lazydocker.LazyDockerUrlUpdater;
import com.devonfw.tools.ide.url.updater.mvn.MvnUrlUpdater;
import com.devonfw.tools.ide.url.updater.node.NodeUrlUpdater;
import com.devonfw.tools.ide.url.updater.npm.NpmUrlUpdater;
import com.devonfw.tools.ide.url.updater.oc.OcUrlUpdater;
import com.devonfw.tools.ide.url.updater.pip.PipUrlUpdater;
import com.devonfw.tools.ide.url.updater.python.PythonUrlUpdater;
import com.devonfw.tools.ide.url.updater.quarkus.QuarkusUrlUpdater;
import com.devonfw.tools.ide.url.updater.sonar.SonarUrlUpdater;
import com.devonfw.tools.ide.url.updater.terraform.TerraformUrlUpdater;
import com.devonfw.tools.ide.url.updater.vscode.VSCodeUrlUpdater;

/**
 * The {@code UpdateManager} class manages the update process for various tools by using a list of
 * {@link AbstractUrlUpdater}s to update the {@link UrlRepository}. The list of {@link AbstractUrlUpdater}s contains crawlers
 * for different tools and services, To use the UpdateManager, simply create an instance with the path to the repository
 * as a parameter and call the {@link #updateAll()} method.
 */
public class UpdateManager {
  private final UrlRepository urlRepository;

  private final List<AbstractUrlUpdater> crawlers = Arrays.asList(new AwsUrlUpdater(), new AzureUrlUpdater(),
      new CobigenUrlUpdater(), new DotNetUrlUpdater(), new DockerDesktopUrlUpdater(), new EclipseCppUrlUpdater(),
      new EclipseJavaUrlUpdater(), new GcViewerUrlUpdater(), new GhUrlUpdater(), new GraalVmUrlUpdater(), new GradleUrlUpdater(),
      new HelmUrlUpdater(), new IntellijUltimateUrlUpdater(), new IntellijCommunityUrlUpdater(), new JavaUrlUpdater(),
      new JenkinsUrlUpdater(), new KotlincUrlUpdater(), new KotlincNativeUrlUpdater(), new LazyDockerUrlUpdater(), new MvnUrlUpdater(),
      new NodeUrlUpdater(), new NpmUrlUpdater(), new OcUrlUpdater(), new PipUrlUpdater(), new PythonUrlUpdater(), new QuarkusUrlUpdater(),
      new DockerRancherDesktopUrlUpdater(), new SonarUrlUpdater(), new TerraformUrlUpdater(), new VSCodeUrlUpdater());

  public UpdateManager(Path pathToRepository) {

    this.urlRepository = UrlRepository.load(pathToRepository);
  }

  public void updateAll() {

    for (AbstractUrlUpdater crawler : crawlers) {
      crawler.update(urlRepository);
    }
  }

}
