package org.libra.model.token;

import org.libra.model.ParsingContext;

public class AccessModifierToken extends Token {

    public AccessModifierToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {

    }


}
