package io.github.mordijc.tasks;

import io.github.mordijc.Application;
import io.github.mordijc.command.Command;
import picocli.CommandLine;

public class CommandCheckerTask implements Application.ApplicationExecutionBlock {

    @Override
    public void run(Application app) throws Application.ApplicationExecutionBlockException {
        Command command = app.getApplicationCommand();

        if (printHelpAndExitIfRequired(app, command)) {
            return;
        }

        checkSensorOptionsAndThrowIfInvalid(command);
    }

    private void checkSensorOptionsAndThrowIfInvalid(Command command) {
        if ((command.latitude != null && command.longitude == null)
                || (command.latitude == null && command.longitude != null)) {
            throw new Application.ApplicationExecutionBlockException(
                    "Nearest sensor service requires both of latitude and longitude."
            );
        } else if (command.sensorId != null
                && command.latitude != null
                && command.longitude != null) {
            throw new Application.ApplicationExecutionBlockException(
                    "Too much options. Provide either latitude and longitude or sensor id."
            );
        } else if (command.sensorId == null
                && command.latitude == null
                && command.longitude == null) {
            throw new Application.ApplicationExecutionBlockException(
                    "At least one option group must be provided must be provided. "
                            + "Provide sensor id or geolocation coordinates.");
        }
    }

    private boolean printHelpAndExitIfRequired(Application app, Command command) {
        if (command.requestHelp) {
            CommandLine.usage(command, System.out);
            app.exit();
            return true;
        }
        return false;
    }
}
