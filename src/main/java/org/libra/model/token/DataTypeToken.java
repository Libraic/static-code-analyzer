package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

public class DataTypeToken extends Token {

    public DataTypeToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        Node programNode = new UnaryNode(this);
        parsingContext.addNode(programNode);
    }
}
