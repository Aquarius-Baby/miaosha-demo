package com.demo.miaoshademo.exception;


import com.demo.miaoshademo.common.CmsStatus;

public class GlobalException extends RuntimeException {

    private CmsStatus status;

    public GlobalException(CmsStatus status) {
        super();
        this.status = status;
    }

    public CmsStatus getStatus() {
        return status;
    }

    public void setStatus(CmsStatus status) {
        this.status = status;
    }
}
