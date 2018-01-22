package io.github.mordijc;

import io.github.mordijc.command.Command;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application class. Parses arguments and executes task chain.
 */
public final class Application {
    private boolean shouldStop = false;
    private Command applicationCommand;
    private List<ApplicationExecutionBlock> applicationExecutionBlockList = new ArrayList<>();

    /**
     * Constructs {@code Application}. Arguments are parsed and pre-validated.
     *
     * @param args program arguments.
     */
    public Application(String[] args) {
        try {
            applicationCommand = CommandLine.populateCommand(new Command(), args);
        } catch (CommandLine.MissingParameterException e) {
            printErrorMessageAndExit("Command parameters error: " + e.getMessage());
        } catch (CommandLine.OverwrittenOptionException e) {
            printErrorMessageAndExit("Options cannot be overwritten.\n" + e.getMessage());
        } catch (CommandLine.UnmatchedArgumentException e) {
            printErrorMessageAndExit("Too much arguments: " + e.getMessage());
        }
    }

    /**
     * Returns application parsed command.
     */
    public Command getApplicationCommand() {
        return applicationCommand;
    }

    /**
     * Adds task to execution chain.
     */
    public Application addChainBlock(ApplicationExecutionBlock applicationExecutionBlock) {
        assert (applicationCommand != null);

        applicationExecutionBlockList.add(applicationExecutionBlock);

        return this;
    }

    /**
     * Tells application not to execute next task. WARNING!!! This will not
     * stop execution of current task, you have to do it on your own.
     */
    public void exit() {
        this.shouldStop = true;
    }

    /**
     * Starts execution of application task chain.
     */
    public void run() {
        try {
            for (ApplicationExecutionBlock applicationExecutionBlock : applicationExecutionBlockList) {
                if (!shouldStop) {
                    applicationExecutionBlock.run(this);
                }
            }
        } catch (ApplicationExecutionBlockException e) {
            System.err.println("Application tasks exception: " + e.getMessage());
        }
    }


    private void printErrorMessageAndExit(String message) {
        System.err.println(message);
        this.exit();
    }

    /**
     * This interface is used to represent task in application execution chain.
     * Classes implementing this interface can be added to {@code Application}
     * execution chain.
     */
    public interface ApplicationExecutionBlock {


        /**
         * Runs this execution block when executed by {@code Application}.
         *
         * @param app {@code Application} instance.
         * @throws ApplicationExecutionBlockException
         */
        void run(Application app) throws ApplicationExecutionBlockException;
    }

    public static class ApplicationExecutionBlockException extends RuntimeException {
        /**
         * Constructs a new runtime exception with {@code null} as its
         * detail message.  The cause is not initialized, and may subsequently be
         * initialized by a call to {@link #initCause}.
         */
        public ApplicationExecutionBlockException() {
            super();
        }

        /**
         * Constructs a new runtime exception with the specified detail message.
         * The cause is not initialized, and may subsequently be initialized by a
         * call to {@link #initCause}.
         *
         * @param message the detail message. The detail message is saved for
         *                later retrieval by the {@link #getMessage()} method.
         */
        public ApplicationExecutionBlockException(String message) {
            super(message);
        }

        /**
         * Constructs a new runtime exception with the specified detail message and
         * cause.  <p>Note that the detail message associated with
         * {@code cause} is <i>not</i> automatically incorporated in
         * this runtime exception's detail message.
         *
         * @param message the detail message (which is saved for later retrieval
         *                by the {@link #getMessage()} method).
         * @param cause   the cause (which is saved for later retrieval by the
         *                {@link #getCause()} method).  (A {@code null} value is
         *                permitted, and indicates that the cause is nonexistent or
         *                unknown.)
         * @since 1.4
         */
        public ApplicationExecutionBlockException(String message, Throwable cause) {
            super(message, cause);
        }

        /**
         * Constructs a new runtime exception with the specified cause and a
         * detail message of {@code (cause==null ? null : cause.toString())}
         * (which typically contains the class and detail message of
         * {@code cause}).  This constructor is useful for runtime exceptions
         * that are little more than wrappers for other throwables.
         *
         * @param cause the cause (which is saved for later retrieval by the
         *              {@link #getCause()} method).  (A {@code null} value is
         *              permitted, and indicates that the cause is nonexistent or
         *              unknown.)
         * @since 1.4
         */
        public ApplicationExecutionBlockException(Throwable cause) {
            super(cause);
        }

        /**
         * Constructs a new runtime exception with the specified detail
         * message, cause, suppression enabled or disabled, and writable
         * stack trace enabled or disabled.
         *
         * @param message            the detail message.
         * @param cause              the cause.  (A {@code null} value is permitted,
         *                           and indicates that the cause is nonexistent or unknown.)
         * @param enableSuppression  whether or not suppression is enabled
         *                           or disabled
         * @param writableStackTrace whether or not the stack trace should
         *                           be writable
         * @since 1.7
         */
        protected ApplicationExecutionBlockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
