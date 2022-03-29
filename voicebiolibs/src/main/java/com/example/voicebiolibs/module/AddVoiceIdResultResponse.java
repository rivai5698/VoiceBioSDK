package com.example.voicebiolibs.module;

/**
 *
 * @author thangth
 */
public class AddVoiceIdResultResponse {
    Integer status;
    String msg;
    String file_code;
    String code;

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

    public String getFile_code() {
        return file_code;
    }

    public void setFile_code(String file_code) {
        this.file_code = file_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AddVoiceIdResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", file_code='" + file_code + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

