package org.libra.utils;

import org.libra.model.node.BinaryExpressionNode;
import org.libra.model.node.Node;
import org.libra.model.node.ProgramNode;
import org.libra.model.node.SubprogramNode;
import org.libra.model.node.UnaryNode;
import org.libra.model.token.Token;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.libra.model.AccessModifier.PUBLIC;
import static org.libra.model.Membership.CLASS;
import static org.libra.model.State.OVERRIDABLE;
import static org.libra.utils.TestConstants.ASSIGNEMENT_OPERATOR_TOKEN;
import static org.libra.utils.TestConstants.CLOSED_PARENTHESES_TOKEN;
import static org.libra.utils.TestConstants.COMMA_SEPARATOR_TOKEN;
import static org.libra.utils.TestConstants.DO_SOME_ARITHMETIC_METHOD_NAME;
import static org.libra.utils.TestConstants.FUNCTION_METHOD_NAME;
import static org.libra.utils.TestConstants.INSTRUCTION_TOKEN;
import static org.libra.utils.TestConstants.INTEGER_DATA_TYPE_TOKEN;
import static org.libra.utils.TestConstants.METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC;
import static org.libra.utils.TestConstants.METHOD_DECLARATION_TOKEN_FUNCTION;
import static org.libra.utils.TestConstants.METHOD_INSTRUCTION_TOKEN;
import static org.libra.utils.TestConstants.MINUS_OPERATOR_TOKEN;
import static org.libra.utils.TestConstants.MULTIPLY_OPERATOR_TOKEN;
import static org.libra.utils.TestConstants.NUMERIC_TOKEN_2;
import static org.libra.utils.TestConstants.NUMERIC_TOKEN_3;
import static org.libra.utils.TestConstants.NUMERIC_TOKEN_4;
import static org.libra.utils.TestConstants.NUMERIC_TOKEN_5;
import static org.libra.utils.TestConstants.OPEN_PARENTHESES_TOKEN;
import static org.libra.utils.TestConstants.PLUS_OPERATOR_TOKEN;
import static org.libra.utils.TestConstants.PRIMITIVE_INT_DATA_TYPE_TOKEN;
import static org.libra.utils.TestConstants.PROGRAM_TOKEN;
import static org.libra.utils.TestConstants.PUBLIC_ACCESS_TOKEN;
import static org.libra.utils.TestConstants.STATE_TOKEN;
import static org.libra.utils.TestConstants.STATIC_ACCESS_TOKEN;
import static org.libra.utils.TestConstants.VARIABLE_NAME_TOKEN_A;
import static org.libra.utils.TestConstants.VARIABLE_NAME_TOKEN_B;
import static org.libra.utils.TestConstants.VARIABLE_NAME_TOKEN_FACTOR;
import static org.libra.utils.TestConstants.VARIABLE_NAME_TOKEN_NUMBER;
import static org.libra.utils.TestConstants.VARIABLE_NAME_TOKEN_RESULT;
import static org.libra.utils.TestConstants.VOID_RETURN_TYPE;
import static org.libra.utils.TestConstants.VOID_RETURN_TYPE_TOKEN;

public class TestObjects {
    public static final List<Token> TEST_FILE_1_TOKENS = List.of(
        PROGRAM_TOKEN,
        PUBLIC_ACCESS_TOKEN,
        STATIC_ACCESS_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_FUNCTION,
        OPEN_PARENTHESES_TOKEN,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN,
        PRIMITIVE_INT_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_A,
        ASSIGNEMENT_OPERATOR_TOKEN,
        NUMERIC_TOKEN_2,
        PLUS_OPERATOR_TOKEN,
        NUMERIC_TOKEN_3,
        INSTRUCTION_TOKEN
    );

    public static final List<Token> TEST_FILE_2_TOKENS = List.of(
        PROGRAM_TOKEN,
        PUBLIC_ACCESS_TOKEN,
        STATIC_ACCESS_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_FUNCTION,
        OPEN_PARENTHESES_TOKEN,
        PRIMITIVE_INT_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_A,
        COMMA_SEPARATOR_TOKEN,
        PRIMITIVE_INT_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_B,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN,
        PRIMITIVE_INT_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_RESULT,
        ASSIGNEMENT_OPERATOR_TOKEN,
        VARIABLE_NAME_TOKEN_A,
        MULTIPLY_OPERATOR_TOKEN,
        NUMERIC_TOKEN_3,
        PLUS_OPERATOR_TOKEN,
        VARIABLE_NAME_TOKEN_B,
        MULTIPLY_OPERATOR_TOKEN,
        NUMERIC_TOKEN_5,
        INSTRUCTION_TOKEN
    );

    public static final List<Token> TEST_FILE_3_TOKENS = List.of(
        PROGRAM_TOKEN,
        PUBLIC_ACCESS_TOKEN,
        STATIC_ACCESS_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC,
        OPEN_PARENTHESES_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_NUMBER,
        COMMA_SEPARATOR_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_FACTOR,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN,
        VARIABLE_NAME_TOKEN_NUMBER,
        ASSIGNEMENT_OPERATOR_TOKEN,
        OPEN_PARENTHESES_TOKEN,
        NUMERIC_TOKEN_2,
        MULTIPLY_OPERATOR_TOKEN,
        OPEN_PARENTHESES_TOKEN,
        OPEN_PARENTHESES_TOKEN,
        NUMERIC_TOKEN_3,
        PLUS_OPERATOR_TOKEN,
        NUMERIC_TOKEN_4,
        CLOSED_PARENTHESES_TOKEN,
        MULTIPLY_OPERATOR_TOKEN,
        NUMERIC_TOKEN_5,
        CLOSED_PARENTHESES_TOKEN,
        CLOSED_PARENTHESES_TOKEN,
        MINUS_OPERATOR_TOKEN,
        NUMERIC_TOKEN_2,
        INSTRUCTION_TOKEN,
        VARIABLE_NAME_TOKEN_FACTOR,
        ASSIGNEMENT_OPERATOR_TOKEN,
        NUMERIC_TOKEN_2,
        PLUS_OPERATOR_TOKEN,
        NUMERIC_TOKEN_3,
        INSTRUCTION_TOKEN
    );
    public static final List<Token> TEST_FILE_4_TOKENS = List.of(
        PROGRAM_TOKEN,
        PUBLIC_ACCESS_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC,
        OPEN_PARENTHESES_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_NUMBER,
        COMMA_SEPARATOR_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_FACTOR,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN
    );

    public static final List<Token> TEST_FILE_5_TOKENS = List.of(
        PROGRAM_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC,
        OPEN_PARENTHESES_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_NUMBER,
        COMMA_SEPARATOR_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_FACTOR,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN
    );

    public static final List<Token> TEST_FILE_6_TOKENS = List.of(
        PROGRAM_TOKEN,
        STATIC_ACCESS_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC,
        OPEN_PARENTHESES_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_NUMBER,
        COMMA_SEPARATOR_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_FACTOR,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN
    );

    public static final List<Token> TEST_FILE_7_TOKENS = List.of(
        PROGRAM_TOKEN,
        PUBLIC_ACCESS_TOKEN,
        STATIC_ACCESS_TOKEN,
        STATE_TOKEN,
        VOID_RETURN_TYPE_TOKEN,
        METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC,
        OPEN_PARENTHESES_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_NUMBER,
        COMMA_SEPARATOR_TOKEN,
        INTEGER_DATA_TYPE_TOKEN,
        VARIABLE_NAME_TOKEN_FACTOR,
        CLOSED_PARENTHESES_TOKEN,
        METHOD_INSTRUCTION_TOKEN
    );

    /*
        public static void function() {
            int a = 2 + 3;
        }
     */
    public static Map<Integer, List<String>> getTestFile1Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(1, List.of("public", "static", "void", "function", "(", ")", "{"));
        map.put(2, List.of("int", "a", "=", "2", "+", "3", ";"));
        return map;
    }

    /*
        public static void function(int a, int b) {
            int result = a * 3 + b * 5;
        }
     */
    public static Map<Integer, List<String>> getTestFile2Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(
            1,
            List.of("public", "static", "void", "function", "(", "int", "a", ",", "int", "b", ")", "{")
        );
        map.put(2, List.of("int", "result", "=", "a", "*", "3", "+", "b", "*", "5", ";"));
        return map;
    }

    /*
        public static void doSomeArithmetic(Integer number, Integer factor) {
            number = (2 * ((3 + 4) * 5)) - 2;
            factor = 2 + 3;
        }
     */
    public static Map<Integer, List<String>> getTestFile3Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(
            1,
            List.of("public", "static", "void", "doSomeArithmetic", "(", "Integer", "number", ",", "Integer",
                "factor", ")", "{"
            )
        );
        map.put(
            2,
            List.of("number", "=", "(", "2", "*", "(", "(", "3", "+", "4", ")", "*", "5", ")", ")", "-", "2",
                ";"
            )

        );
        map.put(
            3,
            List.of("factor", "=", "2", "+", "3", ";")
        );

        return map;
    }

    /*
        Only the method declaration
        public void doSomeArithmetic(Integer number, Integer factor) {
     */
    public static Map<Integer, List<String>> getTestFile4Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(
            1,
            List.of("public", "void", "doSomeArithmetic", "(", "Integer", "number", ",", "Integer",
                "factor", ")", "{"
            )
        );
        return map;
    }

    /*
        Only the method declaration
        void doSomeArithmetic(Integer number, Integer factor) {
     */
    public static Map<Integer, List<String>> getTestFile5Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(
            1,
            List.of("void", "doSomeArithmetic", "(", "Integer", "number", ",", "Integer",
                "factor", ")", "{"
            )
        );
        return map;
    }

    /*
        Only the method declaration
        static void doSomeArithmetic(Integer number, Integer factor) {
     */
    public static Map<Integer, List<String>> getTestFile6Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(
            1,
            List.of("static", "void", "doSomeArithmetic", "(", "Integer", "number", ",", "Integer",
                "factor", ")", "{"
            )
        );
        return map;
    }

    /*
        Only the method declaration
        public static final void doSomeArithmetic(Integer number, Integer factor) {
     */
    public static Map<Integer, List<String>> getTestFile7Keywords() {
        Map<Integer, List<String>> map = new LinkedHashMap<>();
        map.put(
            1,
            List.of("public", "static", "final", "void", "doSomeArithmetic", "(", "Integer", "number", ",", "Integer",
                "factor", ")", "{"
            )
        );
        return map;
    }

    public static Node getTestFile1AbstractSyntaxTree() {
        // Left Hand of the Assignment Binary Node

        // Child of Variable a Node
        UnaryNode dataTypeNode = new UnaryNode(PRIMITIVE_INT_DATA_TYPE_TOKEN);
        // The actual Left Hand of the Assignment Binary Node
        UnaryNode variableANode = new UnaryNode(VARIABLE_NAME_TOKEN_A);
        variableANode.addNode(dataTypeNode);

        // Right Hand of the Assignment Binary Node

        // Left Hand of the Plus Binary Node
        UnaryNode constantNumeric2 = new UnaryNode(NUMERIC_TOKEN_2);
        // Right Hand of the Plus Binary Node
        UnaryNode constantNumeric3 = new UnaryNode(NUMERIC_TOKEN_3);
        // Plus Binary Node, the actual Right Hand of the Assignment Binary Node
        BinaryExpressionNode plusBinaryExpressionNode = new BinaryExpressionNode(PLUS_OPERATOR_TOKEN);
        plusBinaryExpressionNode.addNode(constantNumeric2);
        plusBinaryExpressionNode.addNode(constantNumeric3);

        // Assignment Binary Node
        BinaryExpressionNode assignmentBinaryExpressionNode = new BinaryExpressionNode(ASSIGNEMENT_OPERATOR_TOKEN);
        assignmentBinaryExpressionNode.addNode(variableANode);
        assignmentBinaryExpressionNode.addNode(plusBinaryExpressionNode);

        SubprogramNode subprogramNode = new SubprogramNode(
            METHOD_DECLARATION_TOKEN_FUNCTION,
            FUNCTION_METHOD_NAME,
            PUBLIC,
            OVERRIDABLE,
            VOID_RETURN_TYPE,
            CLASS
        );
        subprogramNode.addNode(assignmentBinaryExpressionNode);

        ProgramNode programNode = new ProgramNode(PROGRAM_TOKEN);
        programNode.addNode(subprogramNode);

        return programNode;
    }

    public static Node getTestFile2AbstractSyntaxTree() {
        // function() method signature variables

        // Child of Variable a Node
        UnaryNode dataTypeNode = new UnaryNode(PRIMITIVE_INT_DATA_TYPE_TOKEN);
        // a signature variable
        UnaryNode variableANode = new UnaryNode(VARIABLE_NAME_TOKEN_A);
        variableANode.addNode(dataTypeNode);

        // b signature variable
        UnaryNode variableBNode = new UnaryNode(VARIABLE_NAME_TOKEN_B);
        variableBNode.addNode(dataTypeNode);

        // Left Hand of the Assignment Binary Node
        UnaryNode variableResultNode = new UnaryNode(VARIABLE_NAME_TOKEN_RESULT);
        variableResultNode.addNode(dataTypeNode);

        // Right Hand of the Assignment Binary Node

        // Left Hand of Addition Node
        BinaryExpressionNode multiplicationNode1 = new BinaryExpressionNode(MULTIPLY_OPERATOR_TOKEN);
        multiplicationNode1.addNode(new UnaryNode(VARIABLE_NAME_TOKEN_A));
        multiplicationNode1.addNode(new UnaryNode(NUMERIC_TOKEN_3));

        // Right Hand of Addition Node
        BinaryExpressionNode multiplicationNode2 = new BinaryExpressionNode(MULTIPLY_OPERATOR_TOKEN);
        multiplicationNode2.addNode(new UnaryNode(VARIABLE_NAME_TOKEN_B));
        multiplicationNode2.addNode(new UnaryNode(NUMERIC_TOKEN_5));

        // Addition Node
        BinaryExpressionNode additionNode = new BinaryExpressionNode(PLUS_OPERATOR_TOKEN);
        additionNode.addNode(multiplicationNode1);
        additionNode.addNode(multiplicationNode2);

        // Assignment Node
        BinaryExpressionNode assignmentNode = new BinaryExpressionNode(ASSIGNEMENT_OPERATOR_TOKEN);
        assignmentNode.addNode(variableResultNode);
        assignmentNode.addNode(additionNode);

        SubprogramNode subprogramNode = new SubprogramNode(
            METHOD_DECLARATION_TOKEN_FUNCTION,
            FUNCTION_METHOD_NAME,
            PUBLIC,
            OVERRIDABLE,
            VOID_RETURN_TYPE,
            CLASS
        );
        subprogramNode.addNode(variableANode);
        subprogramNode.addNode(variableBNode);
        subprogramNode.addNode(assignmentNode);

        ProgramNode programNode = new ProgramNode(PROGRAM_TOKEN);
        programNode.addNode(subprogramNode);

        return programNode;
    }

    public static Node getTestFile3AbstractSyntaxTree() {
        // First Assignment Node
        BinaryExpressionNode assignment1 = new BinaryExpressionNode(ASSIGNEMENT_OPERATOR_TOKEN);

        // Left Hand of First Assignment Node
        assignment1.addNode(new UnaryNode(VARIABLE_NAME_TOKEN_NUMBER));

        // Right Hand of Fist Assignment Node

        // First Plus Binary Expression
        BinaryExpressionNode plusBinaryExpression1 = new BinaryExpressionNode(PLUS_OPERATOR_TOKEN);
        plusBinaryExpression1.addNode(new UnaryNode(NUMERIC_TOKEN_3));
        plusBinaryExpression1.addNode(new UnaryNode(NUMERIC_TOKEN_4));

        // First Multiplication Binary Expression
        BinaryExpressionNode multiplicationBinaryExpression1 = new BinaryExpressionNode(MULTIPLY_OPERATOR_TOKEN);
        multiplicationBinaryExpression1.addNode(plusBinaryExpression1);
        multiplicationBinaryExpression1.addNode(new UnaryNode(NUMERIC_TOKEN_5));

        // Second Multiplication Binary Expression
        BinaryExpressionNode multiplicationBinaryExpression2 = new BinaryExpressionNode(MULTIPLY_OPERATOR_TOKEN);
        multiplicationBinaryExpression2.addNode(new UnaryNode(NUMERIC_TOKEN_2));
        multiplicationBinaryExpression2.addNode(multiplicationBinaryExpression1);

        // Minus Binary Expression
        BinaryExpressionNode minusBinaryExpression = new BinaryExpressionNode(MINUS_OPERATOR_TOKEN);
        minusBinaryExpression.addNode(multiplicationBinaryExpression2);
        minusBinaryExpression.addNode(new UnaryNode(NUMERIC_TOKEN_2));

        assignment1.addNode(minusBinaryExpression);

        // Second Assignment Node
        BinaryExpressionNode assignment2 = new BinaryExpressionNode(ASSIGNEMENT_OPERATOR_TOKEN);

        // Left Hand of Second Assignment Node
        assignment2.addNode(new UnaryNode(VARIABLE_NAME_TOKEN_FACTOR));

        // Right Hand of Second Assignment Node
        BinaryExpressionNode plusBinaryExpression2 = new BinaryExpressionNode(PLUS_OPERATOR_TOKEN);
        plusBinaryExpression2.addNode(new UnaryNode(NUMERIC_TOKEN_2));
        plusBinaryExpression2.addNode(new UnaryNode(NUMERIC_TOKEN_3));

        assignment2.addNode(plusBinaryExpression2);

        // Signature Variables
        UnaryNode numberVariableNode = new UnaryNode(VARIABLE_NAME_TOKEN_NUMBER);
        numberVariableNode.addNode(new UnaryNode(INTEGER_DATA_TYPE_TOKEN));
        UnaryNode factorVariableNode = new UnaryNode(VARIABLE_NAME_TOKEN_FACTOR);
        factorVariableNode.addNode(new UnaryNode(INTEGER_DATA_TYPE_TOKEN));

        SubprogramNode subprogramNode = new SubprogramNode(
            METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC,
            DO_SOME_ARITHMETIC_METHOD_NAME,
            PUBLIC,
            OVERRIDABLE,
            VOID_RETURN_TYPE,
            CLASS
        );
        subprogramNode.addNode(numberVariableNode);
        subprogramNode.addNode(factorVariableNode);
        subprogramNode.addNode(assignment1);
        subprogramNode.addNode(assignment2);

        ProgramNode programNode = new ProgramNode(PROGRAM_TOKEN);
        programNode.addNode(subprogramNode);

        return programNode;
    }
}
