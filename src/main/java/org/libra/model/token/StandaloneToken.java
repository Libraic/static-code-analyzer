package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

public class StandaloneToken extends Token{

    public StandaloneToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        Node constantNode = new UnaryNode(this);
        parsingContext.addNode(constantNode);
    }

}
