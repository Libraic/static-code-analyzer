package org.libra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The class depicting the token which represents a specific entity in the source code.
 */
@AllArgsConstructor
@Getter
public class Token {

    private TokenType tokenType;
    private Object value;

    @Override
    public String toString() {
        return "Type: " +
            tokenType +
            "\n" +
            "Value: " +
            value;
    }
}
