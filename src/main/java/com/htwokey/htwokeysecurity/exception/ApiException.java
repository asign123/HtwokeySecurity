package com.htwokey.htwokeysecurity.exception;

import com.htwokey.htwokeysecurity.entity.api.IErrorCode;

/**
 * @author hchbo
 * @date 2023/3/29 13:58
 */
public class ApiException extends RuntimeException{

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public IErrorCode getErrorCode() {
        return errorCode;
    }
}
