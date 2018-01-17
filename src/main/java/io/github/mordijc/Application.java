package io.github.mordijc;

import io.github.mordijc.cli.Command;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;

public final class Application {
    private boolean shouldStop = false;
    private Command applicationCommand;

    private List<ApplicationExecutionBlock> applicationExecutionBlockList = new ArrayList<>();

    public Application(String[] args) {
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
