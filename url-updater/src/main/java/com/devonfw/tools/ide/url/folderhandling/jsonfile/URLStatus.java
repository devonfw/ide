package com.devonfw.tools.ide.url.folderhandling.jsonfile;

public class URLStatus {
    private Success success;
    private Error error;

    public URLStatus(){
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}


