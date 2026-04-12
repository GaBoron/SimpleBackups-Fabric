package de.melanx.simplebackups.sbk;

public final class SbkException extends RuntimeException {

    public SbkException(String message) {
        super(message);
    }

    public SbkException(String message, Throwable cause) {
        super(message, cause);
    }
}
