package org.libra.model.token;

import lombok.Getter;
import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import java.util.Stack;

/**
 * The class depicting the token which represents a specific entity in the source code.
 */
@Getter
public abstract class Token {

    protected final TokenType tokenType;
    protected final Object value;

    public Token(TokenType tokenType, Object value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Type: " +
            tokenType +
            "\n" +
            "Value: " +
            value;
    }

    public abstract void produceNode(ParsingContext parsingContext);
}
