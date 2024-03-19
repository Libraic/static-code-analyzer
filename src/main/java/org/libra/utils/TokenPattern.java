package org.libra.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum class comprised of token regex patterns and token types.
 * The patters are used upon token generation.
 */
@AllArgsConstructor
@Getter
public enum TokenPattern {
    NUMBER_PATTERN("^[+-]?([0-9]+[.])?[0-9]+$"),
    STRING_PATTERN("^\\\".*\\\"$"),
    ARITHMETIC_OPERATOR_PATTERN("^\\+{1,2}|\\-{1,2}|\\/|\\*$"),
    ASSIGNMENT_OPERATOR_PATTERN("^={1}$"),
    DATA_TYPE_PATTERN(""),
    ENTITY_NAME_PATTERN("^[a-zA-Z_$][\\w$]*$"),
    INSTRUCTION_SUBPROGRAM_PATTERN("^(;|\\{)$"),
    ACCESS_MODIFIER_PATTERN("^public|private|protected$"),
    STATIC_ACCESS_PATTERN("^static$"),
    FINAL_PATTERN("^final$"),
    PARENTHESES_PATTERN("^(\\(|\\))$"),
    SEPARATOR_PATTERN("^,$");

    private final String regex;
}
