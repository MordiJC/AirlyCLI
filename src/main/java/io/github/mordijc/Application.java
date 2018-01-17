package io.github.mordijc;

import io.github.mordijc.cli.Command;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private final String[] args;
    private boolean shouldStop = false;
    private Command applicationCommand;

    private List<ApplicationExecutionBlock> applicationExecutionBlockList = new ArrayList<>();

    public Application(String[] args) {
        this.args = args;
        applicationCommand = CommandLine.populateCommand(new Command(), args);
    }

    public Command getApplicationCommand() {
        return applicationCommand;
    }

    public Application addChainBlock(ApplicationExecutionBlock applicationExecutionBlock) {
        assert (applicationCommand != null);

        applicationExecutionBlockList.add(applicationExecutionBlock);

        return this;
    }

    public void exit() {
        this.shouldStop = true;
    }

    public void run() {
        try {
            for (ApplicationExecutionBlock applicationExecutionBlock : applicationExecutionBlockList) {
                if (!shouldStop) {
                    applicationExecutionBlock.run(this);
                }
            }
        } catch (ApplicationExecutionBlockException e) {
            System.err.println("Application execution exception: " + e.getMessage());
        }
    }

    public interface ApplicationExecutionBlock {

        void run(Application app);
    }

    public class ApplicationExecutionBlockException extends RuntimeException {

    }
}
