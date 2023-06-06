package com.devonfw.tools.ide.url.updater;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import com.devonfw.tools.ide.url.updater.intellij.IntellijUrlUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.devonfw.tools.ide.url.model.folder.UrlRepository;

/**
 * The {@code UpdateManager} class manages the update process for various tools by using a list of
 * {@link AbstractUrlUpdater}s to update the {@link UrlRepository}. The list of {@link AbstractUrlUpdater}s contains
 * crawlers for different tools and services, To use the UpdateManager, simply create an instance with the path to the
 * repository as a parameter and call the {@link #updateAll()} method.
 */
public class UpdateManager {

  private static final Logger logger = LoggerFactory.getLogger(AbstractUrlUpdater.class);

  private final UrlRepository urlRepository;

  private final List<AbstractUrlUpdater> updaters = Arrays.asList(new IntellijUrlUpdater());//new IntellijCommunityUrlUpdater(),new IntellijUltimateUrlUpdater());
      /*new AndroidStudioUrlUpdater(), new AwsUrlUpdater(),
      new AzureUrlUpdater(), new CobigenUrlUpdater(), new DotNetUrlUpdater(), new DockerDesktopUrlUpdater(),
      new EclipseCppUrlUpdater(), new EclipseJavaUrlUpdater(), new GcViewerUrlUpdater(), new GhUrlUpdater(),
      new GraalVmUrlUpdater(), new GradleUrlUpdater(), new HelmUrlUpdater(), new IntellijUltimateUrlUpdater(),
      new IntellijCommunityUrlUpdater(), new JavaUrlUpdater(), new JenkinsUrlUpdater(), new KotlincUrlUpdater(),
      new KotlincNativeUrlUpdater(), new LazyDockerUrlUpdater(), new MvnUrlUpdater(), new NodeUrlUpdater(),
      new NpmUrlUpdater(), new OcUrlUpdater(), new PipUrlUpdater(), new PythonUrlUpdater(), new QuarkusUrlUpdater(),
      new DockerRancherDesktopUrlUpdater(), new SonarUrlUpdater(), new TerraformUrlUpdater(), new VsCodeUrlUpdater());*/

  /**
   * The constructor.
   *
   * @param pathToRepository the {@link Path} to the {@code ide-urls} repository to update.
   */
  public UpdateManager(Path pathToRepository) {

    this.urlRepository = UrlRepository.load(pathToRepository);
  }

  /**
   * Updates {@code ide-urls} for all tools their editions and all found versions.
   */
  public void updateAll() {

    for (AbstractUrlUpdater updater : this.updaters) {
      try {
        updater.update(this.urlRepository);
      } catch (Exception e) {
        logger.error("Failed to update {}", updater.getToolWithEdition(), e);
      }
    }
  }

}
