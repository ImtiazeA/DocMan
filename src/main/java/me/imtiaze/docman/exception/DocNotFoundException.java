package me.imtiaze.docman.exception;

public class DocNotFoundException extends RuntimeException {
    public DocNotFoundException() {
        super();
    }

    public DocNotFoundException(String message) {
        super(message);
    }

    public DocNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
