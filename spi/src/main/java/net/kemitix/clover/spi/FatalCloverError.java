package net.kemitix.clover.spi;

public class FatalCloverError extends RuntimeException {
    public FatalCloverError(final String message) {
        super(message);
    }

    public FatalCloverError(final String message, final Throwable cause) {
        super(message, cause);
    }
}
