package com.example.voicebiolibs.module;

public class EnrollUserResultResponse {
    Integer status;
    String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        return "EnrollUserResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
