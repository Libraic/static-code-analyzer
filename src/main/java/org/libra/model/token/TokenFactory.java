package org.libra.model.token;

import org.libra.exception.ExceptionGenerator;

import static org.libra.exception.ExceptionType.UNEXPECTED_TOKEN_EXCEPTION;
import static org.libra.model.token.TokenType.*;
import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.model.token.TokenType.CLOSED_PARENTHESIS;
import static org.libra.model.token.TokenType.OPEN_PARENTHESIS;
import static org.libra.model.token.TokenType.SEMICOLON;
import static org.libra.utils.Constants.CLOSED_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.FIRST_ELEMENT;
import static org.libra.utils.Constants.OPEN_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.PROGRAM_TOKEN_VALUE;
import static org.libra.utils.TokenPattern.ARITHMETIC_OPERATOR_PATTERN;
import static org.libra.utils.TokenPattern.ASSIGNMENT_OPERATOR_PATTERN;
import static org.libra.utils.TokenPattern.NUMBER_PATTERN;
import static org.libra.utils.TokenPattern.SEMICOLON_PATTERN;
import static org.libra.utils.TokenPattern.STRING_PATTERN;
import static org.libra.utils.TokenPattern.VARIABLE_NAME_PATTERN;

public class TokenFactory {

    private boolean wasProgramTokenCreated;

    public Token produceToken(String keyword, int keywordIndex) {
        if (isDataType(keyword, keywordIndex)) {
            return new DataTypeToken(DATA_TYPE, keyword);
        } else if (isVariable(keyword, keywordIndex)) {
            return new VariableNameToken(VARIABLE_NAME, keyword);
        } else if (isAssignmentOperator(keyword)) {
            return new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keyword);
        } else if (isOpenParenthesis(keyword)) {
            return new SpecialSymbolToken(OPEN_PARENTHESIS, keyword);
        } else if (isClosedParenthesis(keyword)) {
            return new SpecialSymbolToken(CLOSED_PARENTHESIS, keyword);
        } else if (isNumber(keyword)) {
            return new ConstantToken(NUMERIC, keyword);
        } else if (isArithmeticOperation(keyword)) {
            return new BinaryExpressionToken(ARITHMETIC_OPERATOR, keyword);
        } else if (isString(keyword)) {
            return new ConstantToken(STRING, keyword);
        } else if (isSemicolon(keyword)) {
            return new InstructionToken(SEMICOLON, keyword);
        } else {
            throw ExceptionGenerator.of(UNEXPECTED_TOKEN_EXCEPTION);
        }
    }

    public Token createProgramToken() {
        if (!wasProgramTokenCreated) {
            wasProgramTokenCreated = true;
            return new ProgramToken(PROGRAM, PROGRAM_TOKEN_VALUE);
        }

        return null;
    }

    private boolean isOpenParenthesis(String keyword) {
        return keyword.equals(OPEN_PARENTHESIS_LITERAL);
    }

    private boolean isClosedParenthesis(String keyword) {
        return keyword.equals(CLOSED_PARENTHESIS_LITERAL);
    }

    private boolean isNumber(String keyword) {
        return keyword.matches(NUMBER_PATTERN.getRegex());
    }

    private boolean isArithmeticOperation(String keyword) {
        return keyword.matches(ARITHMETIC_OPERATOR_PATTERN.getRegex());
    }

    private boolean isVariable(String keyword, int keywordIndex) {
        return (keywordIndex == 0 || keywordIndex == 1) && keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
    }

    private boolean isDataType(String keyword, int keywordIndex) {
        return keywordIndex == FIRST_ELEMENT && keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
    }

    private boolean isString(String keyword) {
        return keyword.matches(STRING_PATTERN.getRegex());
    }

    private boolean isSemicolon(String keyword) {
        return keyword.matches(SEMICOLON_PATTERN.getRegex());
    }

    private boolean isAssignmentOperator(String keyword) {
        return keyword.matches(ASSIGNMENT_OPERATOR_PATTERN.getRegex());
    }
}
