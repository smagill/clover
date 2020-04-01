package net.kemitix.cossmass.clover.images;

public class FatalCloverError extends RuntimeException {
    public FatalCloverError(final String message) {
        super(message);
    }

    public FatalCloverError(final String message, final Throwable cause) {
        super(message, cause);
    }
}
