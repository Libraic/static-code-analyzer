package org.libra.model;

public enum State {
    FINAL,
    OVERRIDABLE;

    public static State createState(String state) {
        return state.equals("final") ? FINAL : OVERRIDABLE;
    }
}
