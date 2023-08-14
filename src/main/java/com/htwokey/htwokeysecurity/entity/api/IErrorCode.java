package com.htwokey.htwokeysecurity.entity.api;

/**
 * @author hchbo
 * @date 2023/3/29 10:28
 */
public interface IErrorCode {
    /**
     * 返回码
     * @return long
     */
    long getCode();

    /**
     * 返回信息
     * @return String
     */
    String getMessage();
}
