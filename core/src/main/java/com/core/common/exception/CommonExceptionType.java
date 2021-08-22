package com.core.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonExceptionType {
    INTERNAL_SERVER_ERROR("1000", "Internal server error"),

    // validation
    INVALID_PARAMS("2000", "Invalid params"),
    INVALID_ITEM_INFORMATION("2000", "Invalid Item Information, 품목 정보 오류"),

    // user
    CANNOT_FOUND_USER("3000", "Cannot found user"),
    EMAIL_EXIST_EXCEPTION_MSG("3000",  "이미 계정이 존재합니다."),
    NICKNAME_EXIST_EXCEPTION_MSG("3000",  "이미 닉네임이 존재합니다."),
    NOTEXIST_ACCOUNT_EXCEPTION_MSG("3000",  "계정이 없습니다."),
    SIGNIN_EXCEPTION_MSG("3000",  "로그인정보가 일치하지 않습니다."),


    // database
    NONEXIST_DATA_EXCEPTION_MSG("8000",  "데이터를 찾을 수 없습니다. "),
    ERROR_PROCEDURE_EXECUTE("8000",  "프로시저 실행 오류"),
    // business
    NOTEXIST_ITEM("4100", "Cannot found Item"),
    NOTEXIST_ITEM_TEMPLATE("4100", "Cannot found Item template"),


    // HOMS
    EXTENSION_TYPE_ERROR("B428", "HOMS system Error"),
    SPECIAL_CHAR_ERROR("B428", "HOMS system Error"),
    REGEX_ERROR("9999", "REGEX_ERROR");

    private final String errorCode;
    private final String errorMessage;
}
