package com.demo.miaoshademo.common;


public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final Integer DEFAULT_SUCCESS_CODE = 0;
    private static final Integer DEFAULT_FAIL_CODE = 1;

    public static Result genSuccessResult() {
        Result result = new Result();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

//    public static Result genSuccessResult(String message) {
//        Result result = new Result();
//        result.setCode(DEFAULT_SUCCESS_CODE);
//        result.setMessage(message);
//        return result;
//    }

    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result genSuccessResult(Object data, String message) {
        Result result = new Result();
        result.setCode(DEFAULT_SUCCESS_CODE);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setCode(DEFAULT_FAIL_CODE);
        result.setMessage(message);
        return result;
    }

    public static Result genFailResult(Object data, String message) {
        Result result = new Result();
        result.setCode(DEFAULT_FAIL_CODE);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static Result genUnAuthorizedResult(String message) {
        Result result = new Result();
        result.setCode(DEFAULT_FAIL_CODE);
        result.setMessage(message);
        return result;
    }
}