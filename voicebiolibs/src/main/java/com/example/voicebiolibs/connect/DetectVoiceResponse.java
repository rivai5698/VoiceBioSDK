package com.example.voicebiolibs.connect;

import com.google.gson.annotations.SerializedName;

public class DetectVoiceResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("is_spoofing")
    private Boolean is_spoofing;

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
        return "DetectVoiceResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", is_spoofing=" + is_spoofing +
                '}';
    }
}
