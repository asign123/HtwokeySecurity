package com.htwokey.htwokeysecurity.exception;

import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * @author hchbo
 * @date 2023/3/29 15:52
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult<?> handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<?> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult<?> handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * 处理404异常
     * @param e 异常
     * @return CommonResult
     */
    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public CommonResult<?> defaultErrorHandler(HttpServletRequest req, HttpServletResponse resp, Exception e) {
        int statusCode = resp.getStatus();
        log.info("statusCode:{}", statusCode);
        if (statusCode == 404) {
            return CommonResult.notFound(e.getMessage());
        }
        return CommonResult.failed(e.getMessage());

    }


    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> handle(Exception e) {
        e.printStackTrace();
        log.error("Exception:{}", e.getMessage());
        return CommonResult.internalServerError(e.getMessage());
    }

}
