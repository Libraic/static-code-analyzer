package org.libra.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum class comprised of token regex patterns and token types.
 */
@AllArgsConstructor
@Getter
public enum TokenPattern {
    NUMBER_PATTERN("^[+-]?([0-9]+[.])?[0-9]+$"),
    STRING_PATTERN("^\\\".*\\\"$"),
    ARITHMETIC_OPERATOR_PATTERN("^\\+{1,2}|\\-{1,2}|\\/|\\*$"),
    ASSIGNMENT_OPERATOR_PATTERN("^={1}$"),
    DATA_TYPE_PATTERN(""),
    VARIABLE_NAME_PATTERN("^.*$");

    private final String regex;
}
