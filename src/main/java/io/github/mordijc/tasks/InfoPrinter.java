package io.github.mordijc.tasks;

import io.github.mordijc.Application;

public class InfoPrinter implements Application.ApplicationExecutionBlock {
    @Override
    public void run(Application app) {
        System.out.println(
                "     _      _          _            ____   _       ___ \n" +
                "    / \\    (_)  _ __  | |  _   _   / ___| | |     |_ _|\n" +
                "   / _ \\   | | | '__| | | | | | | | |     | |      | | \n" +
                "  / ___ \\  | | | |    | | | |_| | | |___  | |___   | | \n" +
                " /_/   \\_\\ |_| |_|    |_|  \\__, |  \\____| |_____| |___|\n" +
                "                           |___/                       ");
    }
}
