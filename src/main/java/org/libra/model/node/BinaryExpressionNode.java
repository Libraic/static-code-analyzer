package org.libra.model.node;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.libra.model.token.Token;

import static org.libra.model.Color.BLUE;
import static org.libra.model.Color.PURPLE;
import static org.libra.utils.Constants.JSON_OBJECT_END;
import static org.libra.utils.Constants.JSON_OBJECT_START;
import static org.libra.utils.Constants.LEFT_OPERAND_LITERAL;
import static org.libra.utils.Constants.RIGHT_OPERAND_LITERAL;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BinaryExpressionNode extends Node {

    private Node leftHandOperand;
    private Node rightHandOperand;

    public BinaryExpressionNode(Token token) {
        super(token);
        leftHandOperand = null;
        rightHandOperand = null;
    }

    @Override
    public void addNode(Node node) {
        if (leftHandOperand == null) {
            leftHandOperand = node;
        } else {
            rightHandOperand = node;
        }
    }

    @Override
    public void stringify(int spacing) {
        String spacingLiteral = getSpacing(spacing);
        printTokenWithSpacing(PURPLE, spacingLiteral);

        printStringLiteralWithColor(BLUE, spacingLiteral + LEFT_OPERAND_LITERAL + JSON_OBJECT_START);
        leftHandOperand.stringify(spacing + 1);
        printStringLiteralWithColor(BLUE, spacingLiteral + JSON_OBJECT_END);

        printStringLiteralWithColor(BLUE, spacingLiteral + RIGHT_OPERAND_LITERAL + JSON_OBJECT_START);
        rightHandOperand.stringify(spacing + 1);
        printStringLiteralWithColor(BLUE, spacingLiteral + JSON_OBJECT_END);
    }
}
