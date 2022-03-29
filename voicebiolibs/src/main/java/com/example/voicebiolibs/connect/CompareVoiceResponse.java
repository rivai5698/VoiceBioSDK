package com.example.voicebiolibs.connect;

import com.google.gson.annotations.SerializedName;

public class CompareVoiceResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private ResultCompare resultCompare;


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

    public ResultCompare getResultCompare() {
        return resultCompare;
    }

    public void setResultCompare(ResultCompare resultCompare) {
        this.resultCompare = resultCompare;
    }

    @Override
    public String toString() {
        return "CompareVoiceResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", resultCompare=" + resultCompare +
                '}';
    }
}
