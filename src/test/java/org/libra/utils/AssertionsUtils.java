package org.libra.utils;

import lombok.SneakyThrows;
import org.libra.model.node.Node;
import org.libra.model.node.ProgramNode;
import org.libra.model.node.SubprogramNode;
import org.libra.model.node.UnaryNode;
import org.libra.model.token.Token;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssertionsUtils {

    public static void assertListOfTokensAreEqual(
        List<Token> expected,
        List<Token> actual
    ) {
        if (expected.size() != actual.size()) {
            assertEquals(expected.size(), actual.size());
        }

        for (int i = 0; i < expected.size(); ++i) {
            Token expectedToken = expected.get(i);
            Token actualToken = actual.get(i);
            if (!expectedToken.equals(actualToken)) {
                assertEquals(expectedToken, actualToken);
            }
        }

        assertTrue(true);
    }

    @SneakyThrows
    public static void assertAbstractSyntaxTreesAreEqual(
        Node expected,
        Node actual
    ) {
        assertActualObjectIsOfExpectedInstance(actual, expected.getClass().getName());
        ProgramNode expectedProgramNode = (ProgramNode) expected;
        ProgramNode actualProgramNode = (ProgramNode) actual;
        if (!expected.getToken().getTokenType().equals(actual.getToken().getTokenType())) {
            assertEquals(expected.getToken().getTokenType(), actual.getToken().getTokenType());
        }

        if (expectedProgramNode.getSubprograms().size() != actualProgramNode.getSubprograms().size()) {
            assertEquals(expectedProgramNode.getSubprograms().size(), actualProgramNode.getSubprograms().size());
        }

        for (int i = 0; i < expectedProgramNode.getSubprograms().size(); ++i) {
            assertActualObjectIsOfExpectedInstance(
                actualProgramNode.getSubprograms().get(i),
                expectedProgramNode.getSubprograms().get(i).getClass().getName()
            );
            SubprogramNode expectedSubprogramNode = (SubprogramNode) expectedProgramNode.getSubprograms().get(i);
            SubprogramNode actualSubprogramNode = (SubprogramNode) actualProgramNode.getSubprograms().get(i);
            assertSubprogramMetadataEqual(expectedSubprogramNode, actualSubprogramNode);
            assertSignatureVariablesAreEqual(
                expectedSubprogramNode.getSignatureVariables(),
                actualSubprogramNode.getSignatureVariables()
            );
            assertInstructionsAreEqual(
                expectedSubprogramNode.getInstructions(),
                actualSubprogramNode.getInstructions()
            );
        }

        assertTrue(true);
    }

    private static void assertSubprogramMetadataEqual(SubprogramNode expected, SubprogramNode actual) {
        if (!expected.getAccessModifier().equals(actual.getAccessModifier())
            || !expected.getMembership().equals(actual.getMembership())
            || !expected.getReturnType().equals(actual.getReturnType())
            || !expected.getMethodName().equals(actual.getMethodName())
        ) {
            assertEquals(expected, actual);
        }
    }

    @SneakyThrows
    private static void assertSignatureVariablesAreEqual(
        List<Node> expectedSignatureVariables,
        List<Node> actualSignatureVariables
    ) {
        if (expectedSignatureVariables.size() != actualSignatureVariables.size()) {
            assertEquals(expectedSignatureVariables.size(), actualSignatureVariables.size());
        }

        for (int i = 0; i < expectedSignatureVariables.size(); ++i) {
            assertActualObjectIsOfExpectedInstance(
                actualSignatureVariables.get(i),
                expectedSignatureVariables.get(i).getClass().getName()
            );
            UnaryNode expectedSignatureVariable = (UnaryNode) expectedSignatureVariables.get(i);
            UnaryNode actualSignatureVariable = (UnaryNode) actualSignatureVariables.get(i);
            assertVariablesDataTypesAreEqual(expectedSignatureVariable, actualSignatureVariable);
            if (!expectedSignatureVariable.getToken().equals(actualSignatureVariable.getToken())) {
                assertEquals(expectedSignatureVariable.getToken(), actualSignatureVariable.getToken());
            }
        }
    }

    private static void assertActualObjectIsOfExpectedInstance(
        Object actual,
        String className
    ) throws ClassNotFoundException {
        Class<?> expectedClass = Class.forName(className);
        if (!expectedClass.isInstance(actual)) {
            assertEquals(actual.getClass(), expectedClass);
        }
    }

    @SneakyThrows
    private static void assertVariablesDataTypesAreEqual(
        UnaryNode expected,
        UnaryNode actual
    ) {
        if ((expected.getChild() == null && actual.getChild() != null)
            || (expected.getChild() != null && actual.getChild() == null)
        ) {
            assertEquals(expected, actual);
        }

        assertActualObjectIsOfExpectedInstance(actual.getChild(), expected.getClass().getName());
        UnaryNode expectedChildNode = (UnaryNode) expected.getChild();
        UnaryNode actualChildNode = (UnaryNode) actual.getChild();
        if (!expectedChildNode.getToken().equals(actualChildNode.getToken()) || actualChildNode.getChild() != null) {
            assertEquals(expected.getChild().getToken(), actual.getChild().getToken());
        }
    }

    @SneakyThrows
    private static void assertInstructionsAreEqual(List<Node> expected, List<Node> actual) {
        if (expected.size() != actual.size()) {
            assertEquals(expected.size(), actual.size());
        }

        for (int i = 0; i < expected.size(); ++i) {
            assertActualObjectIsOfExpectedInstance(actual.get(i), expected.get(i).getClass().getName());
        }
    }
}
