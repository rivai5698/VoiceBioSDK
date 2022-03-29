package com.example.voicebiolibs.module;

public class DelVoiceIdResultResponse {
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
        return "DelVoiceIdResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
