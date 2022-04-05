package com.paas.base.exception;

import com.paas.base.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;


/**
 * @Date 2022/4/4 周一 11:43
 * @Author xu
 * @FileName BusinessException
 * @Description 业务异常
 */
@Slf4j
public class BusinessException extends RuntimeException{

    protected  int code;

    private static final long serialVersionUID = 6449977071034744852L;

    public BusinessException() {
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.msg(), args));
        this.code = codeEnum.code();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
