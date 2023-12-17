package com.zzy.exception;

import com.zzy.vo.common.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GuiguException extends RuntimeException{

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public GuiguException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
