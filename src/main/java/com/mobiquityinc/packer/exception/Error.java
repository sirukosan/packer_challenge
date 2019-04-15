package com.mobiquityinc.packer.exception;

/**
 * Error types for packing exceptions
 */
public enum Error {
    FILE_NOT_FOUND(0, "File {0} not found"),
    CANNOT_READ_LINE(1, "Cannot read line"),
    MUST_CONTAIN_ONE_COLON(2, "Line {0}: must contain exactly one colon"),
    MAX_WEIGHT_MUST_BE_NUMBER(3, "Line {0}: should start with a number"),
    WRONG_MAX_WEIGHT(4, "Line {0}: total knapsack weight should be less than {1}"),
    WRONG_WEIGHT(5, "Line {0}: weight should be less than {1}"),
    WRONG_VALUE(6, "Line {0}: value should be less than {1}"),
    TOO_MANY_ITEMS(7, "Line {0}: items number shouldn't be more than{1}");

    private final int code;
    private final String description;

    Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }
}