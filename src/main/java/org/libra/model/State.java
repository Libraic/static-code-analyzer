package org.libra.model;

public enum State {
    FINAL,
    OVERRIDABLE,
    INHERITABLE;

    public static State createState(String state) {
        return state.equals("final") ? FINAL : OVERRIDABLE;
    }
}
