package org.libra.exception;

import lombok.Getter;

/**
 * Static Code Analyzer Exception class.
 */
@Getter
public final class StaticCodeAnalyzerException extends RuntimeException {

    private final ExceptionType exceptionType;
    private final String message;

    public StaticCodeAnalyzerException(ExceptionType exceptionType, String message) {
        this.exceptionType = exceptionType;
        this.message = message;
    }

    public void printDetails() {
        System.out.println(exceptionType.name() + ": " + message);
    }
}
