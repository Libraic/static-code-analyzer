package org.libra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum structure containing the messages of the exception that might arise during the execution of the application.
 */
@AllArgsConstructor
@Getter
public enum ExceptionType {

    FILE_PROCESSING_EXCEPTION("An error occurred while processing the provided file."),
    UNEXPECTED_TOKEN_EXCEPTION("The processed token is not recognized by the system.");

    private final String message;
}
