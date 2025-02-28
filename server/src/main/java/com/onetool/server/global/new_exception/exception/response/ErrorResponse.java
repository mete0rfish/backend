package com.onetool.server.global.new_exception.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.onetool.server.global.new_exception.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "displayType"})
public class ErrorResponse {

    @JsonProperty("code")
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String description;
    private final String customErrorMessage;

    public static ErrorResponse generateErrorResponse(ApiException apiException) {
        return new ErrorResponse(
                apiException.getErrorCode().getHttpStatus(),
                apiException.getErrorCode().getServerCode(),
                apiException.getErrorCode().getDescription(),
                apiException.getCustomErrorMessage());
    }

    public static ErrorResponse generateErrorResponse(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorCode = exception.getClass().getSimpleName();
        String description = exception.getMessage() != null ? exception.getMessage() : "No detailed message available.";
        String customErrorMessage = "An error occurred: " + errorCode;

        return new ErrorResponse(status, errorCode, description, customErrorMessage);
    }
}
