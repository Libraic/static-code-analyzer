package org.libra.utils;

import org.libra.model.token.BinaryExpressionToken;
import org.libra.model.token.DataTypeToken;
import org.libra.model.token.EntityNameToken;
import org.libra.model.token.InstructionToken;
import org.libra.model.token.ProgramToken;
import org.libra.model.token.SpecialSymbolToken;
import org.libra.model.token.StandaloneToken;
import org.libra.model.token.Token;

import static org.libra.model.token.TokenType.ACCESS_MODIFIER;
import static org.libra.model.token.TokenType.ARITHMETIC_OPERATOR;
import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.model.token.TokenType.COMMA_SEPARATOR;
import static org.libra.model.token.TokenType.DATA_TYPE;
import static org.libra.model.token.TokenType.INSTRUCTION;
import static org.libra.model.token.TokenType.METHOD;
import static org.libra.model.token.TokenType.METHOD_DECLARATION;
import static org.libra.model.token.TokenType.NUMERIC;
import static org.libra.model.token.TokenType.PARENTHESES;
import static org.libra.model.token.TokenType.PROGRAM;
import static org.libra.model.token.TokenType.RETURN_TYPE;
import static org.libra.model.token.TokenType.STATE;
import static org.libra.model.token.TokenType.STATIC_ACCESS;
import static org.libra.model.token.TokenType.VARIABLE_NAME;

public class TestConstants {
    public static final String FILES_RELATIVE_PATH = "./src/test/resources/files";
    public static final String TEST_FILE_1 = FILES_RELATIVE_PATH + "/test-file-1.txt";
    public static final String TEST_FILE_2 = FILES_RELATIVE_PATH + "/test-file-2.txt";
    public static final String TEST_FILE_3 = FILES_RELATIVE_PATH + "/test-file-3.txt";
    public static final String PROGRAM_TOKEN_VALUE = "Java Program";
    public static final String FUNCTION_METHOD_NAME = "function";
    public static final String DO_SOME_ARITHMETIC_METHOD_NAME = "doSomeArithmetic";
    public static final String VOID_RETURN_TYPE = "void";
    public static final Token PROGRAM_TOKEN = new ProgramToken(PROGRAM, PROGRAM_TOKEN_VALUE);
    public static final Token PUBLIC_ACCESS_TOKEN = new StandaloneToken(ACCESS_MODIFIER, "public");
    public static final Token STATIC_ACCESS_TOKEN = new StandaloneToken(STATIC_ACCESS, "static");
    public static final Token STATE_TOKEN = new StandaloneToken(STATE, "final");
    public static final Token VOID_RETURN_TYPE_TOKEN = new DataTypeToken(RETURN_TYPE, "void");
    public static final Token METHOD_DECLARATION_TOKEN_FUNCTION = new EntityNameToken(
        METHOD_DECLARATION,
        "function"
    );
    public static final Token METHOD_DECLARATION_TOKEN_DO_SOME_ARITHMETIC = new EntityNameToken(
        METHOD_DECLARATION,
        "doSomeArithmetic"
    );
    public static final Token OPEN_PARENTHESES_TOKEN = new SpecialSymbolToken(PARENTHESES, "(");
    public static final Token CLOSED_PARENTHESES_TOKEN = new SpecialSymbolToken(PARENTHESES, ")");
    public static final Token METHOD_INSTRUCTION_TOKEN = new InstructionToken(METHOD, "{");
    public static final Token PRIMITIVE_INT_DATA_TYPE_TOKEN = new DataTypeToken(DATA_TYPE, "int");
    public static final Token INTEGER_DATA_TYPE_TOKEN = new DataTypeToken(DATA_TYPE, "Integer");
    public static final Token VARIABLE_NAME_TOKEN_A = new EntityNameToken(VARIABLE_NAME, "a");
    public static final Token VARIABLE_NAME_TOKEN_B = new EntityNameToken(VARIABLE_NAME, "b");
    public static final Token VARIABLE_NAME_TOKEN_NUMBER = new EntityNameToken(VARIABLE_NAME, "number");
    public static final Token VARIABLE_NAME_TOKEN_FACTOR = new EntityNameToken(VARIABLE_NAME, "factor");
    public static final Token VARIABLE_NAME_TOKEN_RESULT = new EntityNameToken(VARIABLE_NAME, "result");
    public static final Token ASSIGNEMENT_OPERATOR_TOKEN = new BinaryExpressionToken(ASSIGNMENT_OPERATOR, "=");
    public static final Token NUMERIC_TOKEN_2 = new StandaloneToken(NUMERIC, "2");
    public static final Token NUMERIC_TOKEN_3 = new StandaloneToken(NUMERIC, "3");
    public static final Token NUMERIC_TOKEN_4 = new StandaloneToken(NUMERIC, "4");
    public static final Token NUMERIC_TOKEN_5 = new StandaloneToken(NUMERIC, "5");
    public static final Token PLUS_OPERATOR_TOKEN = new BinaryExpressionToken(ARITHMETIC_OPERATOR, "+");
    public static final Token MINUS_OPERATOR_TOKEN = new BinaryExpressionToken(ARITHMETIC_OPERATOR, "-");
    public static final Token MULTIPLY_OPERATOR_TOKEN = new BinaryExpressionToken(ARITHMETIC_OPERATOR, "*");
    public static final Token INSTRUCTION_TOKEN = new InstructionToken(INSTRUCTION, ";");
    public static final Token COMMA_SEPARATOR_TOKEN = new SpecialSymbolToken(COMMA_SEPARATOR, ",");
}
