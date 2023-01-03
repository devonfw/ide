package com.devonfw.tools.ide.url;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.UpdateManager;
import com.devonfw.tools.ide.url.Updater.aws.AWSCrawler;
import com.devonfw.tools.ide.url.Updater.az.AzureCrawler;
import com.devonfw.tools.ide.url.Updater.cobigen.CobigenUpdater;
import com.devonfw.tools.ide.url.Updater.docker.DockerCrawler;
import com.devonfw.tools.ide.url.Updater.dotnet.DotNetCrawler;
import com.devonfw.tools.ide.url.Updater.eclipse.EclipseCrawler;
import com.devonfw.tools.ide.url.Updater.eclipse.EclipseJavaCrawler;
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
import com.devonfw.tools.ide.url.Updater.python.PythonCrawler;
import com.devonfw.tools.ide.url.Updater.quarkus.QuarkusCrawler;
import com.devonfw.tools.ide.url.Updater.rancher.RancherCrawler;
import com.devonfw.tools.ide.url.Updater.sonarqube.SonarqubeCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        String pathToRepo ="I:\\UrlRepoTest";
        UrlRepository urlRepository = new UrlRepository(Path.of(pathToRepo));
//        UpdateManager updateManager = new UpdateManager(urlRepository);
//        updateManager.doUpdateAll();
        CobigenUpdater cobigenUpdater = new CobigenUpdater();
        cobigenUpdater.doUpdate(urlRepository);




    }
}