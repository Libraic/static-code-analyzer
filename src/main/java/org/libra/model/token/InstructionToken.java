package org.libra.model.token;

import org.libra.model.ParsingContext;
import org.libra.model.node.Node;
import org.libra.model.node.ProgramNode;
import org.libra.model.node.UnaryNode;
import org.libra.service.ArithmeticService;

import java.util.Iterator;

import static org.libra.model.token.TokenType.METHOD_DECLARATION;
import static org.libra.model.token.TokenType.PROGRAM;
import static org.libra.utils.Constants.*;

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
            if (isRedundantToken(currentNode.getToken().getValue())) {
                nodeIterator.remove();
                continue;
            }

            if (instructionNode.getToken().getTokenType().equals(PROGRAM)
                && !currentNode.getToken().getTokenType().equals(METHOD_DECLARATION)
            ) {
                assert instructionNode instanceof ProgramNode;
                Node subprogramNode = ((ProgramNode) instructionNode).getLastSubprogram();
                subprogramNode.addNode(currentNode);

            } else {
                instructionNode.addNode(currentNode);
            }

            instructionNode = currentNode;
            if (!currentNode.getToken().getTokenType().equals(PROGRAM)) {
                nodeIterator.remove();
            }
        }
    }

    private boolean isRedundantToken(Object value) {
        return value.equals(OPEN_PARENTHESIS_LITERAL) ||
            value.equals(CLOSED_PARENTHESIS_LITERAL) ||
            value.equals(OPEN_CURLY_BRACE_LITERAL) ||
            value.equals(COMMA_LITERAL);
    }
}
