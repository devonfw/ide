package com.devonfw.tools.ide.url.updater;

import com.devonfw.tools.ide.url.folderhandling.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Set;

public abstract class AbstractCrawler implements Updater {
    protected final HttpClient client = HttpClient.newBuilder().build();

    private static final Logger logger = LoggerFactory.getLogger(AbstractCrawler.class);

    protected String doGetResponseBody(String url) {
        try {
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            return client.send(request1, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Failed to retrieve response body from url: " + url, exception);
        }catch (IllegalArgumentException e){
            logger.error("Error while getting response body from url {}",url, e);
            return "";
        }
    }
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OSType os, String arch, String edition){
        String version = urlVersion.getName();
        String osString = null;
        downloadUrl = downloadUrl.replace("${version}", version);
        if(os!=null) {
            osString = os.toString();
            downloadUrl = downloadUrl.replace("${os}", osString);
        }
        if(arch!=null) {
            downloadUrl = downloadUrl.replace("${arch}", arch);
        }
        if(edition!=null) {
            downloadUrl = downloadUrl.replace("${edition}", edition);
        }
        Result resultOfHttpRequest= doCheckIfDownloadUrlWorks(downloadUrl);
        if(resultOfHttpRequest.isSuccess()){
            UrlDownloadFile urlDownloadFile = urlVersion.getOrCreateUrls(osString,arch);
            urlDownloadFile.addUrl(downloadUrl);
            urlVersion.save();
            return true;
        }else {
            return false;
        }
    }
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OSType os, String arch){
       return doUpdateVersion(urlVersion,downloadUrl,os,arch,null);
    }
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl, OSType os) {
        return doUpdateVersion(urlVersion, downloadUrl, os, null);
    }


        /**
         * Operating system independent
         */
    protected boolean doUpdateVersion(UrlVersion urlVersion, String downloadUrl){
        return doUpdateVersion(urlVersion, downloadUrl, null);
    }

    protected Result doCheckIfDownloadUrlWorks(String downloadUrl) {
        // Do Head request to check if the download url works and if the file is available for download
        Result result;
        Level logLevel=Level.INFO;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(downloadUrl))
                    .method("HEAD", HttpRequest.BodyPublishers.noBody())
                    .timeout(Duration.ofSeconds(5))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Return the success or failure result
            result = response.statusCode() >= 200 && response.statusCode() < 400
                    ? new Result(true, response.statusCode(), downloadUrl)
                    : new Result(false, response.statusCode(), downloadUrl);
        } catch (IOException | InterruptedException e) {

            result = new Result(false, 500, downloadUrl);
            //TODO: Für erfolgreichen Request im status json speichern
        }
        if(result.isFailure()){
            logLevel=Level.WARN;
        }
        logger.atLevel(logLevel).log("Download url: {} is {} with status code: {}", downloadUrl, result.isSuccess() ? "available" : "not available", result.getHttpStatusCode());
        return result;

    }

    protected abstract String getToolName();

    protected String getEdition(){
        return getToolName();
    }

    protected abstract void updateVersion(UrlVersion urlVersion);
    /**
     * @param version original version.
     * @return transformed JavaJsonVersion or null to filter and ignore the version
     */
    protected String mapVersion(String version){
        return version;
    }

    protected abstract Set<String> getVersions();


    @Override
    public void update(UrlRepository urlRepository) {
        UrlTool tool = urlRepository.getOrCreateChild(getToolName());
        UrlEdition edition = tool.getOrCreateChild(getEdition());
        Set<String> versions = getVersions();
        for (String version : versions) {
            version = mapVersion(version);
            if (version!=null && edition.getChild(version) == null) {
                UrlVersion urlVersion = edition.getOrCreateChild(version);
                updateVersion(urlVersion);
            }
        }
    }

    protected void updateExistingVersions(UrlRepository urlRepository) {
        UrlTool tool = urlRepository.getOrCreateChild(getToolName());
        UrlEdition edition = tool.getOrCreateChild(getEdition());
        edition.getListOfAllChildren().forEach(version -> updateVersion(edition.getChild(version)));
    }

}