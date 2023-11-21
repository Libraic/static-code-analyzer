package org.libra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An enum class that represents different color used to output the Abstract Syntax Tree to the terminal.
 * The colors are used to ease the visualization of the tree.
 */
@AllArgsConstructor
@Getter
public enum Color {
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m");

    private final String value;
}
