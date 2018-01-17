package io.github.mordijc.execution;

import io.github.mordijc.Application;
import io.github.mordijc.cli.Command;
import picocli.CommandLine;

public class CommandChecker implements Application.ApplicationExecutionBlock {

    @Override
    public void run(Application app) throws Application.ApplicationExecutionBlockException {
        Command command = app.getApplicationCommand();

        printHelpAndExitIfRequired(app, command);

        checkSensorOptionsAndThrowIfInvalid(command);
    }

    private void checkSensorOptionsAndThrowIfInvalid(Command command) {
        if ((command.latitude != null && command.longitude == null)
                || (command.latitude == null && command.longitude != null)) {
            throw new Application.ApplicationExecutionBlockException(
                    "Nearest sensor service requires both of latitude and longitude."
            );
        }
        else //noinspection ConstantConditions | IntelliJ flag
            if (command.sensorId != null
                    && command.latitude != null
                    && command.longitude != null) {
                throw new Application.ApplicationExecutionBlockException(
                        "Too much options. Provide either latitude and longitude or sensor id."
                );
            } else {
                throw new Application.ApplicationExecutionBlockException(
                        "At least one option group must be provided must be provided. "
                                + "Provide sensor id or geolocation coordinates.");
            }
    }

    private void printHelpAndExitIfRequired(Application app, Command command) {
        if (command.requestHelp) {
            CommandLine.usage(command, System.out);
            app.exit();
        }
    }
}
