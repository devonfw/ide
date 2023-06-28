package com.devonfw.tools.ide.dev.command;

public abstract class EnvCommand extends AbstractCommand {

    @Override
    public void run() {
        envCommand();
    }

    protected abstract void envCommand();

}
