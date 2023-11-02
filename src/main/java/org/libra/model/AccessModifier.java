package org.libra.model;

public enum AccessModifier {
    PUBLIC,
    PRIVATE,
    PROTECTED,
    PACKAGE_PRIVATE;

    public static AccessModifier createAccessModifier(String accessModifier) {
        switch (accessModifier) {
            case "public":
                return PUBLIC;
            case "private":
                return PRIVATE;
            case "protected":
                return PROTECTED;
        }

        return PACKAGE_PRIVATE;
    }
}
