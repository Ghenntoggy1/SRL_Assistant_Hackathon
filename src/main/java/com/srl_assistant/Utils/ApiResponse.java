package com.srl_assistant.Utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ApiResponse<T> {

    private int errorCode;
    private String message;
    private T data;

    public ApiResponse(String message, T data) {
        this(message, data, 0);
    }
    public ApiResponse(String message, T data, int errorCode) {
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

}