package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.folderhandling.UrlRepository;
import com.devonfw.tools.ide.url.updater.aws.AWSCrawler;
import com.devonfw.tools.ide.url.updater.az.AzureCrawler;
import com.devonfw.tools.ide.url.updater.cobigen.CobigenCrawler;
import com.devonfw.tools.ide.url.updater.docker.DockerCrawler;
import com.devonfw.tools.ide.url.updater.dotnet.DotNetCrawler;
import com.devonfw.tools.ide.url.updater.eclipse.EclipseCppCrawler;
import com.devonfw.tools.ide.url.updater.eclipse.EclipseEclipseCrawler;
import com.devonfw.tools.ide.url.updater.gcviewer.GCViewerCrawler;
import com.devonfw.tools.ide.url.updater.gh.GHCrawler;
import com.devonfw.tools.ide.url.updater.graalvm.GraalVMCrawler;
import com.devonfw.tools.ide.url.updater.gradle.GradleCrawler;
import com.devonfw.tools.ide.url.updater.helm.HelmCrawler;
import com.devonfw.tools.ide.url.updater.intellij.IntelliJIntellijEditionCrawler;
import com.devonfw.tools.ide.url.updater.intellij.IntelliJUltimateEditionCrawler;
import com.devonfw.tools.ide.url.updater.java.JavaCrawler;
import com.devonfw.tools.ide.url.updater.jenkins.JenkinsCrawler;
import com.devonfw.tools.ide.url.updater.kotlin.KotlinCrawler;
import com.devonfw.tools.ide.url.updater.kotlin.KotlinNativeCrawler;
import com.devonfw.tools.ide.url.updater.lazydocker.LazyDockerCrawler;
import com.devonfw.tools.ide.url.updater.mvn.MvnCrawler;
import com.devonfw.tools.ide.url.updater.nodejs.NodeJsCrawler;
import com.devonfw.tools.ide.url.updater.npm.NpmCrawler;
import com.devonfw.tools.ide.url.updater.oc.OcCrawler;
import com.devonfw.tools.ide.url.updater.pip.PipCrawler;
import com.devonfw.tools.ide.url.updater.python.PythonCrawler;
import com.devonfw.tools.ide.url.updater.quarkus.QuarkusCrawler;
import com.devonfw.tools.ide.url.updater.rancher.RancherCrawler;
import com.devonfw.tools.ide.url.updater.sonarqube.SonarqubeCrawler;
import com.devonfw.tools.ide.url.updater.terraform.TerraformCrawler;
import com.devonfw.tools.ide.url.updater.vscode.VSCodeCrawler;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code UpdateManager} class manages the update process for various tools by using a list of {@link AbstractCrawler}s
 * to update the {@link UrlRepository}. The list of {@link AbstractCrawler}s contains crawlers for different tools and services,
 * To use the UpdateManager, simply create an instance with the path to the repository as a parameter and call the {@link #updateAll()} method.
 */
public class UpdateManager {
	private final UrlRepository urlRepository;
	private final List<AbstractCrawler> crawlers = Arrays.asList(new AWSCrawler(), new AzureCrawler(), new CobigenCrawler(), new DotNetCrawler(), new DockerCrawler(), new EclipseCppCrawler(), new EclipseEclipseCrawler(), new GCViewerCrawler(), new GHCrawler(), new GraalVMCrawler(), new GradleCrawler(), new HelmCrawler(), new IntelliJUltimateEditionCrawler(), new IntelliJIntellijEditionCrawler(), new JavaCrawler(), new JenkinsCrawler(), new KotlinCrawler(), new KotlinNativeCrawler(), new LazyDockerCrawler(), new MvnCrawler(), new NodeJsCrawler(), new NpmCrawler(), new OcCrawler(), new PipCrawler(), new PythonCrawler(), new QuarkusCrawler(), new RancherCrawler(), new SonarqubeCrawler(), new TerraformCrawler(), new VSCodeCrawler());

	public UpdateManager(Path pathToRepository) {
		this.urlRepository = UrlRepository.load(pathToRepository);
	}

	public void updateAll() {
		for (AbstractCrawler crawler : crawlers) {
			crawler.update(urlRepository);
		}
	}


}
