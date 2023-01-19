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

public class UpdateManager {
    private final UrlRepository urlRepository;

    public UpdateManager(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private AWSCrawler awsCrawler = new AWSCrawler();
    private AzureCrawler azureCrawler = new AzureCrawler();

    private CobigenCrawler cobigenCrawler = new CobigenCrawler();
    private DotNetCrawler dotNetCrawler = new DotNetCrawler();
    private DockerCrawler dockerCrawler = new DockerCrawler();
    private EclipseCppCrawler eclipseCppCrawler = new EclipseCppCrawler();

    private EclipseJavaCrawler eclipseJavaCrawler = new EclipseJavaCrawler();

    private GCViewerCrawler gcViewerCrawler = new GCViewerCrawler();

    private GHCrawler ghCrawler = new GHCrawler();

    private GraalVMCrawler graalvmCrawler = new GraalVMCrawler();

    private GradleCrawler gradleCrawler = new GradleCrawler();

    private HelmCrawler helmCrawler = new HelmCrawler();

    private IntelliJUltimateEditionCrawler intellijUltimateCrawler = new IntelliJUltimateEditionCrawler();

    private IntelliJCommunityEditionCrawler intellijCommunityEditionCrawler = new IntelliJCommunityEditionCrawler();

    private JavaCrawler javaCrawler = new JavaCrawler();

    private JenkinsCrawler jenkinsCrawler = new JenkinsCrawler();

    private LazyDockerCrawler lazyDockerCrawler = new LazyDockerCrawler();

    private MavenCrawler mavenCrawler = new MavenCrawler();

    private NodeJsCrawler nodeJSCrawler = new NodeJsCrawler();

    private NpmCrawler npmCrawler = new NpmCrawler();

    private OcCrawler ocCrawler = new OcCrawler();

    private PipCrawler pipCrawler = new PipCrawler();

    private PythonCrawler pythonCrawler = new PythonCrawler();

    private QuarkusCrawler quarkusCrawler = new QuarkusCrawler();

    private RancherCrawler rancherCrawler = new RancherCrawler();

    private SonarqubeCrawler sonarqubeCrawler = new SonarqubeCrawler();

    private TerraformCrawler terraformCrawler = new TerraformCrawler();

    private VSCodeCrawler vsCodeCrawler = new VSCodeCrawler();

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

}
