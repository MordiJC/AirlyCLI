package io.github.mordijc.cli;

import picocli.CommandLine;

@CommandLine.Command(headerHeading = "Usage:%n%n",
        header = "Airly console client.",
        descriptionHeading = "%nDescription:%n%n",
        description =
                "This program connects to Airly REST API and provides " +
                "information about nearest or specific air quality sensor.")
public class Command {

    @CommandLine.Option(
            names = {"--latitude"},
            description = "Nearest sensor searching latitude. (Longitude must be provided as well)")
    public Double latitude = 0.0;

    @CommandLine.Option(
            names = {"--longitude"},
            description = "Nearest sensor searcing longitude. (Latitude must be provided as well)")
    public Double longitude = 0.0;

    @CommandLine.Option(
            names = {"-s", "--sensor"},
            description = "Get sensor information by its ID")
    public Integer sensorId = -1;

   @CommandLine.Option(
           names = {"-k", "--apikey"},
           description = "Airly REST service API token. Can also be given as API_KEY environment variable. " +
                   "If program will not find this value either in environment variable or in this option, " +
                   "then it will ask for API token.")
    public String apikey = "";
}
