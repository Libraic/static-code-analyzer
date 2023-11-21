package org.libra.model.token;

import lombok.EqualsAndHashCode;
import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.ProgramNode;

@EqualsAndHashCode(callSuper = true)
public class ProgramToken extends Token {

    public ProgramToken(TokenType tokenType, Object value) {
        super(tokenType, value);
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        Node programNode = new ProgramNode(this);
        parsingContext.addNode(programNode);
    }
}
