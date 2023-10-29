package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.UnaryNode;
import org.libra.service.ArithmeticService;

import java.util.Iterator;

import static org.libra.model.token.TokenType.CLOSED_PARENTHESIS;
import static org.libra.model.token.TokenType.OPEN_PARENTHESIS;
import static org.libra.model.token.TokenType.PROGRAM;

public class InstructionToken extends Token {

    private final ArithmeticService arithmeticService;

    public InstructionToken(TokenType tokenType, Object value) {
        super(tokenType, value);
        arithmeticService = new ArithmeticService();
    }

    @Override
    public void produceNode(ParsingContext parsingContext) {
        int start = parsingContext.findAssignmentNodeIndex();
        arithmeticService.handleMultiplicationAndAddition(parsingContext, start);

        Node instructionNode = new UnaryNode(this);
        Iterator<Node> nodeIterator = parsingContext.getNodesIterator();
        while (nodeIterator.hasNext()) {
            Node currentNode = nodeIterator.next();
            if (isParenthesis(currentNode.getToken().getTokenType())) {
                continue;
            }
            instructionNode.addNode(currentNode);
            instructionNode = currentNode;
            if (!currentNode.getToken().getTokenType().equals(PROGRAM)) {
                nodeIterator.remove();
            }
        }
    }

    private boolean isParenthesis(TokenType tokenType) {
        return tokenType.equals(OPEN_PARENTHESIS) ||
            tokenType.equals(CLOSED_PARENTHESIS);
    }
}
