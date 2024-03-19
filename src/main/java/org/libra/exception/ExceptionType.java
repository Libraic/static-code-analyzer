package org.libra.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum structure containing the messages of the exception that might arise during the execution of the application.
 */
@AllArgsConstructor
@Getter
public enum ExceptionType {

    FILE_PROCESSING_EXCEPTION,
    UNEXPECTED_TOKEN_EXCEPTION
}
