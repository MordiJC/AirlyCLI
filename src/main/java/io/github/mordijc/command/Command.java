package io.github.mordijc.command;

import picocli.CommandLine;

/**
 * Command object used to map program command using PicoCLI.
 */
@CommandLine.Command(headerHeading = "Usage:%n%n",
        header = "Airly console client.",
        name = "java -jar AirlyCLI.jar",
        descriptionHeading = "%nDescription:%n%n",
        synopsisHeading = "%n",
        parameterListHeading = "%nParameters:%n",
        optionListHeading = "%nOptions:%n",
        description =
                "This program connects to Airly REST API and provides " +
                        "information about nearest or specific air quality sensor.\n" +
                        "Your system have to support UTF-8 encoding. On windows enable code page 65001.")
public class Command {

    @CommandLine.Option(
            names = {"--latitude"},
            description = "Nearest sensor searching latitude. (Longitude must be provided as well)")
    public Double latitude = null;

    @CommandLine.Option(
            names = {"--longitude"},
            description = "Nearest sensor searching longitude. (Latitude must be provided as well)")
    public Double longitude = null;

    @CommandLine.Option(
            names = {"-s", "--sensor"},
            description = "Get sensor information by its ID")
    public Integer sensorId = null;

    @CommandLine.Option(
            names = {"-k", "--apiKey"},
            description = "Airly REST service API token. Can also be given as API_KEY environment variable. " +
                    "If program will not find this value either in environment variable or in this option, " +
                    "then it will ask for API token.")
    public String apiKey = null;

    @CommandLine.Option(
            names = {"-h", "--help"},
            description = "Show help and exit.")
    public Boolean requestHelp = false;
}
