package com.core.common.exception;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

@Getter
public class CommonException extends RuntimeException {

    @Autowired
    Gson gson ;

    private static final long serialVersionUID = 1L;

    private CommonExceptionType type = null;


    public CommonException(CommonExceptionType type, String message) {
        super(type.getErrorMessage() + message);
        this.type = type;
    }

    public CommonException(CommonExceptionType type, String[] params) {
        super(CommonExceptionUtils.buildMessage(type, params));
        this.type = type;
    }

    public CommonException(String code, String[] params) {
        super(CommonExceptionUtils.buildMessage(code, params));
    }

    public CommonException(String responseMessage) {
        super(responseMessage);
    }

    public CommonException(CommonExceptionType type, Exception e) {
        super(type.getErrorCode() + type.getErrorMessage() + " / " + e.getMessage() + " / " +
                Arrays.stream(e.getStackTrace()).findFirst().get().getClassName() +  " " +
                Arrays.stream(e.getStackTrace()).findFirst().get().getLineNumber()
        );

        this.type = type;
    }



}
