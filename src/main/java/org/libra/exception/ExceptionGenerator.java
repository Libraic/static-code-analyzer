package org.libra.exception;

/**
 * A class containing the utilities necessaries for generating exceptions.
 */
public final class ExceptionGenerator extends RuntimeException {

    public static StaticCodeAnalyzerException of(ExceptionType exceptionType, String message) {
        throw new StaticCodeAnalyzerException(exceptionType, message);
    }
}
