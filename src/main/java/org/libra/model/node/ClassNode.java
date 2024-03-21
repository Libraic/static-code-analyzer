package org.libra.model.node;

import static org.libra.model.Color.WHITE;
import static org.libra.utils.Constants.JSON_OBJECT_END;
import static org.libra.utils.Constants.JSON_OBJECT_START;
import static org.libra.utils.Constants.SUBPROGRAM_LITERAL;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.libra.model.AccessModifier;
import org.libra.model.State;
import org.libra.model.token.Token;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClassNode extends Node {

    private final List<Node> subprograms;
    private final String className;
    private final AccessModifier accessModifier;
    private final State state;

    public ClassNode(
        Token token,
        String className,
        AccessModifier accessModifier,
        State state
    ) {
        super(token);
        this.className = className;
        this.accessModifier = accessModifier;
        this.state = state;
        subprograms = new ArrayList<>();
    }

    public Node getLastSubprogram() {
        return subprograms.get(subprograms.size() - 1);
    }

    @Override
    public void addNode(Node node) {
        subprograms.add(node);
    }

    @Override
    public void stringify(int spacing) {
        String spacingLiteral = getSpacing(spacing);
        printStringLiteralWithColor(WHITE, spacingLiteral + "Type: CLASS");
        printStringLiteralWithColor(WHITE, spacingLiteral + "Class name: " + className);
        printStringLiteralWithColor(WHITE, spacingLiteral + "Access: " + accessModifier);
        printStringLiteralWithColor(WHITE, spacingLiteral + "State: " + state);
        printStringLiteralWithColor(WHITE, spacingLiteral + "Class body: {");
        for (int i = 0; i < subprograms.size(); ++i) {
            Node subprogram = subprograms.get(i);
            printStringLiteralWithColor(WHITE, spacingLiteral + SUBPROGRAM_LITERAL + (i + 1) + JSON_OBJECT_START);
            subprogram.stringify(spacing + 2);
            printStringLiteralWithColor(WHITE, spacingLiteral + "\t}");
        }
        printStringLiteralWithColor(WHITE, spacingLiteral + JSON_OBJECT_END);
    }
}
