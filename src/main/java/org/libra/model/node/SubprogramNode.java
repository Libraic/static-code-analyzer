package org.libra.model.node;

import lombok.Getter;
import org.libra.model.AccessModifier;
import org.libra.model.Membership;
import org.libra.model.token.Token;
import org.libra.model.token.TokenType;

import java.util.ArrayList;
import java.util.List;

import static org.libra.model.Color.*;
import static org.libra.utils.Constants.INSTRUCTION_LITERAL;

@Getter
public class SubprogramNode extends Node {

    private final List<Node> instructions;
    private final List<Node> signatureVariables;
    private final String methodName;
    private final AccessModifier accessModifier;
    private final String returnType;
    private final Membership membership;

    public SubprogramNode(
        Token token,
        String methodName,
        AccessModifier accessModifier,
        String returnType,
        Membership membership
    ) {
        super(token);
        this.methodName = methodName;
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.membership = membership;
        instructions = new ArrayList<>();
        signatureVariables = new ArrayList<>();
    }

    @Override
    public void addNode(Node node) {
        if (node.getToken().getTokenType().equals(TokenType.VARIABLE_NAME)) {
            signatureVariables.add(node);
        } else {
            instructions.add(node);
        }
    }

    @Override
    public void stringify(int spacing) {
        String spacingLiteral = getSpacing(spacing);
        printStringLiteralWithColor(CYAN, spacingLiteral + "Type: SUBPROGRAM");
        printStringLiteralWithColor(CYAN, spacingLiteral + "Subprogram Name: " + methodName);
        printStringLiteralWithColor(CYAN, spacingLiteral + "Access: " + accessModifier);
        printStringLiteralWithColor(CYAN, spacingLiteral + "Membership: " + membership);
        printStringLiteralWithColor(CYAN, spacingLiteral + "Signature variables: {");
        for (Node signatureVariableNode : signatureVariables) {
            signatureVariableNode.stringify(spacing + 1);
        }

        printStringLiteralWithColor(CYAN, spacingLiteral + "}");
        printStringLiteralWithColor(CYAN, spacingLiteral + "Subprogram Body: {");
        for (int i = 0; i < instructions.size(); ++i) {
            Node node = instructions.get(i);
            printStringLiteralWithColor(RED, spacingLiteral + INSTRUCTION_LITERAL + (i + 1) + ": {");
            node.stringify(spacing + 2);
            printStringLiteralWithColor(RED, spacingLiteral + "\t}");
        }

        printStringLiteralWithColor(CYAN, spacingLiteral + "}");
    }
}
