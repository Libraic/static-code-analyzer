package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;

public class EntityNameToken extends Token {

    public EntityNameToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        Node variableNode = new UnaryNode(this);
        if (!parsingContext.isAssignmentNodePresent()) {
            Node previousNode = parsingContext.retrieveAndRemoveLastNode();
            variableNode.addNode(previousNode);
        }

        parsingContext.addNode(variableNode);
    }
}
