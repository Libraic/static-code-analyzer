package org.libra.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A data structure containing the relevant information necessary for converting the given keywords into relevant
 * tokens. Since a lot of keywords may represent different tokens, the aim of these premises is to avoid ambiguity.
 */

@Builder
@Setter
@Getter
public class TokenPremises {
    /**
     * A boolean flag indicating if the present keyword is part of method declaration or not. It helps to extract
     * the metadata of the method, such as name, access, visibility and so on.
     */
    private boolean isMethodDeclaration;

    /**
     * A boolean flag indicating if the present keyword is part of method signature or not. It helps to identify
     * if the current keyword is a METHOD_NAME or a VARIABLE_NAME/DATA_TYPE.
     */
    private boolean isInsideMethodSignature;

    /**
     * A boolean flag indicating if the next keyword relative to the current one is an assignment keyword. It helps
     * to identify if the current keyword is a VARIABLE_NAME or a DATA_TYPE.
     */
    private boolean isAssignmentNextKeyword;

    /**
     * A flag that depicts if the following keywords are after the assignment operator.
     */
    private boolean isPostAssignmentKeyword;

    /**
     * An index to help the system to delimit DATA_TYPE from VARIABLE_NAME inside method signatures.
     */
    private int methodSignatureCurrentPosition;

    /**
     * A flag that indicates if the next keyword is open parenthesis.
     */
    private boolean isNextKeywordOpenParenthesis;

    /**
     * A flag that indicates if a class is being declared.
     */
    private boolean isClassDeclaration;

    /**
     * A flag that indicates that <em>class</em> keyword was found.
     */
    private boolean wasClassKeywordFound;

    /**
     * Utility method to increment the index that points to the current position inside the signature of the method.
     */
    public void incrementMethodSignatureCurrentPosition() {
        ++methodSignatureCurrentPosition;
    }
}
