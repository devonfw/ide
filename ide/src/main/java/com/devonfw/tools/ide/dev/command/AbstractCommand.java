package com.devonfw.tools.ide.dev.command;

import java.util.concurrent.Callable;

public abstract class AbstractCommand implements Callable<Integer> {

    final Integer SUCCESS = 0;

    @Override
    public Integer call() {
        System.out.println("Running command " + getClass().getSimpleName());
        run();
        return SUCCESS;
    }

    public abstract void run();
}
