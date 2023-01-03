package com.devonfw.tools.ide.url.Updater;

import com.devonfw.tools.ide.url.Updater.aws.AWSCrawler;
import com.devonfw.tools.ide.url.Updater.az.AzureCrawler;
import com.devonfw.tools.ide.url.Updater.docker.DockerCrawler;
import com.devonfw.tools.ide.url.Updater.dotnet.DotNetCrawler;
import com.devonfw.tools.ide.url.Updater.eclipse.*;
import com.devonfw.tools.ide.url.Updater.gcviewer.GCViewerCrawler;
import com.devonfw.tools.ide.url.Updater.gh.GHCrawler;
import com.devonfw.tools.ide.url.Updater.graalvm.GraalvmCrawler;
import com.devonfw.tools.ide.url.Updater.gradle.GradleCrawler;
import com.devonfw.tools.ide.url.Updater.helm.HelmCrawler;
import com.devonfw.tools.ide.url.Updater.intellij.IntellijCommunityEditionCrawler;
import com.devonfw.tools.ide.url.Updater.intellij.IntellijUltimateCrawler;
import com.devonfw.tools.ide.url.Updater.jenkins.JenkinsCrawler;
import com.devonfw.tools.ide.url.Updater.lazydocker.LazyDockerCrawler;
import com.devonfw.tools.ide.url.Updater.maven.MavenCrawler;
import com.devonfw.tools.ide.url.Updater.nodejs.NodeJSCrawler;
import com.devonfw.tools.ide.url.Updater.npm.NpmCrawler;
import com.devonfw.tools.ide.url.Updater.oc.OcCrawler;
import com.devonfw.tools.ide.url.Updater.pip.PipCrawler;
import com.devonfw.tools.ide.url.Updater.python.PythonCrawler;
import com.devonfw.tools.ide.url.Updater.quarkus.QuarkusCrawler;
import com.devonfw.tools.ide.url.Updater.rancher.RancherCrawler;
import com.devonfw.tools.ide.url.Updater.sonarqube.SonarqubeCrawler;
import com.devonfw.tools.ide.url.Updater.terraform.TerraformCrawler;
import com.devonfw.tools.ide.url.Updater.vscode.VSCodeCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;

public class UpdateManager {
    private final UrlRepository urlRepository;

    public UpdateManager(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private AWSCrawler awsCrawler = new AWSCrawler();
    private AzureCrawler azureCrawler = new AzureCrawler();
    private DotNetCrawler dotNetCrawler = new DotNetCrawler();

    private DockerCrawler dockerCrawler = new DockerCrawler();
    private EclipseCommitersCrawler eclipseCommitersCrawler = new EclipseCommitersCrawler();
    private EclipseCppCrawler eclipseCppCrawler = new EclipseCppCrawler();

    private EclipseDslCrawler eclipseDslCrawler = new EclipseDslCrawler();

    private EclipseEmbedCppCrawler eclipseEmbedCppCrawler = new EclipseEmbedCppCrawler();

    private EclipseJavaCrawler eclipseJavaCrawler = new EclipseJavaCrawler();

    private EclipseJeeCrawler eclipseJeeCrawler = new EclipseJeeCrawler();
    private EclipseModelingCrawler eclipseModelingCrawler = new EclipseModelingCrawler();

    private EclipseParallelCrawler eclipseParallelCrawler = new EclipseParallelCrawler();
    private EclipsePhpCrawler eclipsePhpCrawler = new EclipsePhpCrawler();

    private EclipseRcpCrawler eclipseRcpCrawler = new EclipseRcpCrawler();

    private EclipseScoutCrawler eclipseScoutCrawler = new EclipseScoutCrawler();

    private GCViewerCrawler gcViewerCrawler = new GCViewerCrawler();

    private GHCrawler ghCrawler = new GHCrawler();

    private GraalvmCrawler graalvmCrawler = new GraalvmCrawler();

    private GradleCrawler gradleCrawler = new GradleCrawler();

    private HelmCrawler helmCrawler = new HelmCrawler();

    private IntellijUltimateCrawler intellijUltimateCrawler = new IntellijUltimateCrawler();

    private IntellijCommunityEditionCrawler intellijCommunityEditionCrawler = new IntellijCommunityEditionCrawler();

    //TODO: ADD java Crawler

    private JenkinsCrawler jenkinsCrawler = new JenkinsCrawler();

    private LazyDockerCrawler lazyDockerCrawler = new LazyDockerCrawler();

    private MavenCrawler mavenCrawler = new MavenCrawler();

    private NodeJSCrawler nodeJSCrawler = new NodeJSCrawler();

    private NpmCrawler npmCrawler = new NpmCrawler();

    private OcCrawler ocCrawler = new OcCrawler();

    private PipCrawler pipCrawler = new PipCrawler();

    private PythonCrawler pythonCrawler = new PythonCrawler();

    private QuarkusCrawler quarkusCrawler = new QuarkusCrawler();

    private RancherCrawler rancherCrawler = new RancherCrawler();

    private SonarqubeCrawler sonarqubeCrawler = new SonarqubeCrawler();

    private TerraformCrawler terraformCrawler = new TerraformCrawler();

    private VSCodeCrawler vsCodeCrawler = new VSCodeCrawler();

    public void doUpdateAll() {
        awsCrawler.doUpdate(urlRepository);
        azureCrawler.doUpdate(urlRepository);
        dotNetCrawler.doUpdate(urlRepository);
        dockerCrawler.doUpdate(urlRepository);
        eclipseCommitersCrawler.doUpdate(urlRepository);
        eclipseCppCrawler.doUpdate(urlRepository);
        eclipseDslCrawler.doUpdate(urlRepository);
        eclipseEmbedCppCrawler.doUpdate(urlRepository);
        eclipseJavaCrawler.doUpdate(urlRepository);
        eclipseJeeCrawler.doUpdate(urlRepository);
        eclipseModelingCrawler.doUpdate(urlRepository);
        eclipseParallelCrawler.doUpdate(urlRepository);
        eclipsePhpCrawler.doUpdate(urlRepository);
        eclipseRcpCrawler.doUpdate(urlRepository);
        eclipseScoutCrawler.doUpdate(urlRepository);
        gcViewerCrawler.doUpdate(urlRepository);
        ghCrawler.doUpdate(urlRepository);
        graalvmCrawler.doUpdate(urlRepository);
        gradleCrawler.doUpdate(urlRepository);
        helmCrawler.doUpdate(urlRepository);
        //TODO: Add Missing Tools here, Java.
        intellijUltimateCrawler.doUpdate(urlRepository);
        intellijCommunityEditionCrawler.doUpdate(urlRepository);
        jenkinsCrawler.doUpdate(urlRepository);
        lazyDockerCrawler.doUpdate(urlRepository);
        mavenCrawler.doUpdate(urlRepository);
        nodeJSCrawler.doUpdate(urlRepository);
        npmCrawler.doUpdate(urlRepository);
        ocCrawler.doUpdate(urlRepository);
        pipCrawler.doUpdate(urlRepository);
        pythonCrawler.doUpdate(urlRepository);
        quarkusCrawler.doUpdate(urlRepository);
        rancherCrawler.doUpdate(urlRepository);
        sonarqubeCrawler.doUpdate(urlRepository);
        terraformCrawler.doUpdate(urlRepository);
        vsCodeCrawler.doUpdate(urlRepository);
    }

}
