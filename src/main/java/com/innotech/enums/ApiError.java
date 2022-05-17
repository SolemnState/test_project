package com.innotech.enums;

public enum ApiError {
    WRONG_DATE_FORMAT(1, "Date format doesn't match the pattern \"dd.MM.yyyy\""),
    INPUT_DATE_IS_NULL(2, "Input date should not be null"),
    INPUT_JSON_IS_NOT_VALID(3, "Input body is not valid");

    private Integer code;
    private String message;

    ApiError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }

}
