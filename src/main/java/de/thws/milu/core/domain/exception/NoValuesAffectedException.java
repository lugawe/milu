package de.thws.milu.core.domain.exception;

public class NoValuesAffectedException extends RuntimeException {

    public NoValuesAffectedException() {
        super();
    }

    public NoValuesAffectedException(String message) {
        super(message);
    }

    public NoValuesAffectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoValuesAffectedException(Throwable t) {
        super(t);
    }
}
