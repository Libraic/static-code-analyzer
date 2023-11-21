package org.libra.analysis;

import lombok.AllArgsConstructor;
import org.libra.exception.ExceptionGenerator;
import org.libra.utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.libra.exception.ExceptionType.FILE_PROCESSING_EXCEPTION;
import static org.libra.utils.Constants.ASSIGNMENT_OPERATOR;
import static org.libra.utils.Constants.CARRIAGE_RETURN;
import static org.libra.utils.Constants.CLOSED_PARENTHESES;
import static org.libra.utils.Constants.COMMA;
import static org.libra.utils.Constants.NEW_LINE;
import static org.libra.utils.Constants.OPEN_CURLY_BRACE;
import static org.libra.utils.Constants.SEMICOLON;
import static org.libra.utils.Constants.SPACE;

/**
 * This class acts as parser for input files, which contains the code be analyzed.
 */

@AllArgsConstructor
public class FileParser {

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
     *     <li><strong>(, )</strong> - Open/Closed Parentheses keywords</li>
     *     <li><strong>[, ]</strong> - Open/Closed Brackets keywords</li>
     *     <li><strong>{, }</strong> - Open/Closed Braces keywords</li>
     * </ul>
     * @param fileName the input file name.
     */
    public Map<Integer, List<String>> parseFile(String fileName) {
        List<String> keywords = new ArrayList<>();
        Map<Integer, List<String>> keywordsFromEachRow = new LinkedHashMap<>();
        int row = 1;

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

                if (isSemicolon(character) || isCurlyBrace(character)) {
                    keywords.add(Character.toString(character));
                    keywordsFromEachRow.put(row++, keywords);
                    keywords = new ArrayList<>();
                    continue;
                }

                appendCharacterToKeyword(character, keyword);
            }
        } catch (IOException e) {
            throw ExceptionGenerator.of(FILE_PROCESSING_EXCEPTION);
        }

        return keywordsFromEachRow;
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
            isParentheses(character) ||
            isParentheses(lastCharacterFromKeyword) ||
            isComma(character);
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

    private boolean isCurlyBrace(char character) {
        return character == OPEN_CURLY_BRACE;
    }

    private boolean isComma(char character) {
        return character == COMMA;
    }

    private boolean isParentheses(char character) {
        return character == Constants.OPEN_PARENTHESES_CHARACTER || character == CLOSED_PARENTHESES;
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
