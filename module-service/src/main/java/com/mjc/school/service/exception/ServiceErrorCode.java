package com.mjc.school.service.exception;

public enum ServiceErrorCode {
    NEWS_ID_DOES_NOT_EXIST("000001", "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST("000002", "Author with id %d does not exist."),
    TAG_ID_DOES_NOT_EXIST("000003", "Tag with id %d does not exist."),
    COMMENT_ID_DOES_NOT_EXIST("000004","Comment with id %d does not exist." ),
    AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID("000005", "Author not found for news with id %d."),
    COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID("000006","Comment not found for news with id %d."),
    STRING_VALIDATION("000007",
            "Text \"%s\" does not meet requirements: null, less or more than necessary (%s)."),
    NUMBER_VALIDATION("000008",
            "Number \"%d\" does not meet requirements: negative, null, less or more than necessary (%s)."),
    VALIDATION("000013", "Validation failed: %s");

    private final String errorMessage;

    ServiceErrorCode(String errorCode, String message) {
        this.errorMessage = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + message;
    }

    public String getMessage() {
        return errorMessage;
    }

}
