package org.libra.model.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.libra.model.ParsingContext;

/**
 * The class depicting the token which represents a specific entity in the source code.
 * Each Token has the possibility to produce an affiliated Node, depending on the concrete type of token.
 * The way of producing the affiliated Node for each token is different, hence the producing method will be abstract.
 */
@Getter
@EqualsAndHashCode
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
