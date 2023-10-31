package org.libra.model.token;

import org.libra.exception.ExceptionGenerator;
import org.libra.model.TokenPremises;

import static org.libra.exception.ExceptionType.UNEXPECTED_TOKEN_EXCEPTION;
import static org.libra.model.token.TokenType.ACCESS_MODIFIER;
import static org.libra.model.token.TokenType.ARITHMETIC_OPERATOR;
import static org.libra.model.token.TokenType.ASSIGNMENT_OPERATOR;
import static org.libra.model.token.TokenType.DATA_TYPE;
import static org.libra.model.token.TokenType.METHOD_NAME;
import static org.libra.model.token.TokenType.NUMERIC;
import static org.libra.model.token.TokenType.PARENTHESIS;
import static org.libra.model.token.TokenType.PROGRAM;
import static org.libra.model.token.TokenType.RETURN_TYPE;
import static org.libra.model.token.TokenType.SEMICOLON;
import static org.libra.model.token.TokenType.STATIC_ACCESS;
import static org.libra.model.token.TokenType.STRING;
import static org.libra.model.token.TokenType.VARIABLE_NAME;
import static org.libra.utils.Constants.FIRST_ELEMENT;
import static org.libra.utils.Constants.FOURTH_ELEMENT;
import static org.libra.utils.Constants.PROGRAM_TOKEN_VALUE;
import static org.libra.utils.Constants.SECOND_ELEMENT;
import static org.libra.utils.Constants.THIRD_ELEMENT;
import static org.libra.utils.TokenPattern.ACCESS_MODIFIER_PATTERN;
import static org.libra.utils.TokenPattern.ARITHMETIC_OPERATOR_PATTERN;
import static org.libra.utils.TokenPattern.ASSIGNMENT_OPERATOR_PATTERN;
import static org.libra.utils.TokenPattern.NUMBER_PATTERN;
import static org.libra.utils.TokenPattern.PARENTHESIS_PATTERN;
import static org.libra.utils.TokenPattern.SEMICOLON_PATTERN;
import static org.libra.utils.TokenPattern.STATIC_ACCESS_PATTERN;
import static org.libra.utils.TokenPattern.STRING_PATTERN;
import static org.libra.utils.TokenPattern.VARIABLE_NAME_PATTERN;

public class TokenFactory {

    private boolean wasProgramTokenCreated;

    public Token produceToken(
        String keyword,
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
        if (isAccessModifier(keyword)) {
            return new AccessModifierToken(ACCESS_MODIFIER, keyword);
        } else if (isStaticAccess(keyword)) {
            return new AccessModifierToken(STATIC_ACCESS, keyword);
        } else if (isDataType(keyword, keywordIndex, tokenPremises)) {
            return new DataTypeToken(DATA_TYPE, keyword);
        } else if (isReturnType(keyword, keywordIndex, tokenPremises)) {
            return new DataTypeToken(RETURN_TYPE, keyword);
        } else if (isEntityName(keyword, keywordIndex, tokenPremises)) {
            return new EntityNameToken(VARIABLE_NAME, keyword);
        } else if (isMethodName(keyword, keywordIndex, tokenPremises)) {
            return new EntityNameToken(METHOD_NAME, keyword);
        } else if (isAssignmentOperator(keyword)) {
            return new BinaryExpressionToken(ASSIGNMENT_OPERATOR, keyword);
        } else if (isParenthesis(keyword)) {
            return new SpecialSymbolToken(PARENTHESIS, keyword);
        } else if (isNumber(keyword)) {
            return new ConstantToken(NUMERIC, keyword);
        } else if (isArithmeticOperation(keyword)) {
            return new BinaryExpressionToken(ARITHMETIC_OPERATOR, keyword);
        } else if (isString(keyword)) {
            return new ConstantToken(STRING, keyword);
        } else if (isSemicolon(keyword)) {
            return new InstructionToken(SEMICOLON, keyword);
        }
        else {
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

    private boolean isParenthesis(String keyword) {
        return keyword.matches(PARENTHESIS_PATTERN.getRegex());
    }

    private boolean isNumber(String keyword) {
        return keyword.matches(NUMBER_PATTERN.getRegex());
    }

    private boolean isArithmeticOperation(String keyword) {
        return keyword.matches(ARITHMETIC_OPERATOR_PATTERN.getRegex());
    }

    private boolean isEntityName(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return (((keywordIndex == FIRST_ELEMENT || keywordIndex == SECOND_ELEMENT)
                    && !tokenPremises.isMethodDeclaration())
                || (tokenPremises.isInsideMethodSignature()
                    && tokenPremises.getMethodSignatureCurrentPosition() % 2 != 0))
            && keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
    }

    private boolean isMethodName(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return (keywordIndex == SECOND_ELEMENT || keywordIndex == THIRD_ELEMENT || keywordIndex == FOURTH_ELEMENT)
            && keyword.matches(VARIABLE_NAME_PATTERN.getRegex())
            && tokenPremises.isMethodDeclaration()
            && !tokenPremises.isInsideMethodSignature();
    }

    private boolean isDataType(String keyword, int keywordIndex, TokenPremises tokenPremises) {
        return ((keywordIndex == FIRST_ELEMENT && !tokenPremises.isMethodDeclaration())
                || (tokenPremises.isMethodDeclaration()
                    && tokenPremises.isInsideMethodSignature()
                    && tokenPremises.getMethodSignatureCurrentPosition() % 2 == 0))
            && keyword.matches(VARIABLE_NAME_PATTERN.getRegex());
    }

    private boolean isAccessModifier(String keyword) {
        return keyword.matches(ACCESS_MODIFIER_PATTERN.getRegex());
    }

    private boolean isString(String keyword) {
        return keyword.matches(STRING_PATTERN.getRegex());
    }

    private boolean isSemicolon(String keyword) {
        return keyword.matches(SEMICOLON_PATTERN.getRegex());
    }

    private boolean isStaticAccess(String keyword) {
        return keyword.matches(STATIC_ACCESS_PATTERN.getRegex());
    }

    private boolean isAssignmentOperator(String keyword) {
        return keyword.matches(ASSIGNMENT_OPERATOR_PATTERN.getRegex());
    }

    private boolean isReturnType(
        String keyword,
        int keywordIndex,
        TokenPremises tokenPremises
    ) {
         return (keywordIndex == FIRST_ELEMENT || keywordIndex == SECOND_ELEMENT || keywordIndex == THIRD_ELEMENT)
             && keyword.matches(VARIABLE_NAME_PATTERN.getRegex())
             && tokenPremises.isMethodDeclaration()
             && !tokenPremises.isInsideMethodSignature();
    }
}
