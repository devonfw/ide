package com.devonfw.tools.ide.url.Updater.eclipse;


import com.devonfw.tools.ide.url.Updater.Mappings;
import com.devonfw.tools.ide.url.Updater.OSType;
import com.devonfw.tools.ide.url.Updater.WebsiteVersionCrawler;


import java.util.*;
import java.util.regex.Pattern;


public abstract class EclipseCrawler extends WebsiteVersionCrawler {


    @Override
    public Mappings getMappings() {
        HashMap<OSType, String> oses = new HashMap<>();
        oses.put(OSType.WINDOWS, "win32");
        oses.put(OSType.LINUX, "linux-gtk");
        oses.put(OSType.MAC, "macosx-cocoa");
        HashMap<String, String> architectures = new HashMap<>();
        architectures.put("x64", "x86_64");
        architectures.put("arm64", "aarch64");
        HashMap<OSType, String> extension = new HashMap<>();
        extension.put(OSType.WINDOWS, "zip");
        extension.put(OSType.LINUX, "tar.gz");
        extension.put(OSType.MAC, "dmg");
        Mappings mappings = new Mappings(oses, architectures, extension);
        mappings.releases.add("R");
        mappings.releases.add("M1");
        mappings.releases.add("M2");
        mappings.releases.add("M3");
        return mappings;
    }

    @Override
    protected Pattern getVersionPattern() {
        return Pattern.compile("\\d{4}-\\d{2}");
    }

    @Override
    public String getToolName() {
        return "Eclipse";
    }

    @Override
    public String getVersionUrl() {
        return "https://www.eclipse.org/downloads/packages/release";
    }

    @Override
    public List<String> getDownloadUrls() {
        ArrayList<String> downloadUrls = new ArrayList<>();
        downloadUrls.add("https://ftp.snt.utwente.nl/pub/software/eclipse/technology/epp/downloads/release/${version}/${release}/eclipse-${edition}-${version}-${release}-${os}-${arch}.${ext}");
        downloadUrls.add("https://archive.eclipse.org/technology/epp/downloads/release/${version}/${release}/eclipse-${edition}-${version}-${release}-${os}-${arch}.${ext}");
        downloadUrls.add("https://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release/${version}/${release}/eclipse-${edition}-${version}-${release}-${os}-${arch}.${ext}");
        return downloadUrls;
    }


}
