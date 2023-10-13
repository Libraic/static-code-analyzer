package org.libra.model.node;

import org.libra.model.token.Token;

import static org.libra.model.Color.YELLOW;
import static org.libra.model.token.TokenType.INSTRUCTION;
import static org.libra.model.token.TokenType.VARIABLE_NAME;
import static org.libra.utils.Constants.DATA_TYPE_KEYWORD;
import static org.libra.utils.Constants.INSTRUCTION_BODY_KEYWORD;
import static org.libra.utils.Constants.JSON_OBJECT_END;
import static org.libra.utils.Constants.JSON_OBJECT_START;
import static org.libra.utils.Constants.NEW_LINE_DELIMITER;

public class UnaryNode extends Node {

    private Node child;

    public UnaryNode(Token token) {
        super(token);
        child = null;
    }

    @Override
    public void addNode(Node node) {
        child = node;
    }

    @Override
    public void stringify(int spacing) {
        String spacingLiteral = getSpacing(spacing);
        for (String tokenString : token.toString().split(NEW_LINE_DELIMITER)) {
            printStringLiteralWithColor(YELLOW, spacingLiteral + tokenString);
        }

        if (child != null) {
            String bodyKeyword = getBodyKeyword();
            printStringLiteralWithColor(YELLOW, spacingLiteral + bodyKeyword + JSON_OBJECT_START);
            child.stringify(spacing + 1);
            printStringLiteralWithColor(YELLOW, spacingLiteral + JSON_OBJECT_END);
        }
    }

    private String getBodyKeyword() {
        if (token.getTokenType().equals(INSTRUCTION)) {
            return INSTRUCTION_BODY_KEYWORD;
        } else if(token.getTokenType().equals(VARIABLE_NAME)) {
            return DATA_TYPE_KEYWORD;
        }

        return null;
    }
}
