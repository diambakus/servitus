package com.orakuma.servitus.config.exception;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Generated
public class ErrorResponse {
    private String code;
    private String message;
}
