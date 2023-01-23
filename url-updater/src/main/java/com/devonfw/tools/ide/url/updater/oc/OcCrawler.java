package com.devonfw.tools.ide.url.updater.oc;

import com.devonfw.tools.ide.url.updater.OSType;
import com.devonfw.tools.ide.url.updater.WebsiteCrawler;
import com.devonfw.tools.ide.url.folderhandling.UrlVersion;

import java.util.regex.Pattern;

public class OcCrawler extends WebsiteCrawler {
    @Override
    protected String getToolName() {
        return "oc";
    }

    @Override
    protected void updateVersion(UrlVersion urlVersion) {
        doUpdateVersion(urlVersion, "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-windows-${version}.zip", OSType.WINDOWS);
        doUpdateVersion(urlVersion, "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-linux-${version}.tar.gz", OSType.LINUX);
        doUpdateVersion(urlVersion, "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/${version}/openshift-client-mac-${version}.tar.gz", OSType.MAC);

    }

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("(\\d\\.\\d\\.\\d*)");
    }

    @Override
    protected String getVersionUrl() {
        return "https://mirror.openshift.com/pub/openshift-v4/clients/ocp/";
    }
}
