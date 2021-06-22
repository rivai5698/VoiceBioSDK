package com.example.voicebiolibs.module;

public class AudioCheckResultResponse {

    Integer code;
    Integer status_code;
    String msg;
    String error;

    public AudioCheckResultResponse() {
    }

    public AudioCheckResultResponse(Integer code, Integer status_code, String msg, String error) {
        this.code = code;
        this.status_code = status_code;
        this.msg = msg;
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    @Override
    public String toString() {
        return "AudioCheckResultResponse{" +
                "code=" + code +
                ", status_code=" + status_code +
                ", msg='" + msg + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}