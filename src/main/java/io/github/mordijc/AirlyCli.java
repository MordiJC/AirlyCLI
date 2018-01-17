package io.github.mordijc;

import io.github.mordijc.tasks.AirlySensorInfo;
import io.github.mordijc.tasks.CommandChecker;
import io.github.mordijc.tasks.InfoPrinter;

public class AirlyCli {

    public static void main(String[] args) {
        Application application = new Application(args);

        application.addChainBlock(new InfoPrinter())
                .addChainBlock(new CommandChecker())
                .addChainBlock(new AirlySensorInfo());

        application.run();
    }
}
