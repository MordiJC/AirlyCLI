package io.github.mordijc.execution;

import io.github.mordijc.Application;
import io.github.mordijc.cli.Command;
import picocli.CommandLine;

public class CommandChecker implements Application.ApplicationExecutionBlock{

    @Override
    public void run(Application app) {
        Command command = app.getApplicationCommand();

        if(command.requestHelp) {
            CommandLine.usage(command, System.out);
            app.exit();
        }
    }
}
