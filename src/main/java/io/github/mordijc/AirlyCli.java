package io.github.mordijc;

import io.github.mordijc.tasks.AirlySensorInfoTask;
import io.github.mordijc.tasks.CommandCheckerTask;
import io.github.mordijc.tasks.InfoPrinterTask;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class AirlyCli {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Your system does not support UTF-8 encoding.");
            return;
        }

        Application application = new Application(args);

        application.addChainBlock(new CommandCheckerTask())
                .addChainBlock(new InfoPrinterTask())
                .addChainBlock(new AirlySensorInfoTask());

        application.run();
    }
}
