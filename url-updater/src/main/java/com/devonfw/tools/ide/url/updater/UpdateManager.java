package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.updater.aws.AWSCrawler;
import com.devonfw.tools.ide.url.updater.az.AzureCrawler;
import com.devonfw.tools.ide.url.updater.cobigen.CobigenCrawler;
import com.devonfw.tools.ide.url.updater.docker.DockerCrawler;
import com.devonfw.tools.ide.url.updater.dotnet.DotNetCrawler;
import com.devonfw.tools.ide.url.updater.eclipse.*;
import com.devonfw.tools.ide.url.updater.gcviewer.GCViewerCrawler;
import com.devonfw.tools.ide.url.updater.gh.GHCrawler;
import com.devonfw.tools.ide.url.updater.graalvm.GraalVMCrawler;
import com.devonfw.tools.ide.url.updater.gradle.GradleCrawler;
import com.devonfw.tools.ide.url.updater.helm.HelmCrawler;
import com.devonfw.tools.ide.url.updater.intellij.IntelliJCommunityEditionCrawler;
import com.devonfw.tools.ide.url.updater.intellij.IntelliJUltimateEditionCrawler;
import com.devonfw.tools.ide.url.updater.java.JavaCrawler;
import com.devonfw.tools.ide.url.updater.jenkins.JenkinsCrawler;
import com.devonfw.tools.ide.url.updater.lazydocker.LazyDockerCrawler;
import com.devonfw.tools.ide.url.updater.maven.MavenCrawler;
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
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;

import java.nio.file.Path;

public class UpdateManager {
    private final UrlRepository urlRepository;

    public UpdateManager(Path pathToRepository) {
        this.urlRepository = UrlRepository.load(pathToRepository);
    }

    private final AWSCrawler awsCrawler = new AWSCrawler();
    private final AzureCrawler azureCrawler = new AzureCrawler();
    private final CobigenCrawler cobigenCrawler = new CobigenCrawler();
    private final DotNetCrawler dotNetCrawler = new DotNetCrawler();
    private final DockerCrawler dockerCrawler = new DockerCrawler();
    private final EclipseCppCrawler eclipseCppCrawler = new EclipseCppCrawler();

    private final EclipseJavaCrawler eclipseJavaCrawler = new EclipseJavaCrawler();

    private final GCViewerCrawler gcViewerCrawler = new GCViewerCrawler();

    private final GHCrawler ghCrawler = new GHCrawler();

    private final GraalVMCrawler graalvmCrawler = new GraalVMCrawler();

    private final GradleCrawler gradleCrawler = new GradleCrawler();

    private final HelmCrawler helmCrawler = new HelmCrawler();

    private final IntelliJUltimateEditionCrawler intellijUltimateCrawler = new IntelliJUltimateEditionCrawler();

    private final IntelliJCommunityEditionCrawler intellijCommunityEditionCrawler = new IntelliJCommunityEditionCrawler();

    private final JavaCrawler javaCrawler = new JavaCrawler();

    private final JenkinsCrawler jenkinsCrawler = new JenkinsCrawler();

    private final LazyDockerCrawler lazyDockerCrawler = new LazyDockerCrawler();

    private final MavenCrawler mavenCrawler = new MavenCrawler();

    private final NodeJsCrawler nodeJSCrawler = new NodeJsCrawler();

    private final NpmCrawler npmCrawler = new NpmCrawler();

    private final OcCrawler ocCrawler = new OcCrawler();

    private final PipCrawler pipCrawler = new PipCrawler();

    private final PythonCrawler pythonCrawler = new PythonCrawler();

    private final QuarkusCrawler quarkusCrawler = new QuarkusCrawler();

    private final RancherCrawler rancherCrawler = new RancherCrawler();

    private final SonarqubeCrawler sonarqubeCrawler = new SonarqubeCrawler();

    private final TerraformCrawler terraformCrawler = new TerraformCrawler();

    private final VSCodeCrawler vsCodeCrawler = new VSCodeCrawler();

    public void updateAll() {
        awsCrawler.update(urlRepository);
        azureCrawler.update(urlRepository);
        cobigenCrawler.update(urlRepository);
        dotNetCrawler.update(urlRepository);
        dockerCrawler.update(urlRepository);
        eclipseCppCrawler.update(urlRepository);
        eclipseJavaCrawler.update(urlRepository);
        gcViewerCrawler.update(urlRepository);
        ghCrawler.update(urlRepository);
        graalvmCrawler.update(urlRepository);
        gradleCrawler.update(urlRepository);
        helmCrawler.update(urlRepository);
        javaCrawler.update(urlRepository);
        intellijUltimateCrawler.update(urlRepository);
        intellijCommunityEditionCrawler.update(urlRepository);
        jenkinsCrawler.update(urlRepository);
        lazyDockerCrawler.update(urlRepository);
        mavenCrawler.update(urlRepository);
        nodeJSCrawler.update(urlRepository);
        npmCrawler.update(urlRepository);
        ocCrawler.update(urlRepository);
        pipCrawler.update(urlRepository);
        pythonCrawler.update(urlRepository);
        quarkusCrawler.update(urlRepository);
        rancherCrawler.update(urlRepository);
        sonarqubeCrawler.update(urlRepository);
        terraformCrawler.update(urlRepository);
        vsCodeCrawler.update(urlRepository);
    }

//    public void updateOnlyExistingVersions() {
//        awsCrawler.updateExistingVersions(urlRepository);
//        azureCrawler.updateExistingVersions(urlRepository);
//        cobigenCrawler.updateExistingVersions(urlRepository);
//        dotNetCrawler.updateExistingVersions(urlRepository);
//        dockerCrawler.updateExistingVersions(urlRepository);
//        eclipseCppCrawler.updateExistingVersions(urlRepository);
//        eclipseJavaCrawler.updateExistingVersions(urlRepository);
//        gcViewerCrawler.updateExistingVersions(urlRepository);
//        ghCrawler.updateExistingVersions(urlRepository);
//        graalvmCrawler.updateExistingVersions(urlRepository);
//        gradleCrawler.updateExistingVersions(urlRepository);
//        helmCrawler.updateExistingVersions(urlRepository);
//        javaCrawler.updateExistingVersions(urlRepository);
//        intellijUltimateCrawler.updateExistingVersions(urlRepository);
//        intellijCommunityEditionCrawler.updateExistingVersions(urlRepository);
//        jenkinsCrawler.updateExistingVersions(urlRepository);
//        lazyDockerCrawler.updateExistingVersions(urlRepository);
//        mavenCrawler.updateExistingVersions(urlRepository);
//        nodeJSCrawler.updateExistingVersions(urlRepository);
//        npmCrawler.updateExistingVersions(urlRepository);
//        ocCrawler.updateExistingVersions(urlRepository);
//        pipCrawler.updateExistingVersions(urlRepository);
//        pythonCrawler.updateExistingVersions(urlRepository);
//        quarkusCrawler.updateExistingVersions(urlRepository);
//        rancherCrawler.updateExistingVersions(urlRepository);
//        sonarqubeCrawler.updateExistingVersions(urlRepository);
//        terraformCrawler.updateExistingVersions(urlRepository);
//        vsCodeCrawler.updateExistingVersions(urlRepository);
//    }

}
