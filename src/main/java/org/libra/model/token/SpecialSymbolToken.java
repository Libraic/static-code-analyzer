package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.UnaryNode;

public class SpecialSymbolToken extends Token {

    public SpecialSymbolToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        UnaryNode constantNode = new UnaryNode(this);
        parsingContext.addNode(constantNode);
    }
}
