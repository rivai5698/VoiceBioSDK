package com.example.voicebiolibs.module;

/**
 *
 * @author thangth
 */
public class CreateUserResult {
    Integer status;
    String msg;

    public Integer getStatus_code() {
        return status;
    }

    public void setStatus_code(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CreateUserResult{" + "status=" + status + ", msg=" + msg + '}';
    }









}
