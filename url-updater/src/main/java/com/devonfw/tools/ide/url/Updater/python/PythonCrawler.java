package com.devonfw.tools.ide.url.Updater.python;

import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSTypes;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PythonCrawler extends WebsiteVersionCrawler {
    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(3\\.\\d+\\.\\d+)");
    }

    @Override
    protected String getToolName() {
        return "Python";
    }

    @Override
    protected String getEdition() {
        return "Python";
    }

    @Override
    protected String getVersionUrl() {
        return "https://www.python.org/ftp/python/";
    }

    @Override
    protected List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://www.python.org/ftp/python/${version}/Python-${version}${os}.${ext}");
        downloadUrls.add("https://www.python.org/ftp/python/${version}/python-${version}.${ext}");
        return downloadUrls;
    }

    @Override
    protected Mappings getMappings() {
        Mappings mappings = new Mappings();
        mappings.oses.put(OSTypes.WINDOWS,"-embed-win32");
        mappings.extensions.put(OSTypes.WINDOWS, "zip");
        mappings.extensions.put(OSTypes.LINUX, "tgz");
        mappings.extensions.put(OSTypes.MAC, "tgz");
        return mappings;
    }
}
