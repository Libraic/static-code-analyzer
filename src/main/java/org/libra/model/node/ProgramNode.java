package org.libra.model.node;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.libra.model.token.Token;

import java.util.ArrayList;
import java.util.List;

import static org.libra.model.Color.GREEN;
import static org.libra.utils.Constants.JSON_OBJECT_END;
import static org.libra.utils.Constants.JSON_OBJECT_START;
import static org.libra.utils.Constants.PROGRAM_BODY_KEYWORD;
import static org.libra.utils.Constants.SUBPROGRAM_LITERAL;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProgramNode extends Node {

    private final List<Node> subprograms;

    public ProgramNode(Token token) {
        super(token);
        subprograms = new ArrayList<>();
    }

    @Override
    public void addNode(Node node) {
        subprograms.add(node);
    }

    @Override
    public void stringify(int spacing) {
        printStringLiteralWithColor(GREEN, token.toString());
        printStringLiteralWithColor(GREEN, PROGRAM_BODY_KEYWORD + JSON_OBJECT_START);
        for (int i = 0; i < subprograms.size(); ++i) {
            Node subprogram = subprograms.get(i);
            printStringLiteralWithColor(GREEN, SUBPROGRAM_LITERAL + (i + 1) + ": {");
            subprogram.stringify(spacing + 2);
            printStringLiteralWithColor(GREEN, "\t}");
        }

        printStringLiteralWithColor(GREEN, JSON_OBJECT_END);
    }

    public Node getLastSubprogram() {
        return subprograms.get(subprograms.size() - 1);
    }

}
