package com.example.voicebiolibs.connect;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author thangth
 */
@Getter
@Setter
@Data
public class CheckAudioResponse {
    @SerializedName("msg")
    private String msg;
    @SerializedName("status_code")
    private Integer status_code;
    @SerializedName("code")
    private Integer code;
    @SerializedName("error")
    private String error;

    public CheckAudioResponse(){

    }

    public CheckAudioResponse(String msg, Integer status_code,Integer code, String error) {
        this.msg = msg;
        this.code = code;
        this.status_code = status_code;
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
        return "CheckAudioResponse{" +
                "msg='" + msg + '\'' +
                ", status_code=" + status_code +
                ", code=" + code +
                ", error='" + error + '\'' +
                '}';
    }
}
