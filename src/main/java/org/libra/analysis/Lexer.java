package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.exception.ExceptionGenerator;
import org.libra.model.Token;

import java.util.ArrayList;
import java.util.List;

import static org.libra.exception.ExceptionType.UNEXPECTED_TOKEN_EXCEPTION;
import static org.libra.model.TokenType.*;
import static org.libra.utils.Constants.ASSIGNMENT_LITERAL;
import static org.libra.utils.Constants.CLOSED_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.FIRST_ELEMENT;
import static org.libra.utils.Constants.OPEN_PARENTHESIS_LITERAL;
import static org.libra.utils.Constants.SECOND_ELEMENT;
import static org.libra.utils.Constants.SECOND_STARTING_INDEX;
import static org.libra.utils.Constants.THIRD_ELEMENT;
import static org.libra.utils.Constants.THIRD_STARTING_INDEX;
import static org.libra.utils.Constants.ZERO_STARTING_INDEX;
import static org.libra.utils.TokenPattern.*;

/**
 * The class used for tokenizing each input sent by the Parser class.
 */
@AllArgsConstructor
public class Lexer {

    public List<Token> tokenize(List<String> keywords) {
        List<Token> tokens = new ArrayList<>();
        int startingIndex = handleAssignmentInstructionsIfPresent(keywords, tokens);

        for (int i = startingIndex; i < keywords.size(); ++i) {
            String keyword = keywords.get(i);
            if (isOpenParenthesis(keyword)) {
                tokens.add(new Token(OPEN_PARENTHESIS, keyword));
            } else if (isClosedParenthesis(keyword)) {
                tokens.add(new Token(CLOSED_PARENTHESIS, keyword));
            } else if (isNumber(keyword)) {
                tokens.add(new Token(NUMERIC, keyword));
            } else if (isArithmeticOperation(keyword)) {
                tokens.add(new Token(ARITHMETIC_OPERATOR, keyword));
            } else if (isVariable(keyword)) {
                tokens.add(new Token(VARIABLE_NAME, keyword));
            } else if (isString(keyword)) {
                tokens.add(new Token(STRING, keyword));
            } else {
                throw ExceptionGenerator.of(UNEXPECTED_TOKEN_EXCEPTION);
            }
        }

        return tokens;
    }

    private int handleAssignmentInstructionsIfPresent(
        List<String> keywords,
        List<Token> tokens
    ) {
        if (isAssignmentInstruction(keywords)) {
            tokens.add(new Token(VARIABLE_NAME, keywords.get(FIRST_ELEMENT)));
            tokens.add(new Token(ASSIGNMENT_OPERATOR, keywords.get(SECOND_ELEMENT)));
            return SECOND_STARTING_INDEX;
        } else if (isReassignmentInstruction(keywords)) {
            tokens.add(new Token(DATA_TYPE, keywords.get(FIRST_ELEMENT)));
            tokens.add(new Token(VARIABLE_NAME, keywords.get(SECOND_ELEMENT)));
            tokens.add(new Token(ASSIGNMENT_OPERATOR, keywords.get(THIRD_ELEMENT)));
            return THIRD_STARTING_INDEX;
        }

        return ZERO_STARTING_INDEX;
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
}
