package org.libra.model.token;

import org.libra.exception.ExceptionGenerator;

import java.util.List;

import static org.libra.exception.ExceptionType.UNEXPECTED_TOKEN_EXCEPTION;
import static org.libra.model.token.TokenType.*;
import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.model.token.TokenType.CLOSED_PARENTHESIS;
import static org.libra.model.token.TokenType.OPEN_PARENTHESIS;
import static org.libra.model.token.TokenType.SEMICOLON;
import static org.libra.utils.Constants.*;
import static org.libra.utils.Constants.CLOSED_PARENTHESIS_LITERAL;
import static org.libra.utils.TokenPattern.*;

public class TokenFactory {

    public Token produceToken(String keyword) {
        if (isOpenParenthesis(keyword)) {
            return new SpecialSymbolToken(OPEN_PARENTHESIS, keyword);
        } else if (isClosedParenthesis(keyword)) {
            return new SpecialSymbolToken(CLOSED_PARENTHESIS, keyword);
        } else if (isNumber(keyword)) {
            return new ConstantToken(NUMERIC, keyword);
        } else if (isArithmeticOperation(keyword)) {
            return new BinaryExpressionToken(ARITHMETIC_OPERATOR, keyword);
        } else if (isVariable(keyword)) {
            return new VariableNameToken(VARIABLE_NAME, keyword);
        } else if (isString(keyword)) {
            return new ConstantToken(STRING, keyword);
        } else if (isSemicolon(keyword)) {
            return new InstructionToken(SEMICOLON, keyword);
        } else {
            throw ExceptionGenerator.of(UNEXPECTED_TOKEN_EXCEPTION);
        }
    }

    public int handleAssignmentInstructionsIfPresent(
        List<String> keywords,
        List<Token> tokens
    ) {
        if (isAssignmentInstruction(keywords)) {
            tokens.add(new VariableNameToken(VARIABLE_NAME, keywords.get(FIRST_ELEMENT)));
            tokens.add(new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keywords.get(SECOND_ELEMENT)));
            tokens.add(new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keywords.get(SECOND_ELEMENT)));
            return SECOND_STARTING_INDEX;
        } else if (isReassignmentInstruction(keywords)) {
            tokens.add(new DataTypeToken(DATA_TYPE, keywords.get(FIRST_ELEMENT)));
            tokens.add(new VariableNameToken(VARIABLE_NAME, keywords.get(SECOND_ELEMENT)));
            tokens.add(new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keywords.get(THIRD_ELEMENT)));
            return THIRD_STARTING_INDEX;
        }

        return ZERO_STARTING_INDEX;
    }

    public Token createProgramToken() {
        return new ProgramToken(PROGRAM, PROGRAM_TOKEN_VALUE);
    }

    private boolean isAssignmentInstruction(List<String> keywords) {
        return keywords.get(SECOND_ELEMENT).equals(ASSIGNMENT_LITERAL);
    }

    private boolean isReassignmentInstruction(List<String> keywords) {
        return keywords.get(THIRD_ELEMENT).equals(ASSIGNMENT_LITERAL);
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

    private boolean isVariable(String keyword) {
        return keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
    }

    private boolean isString(String keyword) {
        return keyword.matches(STRING_PATTERN.getRegex());
    }

    private boolean isSemicolon(String keyword) {
        return keyword.matches(SEMICOLON_PATTERN.getRegex());
    }
}
