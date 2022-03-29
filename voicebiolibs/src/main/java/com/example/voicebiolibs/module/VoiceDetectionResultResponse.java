package com.example.voicebiolibs.module;

public class VoiceDetectionResultResponse {
    Integer status;
    String msg;
    Boolean is_spoofing;

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

    public Boolean getIs_spoofing() {
        return is_spoofing;
    }

    public void setIs_spoofing(Boolean is_spoofing) {
        this.is_spoofing = is_spoofing;
    }

    @Override
    public String toString() {
        return "VoiceDetectionResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", is_spoofing=" + is_spoofing +
                '}';
    }
}
