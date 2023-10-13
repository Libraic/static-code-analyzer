package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.exception.ExceptionGenerator;
import org.libra.model.token.Token;
import org.libra.utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.libra.exception.ExceptionType.FILE_PROCESSING_EXCEPTION;
import static org.libra.utils.Constants.*;

/**
 * This class acts as parser for input files, which contains the code be analyzed.
 */

@AllArgsConstructor
public class FileParser {

    private final Lexer lexer;

    /**
     * The method takes the input file and reads it character by character.
     * The input file represents the source code file.
     * While reading, it converts each sequence of characters into a keyword.
     * For example, if the parsing algorithm find a sequence of digits, it converts it to a number keyword.
     * <br><br>
     * <ul>
     *     <li><strong>>12, 3.14159, -14</strong> - Number keywords</li>
     *     <li><strong>"Java", "Static Code Analyzer"</strong> - String keywords</li>
     *     <li><strong>;</strong> - Semicolon keyword</strong></li>
     *     <li><strong>java.lang.Integer, int, DeclaredDataType</strong> - Data Type keywords</li>
     *     <li><strong>=</strong> - Assignment keyword</li>
     *     <li><strong>+, -, /, *</strong> - Arithmetic keywords</li>
     *     <li><strong>(, )</strong> - Open/Closed Parenthesis keywords</li>
     *     <li><strong>[, ]</strong> - Open/Closed Brackets keywords</li>
     *     <li><strong>{, }</strong> - Open/Closed Braces keywords</li>
     * </ul>
     * @param fileName the input file name.
     */
    public List<Token> parseFile(String fileName) {
        List<Token> tokens = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        try (
            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader)
        ) {
            int asciiCode;
            StringBuilder keyword = new StringBuilder();
            while ((asciiCode = reader.read()) != -1) {
                char character = (char)asciiCode;
                if (isNewLineOrCarriageReturn(character)) {
                    continue;
                }

                if (isKeywordComplete(character, keyword)) {
                    keywords.add(keyword.toString());
                    keyword.delete(0, keyword.length());
                }

                if (isSemicolon(character)) {
                    keywords.add(Character.toString(character));
                    tokens.addAll(lexer.tokenize(keywords));
                    keywords = new ArrayList<>();
                    continue;
                }

                appendCharacterToKeyword(character, keyword);
            }
        } catch (Exception e) {
            throw ExceptionGenerator.of(FILE_PROCESSING_EXCEPTION);
        }

        return tokens;
    }

    private boolean isKeywordComplete(char character, StringBuilder keyword) {
        if (keyword.length() == 0) {
            return false;
        }

        char lastCharacterFromKeyword = keyword.charAt(keyword.length() - 1);
        return isSpace(character) ||
            isSemicolon(character) ||
            isAssignmentOperator(character) ||
            isAssignmentOperator(lastCharacterFromKeyword) ||
            isArithmeticOperator(character) ||
            isArithmeticOperator(lastCharacterFromKeyword) ||
            isParenthesis(character) ||
            isParenthesis(lastCharacterFromKeyword);
    }

    private boolean isArithmeticOperator(char character) {
        return character == Constants.PLUS_OPERATOR ||
            character == Constants.MINUS_OPERATOR ||
            character == Constants.MULTIPLICATION_OPERATOR ||
            character == Constants.DIVISION_OPERATOR;
    }

    private boolean isAssignmentOperator(char character) {
        return character == ASSIGNMENT_OPERATOR;
    }

    private boolean isSpace(char character) {
        return character == SPACE;
    }

    private boolean isSemicolon(char character) {
        return character == SEMICOLON;
    }

    private boolean isParenthesis(char character) {
        return character == Constants.OPEN_PARENTHESIS_CHARACTER || character == CLOSED_PARENTHESIS;
    }

    private boolean isNewLineOrCarriageReturn(char character) {
        return character == CARRIAGE_RETURN || character == NEW_LINE;
    }

    private void appendCharacterToKeyword(char character, StringBuilder keyword) {
        if (character != SPACE && character != SEMICOLON) {
            keyword.append(character);
        }
    }
}
