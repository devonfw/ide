package com.devonfw.tools.ide.url.Updater.githubapiclasses;

public record GithubJsonItem(
        String node_id,
        Object object,
        String ref,
        String url
){}
