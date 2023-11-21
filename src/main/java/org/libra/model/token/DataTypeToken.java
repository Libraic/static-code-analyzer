package org.libra.model.token;

import lombok.EqualsAndHashCode;
import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

@EqualsAndHashCode(callSuper = true)
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
