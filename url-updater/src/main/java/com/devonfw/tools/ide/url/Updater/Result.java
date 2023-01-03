package com.devonfw.tools.ide.url.Updater;

public class Result<A>  {
    private final boolean success;
    private final A value;

    public Result(boolean success, A value) {
        this.success = success;
        this.value = value;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isFailure() {
        return !this.success;
    }

    public A getValue() {
        return this.value;
    }
}