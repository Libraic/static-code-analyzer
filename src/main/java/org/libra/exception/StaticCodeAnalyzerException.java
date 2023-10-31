package org.libra.exception;

/**
 * Static Code Analyzer Exception class.
 */
public final class StaticCodeAnalyzerException extends RuntimeException {

    private final ExceptionType exceptionType;

    public StaticCodeAnalyzerException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }
}
