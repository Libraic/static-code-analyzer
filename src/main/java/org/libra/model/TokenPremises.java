package org.libra.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class TokenPremises {
    private boolean isMethodDeclaration;
    private boolean isInsideMethodSignature;
    private boolean isVariableBeingEvaluated;
    private int methodSignatureCurrentPosition;

    public void incrementMethodSignatureCurrentPosition() {
        ++methodSignatureCurrentPosition;
    }
}
