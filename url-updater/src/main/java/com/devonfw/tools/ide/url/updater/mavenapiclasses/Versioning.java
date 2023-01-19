package com.devonfw.tools.ide.url.updater.mavenapiclasses;

import java.util.List;

public record Versioning(String latest, String release, List<String> versions, String lastUpdated) {
}
