package com.example.voicebiolibs.connect;

import com.google.gson.annotations.SerializedName;

public class DeleteVoiceFileResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("msg")
    private String msg;

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
        return "DeleteVoiceFileResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
