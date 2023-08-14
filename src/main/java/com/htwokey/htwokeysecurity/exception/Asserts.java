package com.htwokey.htwokeysecurity.exception;

import com.htwokey.htwokeysecurity.entity.api.IErrorCode;

/**
 * @author hchbo
 * @date 2023/3/29 13:58
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
