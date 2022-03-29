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
public class CheckAuthResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("token")
    private String token;
    @SerializedName("msg")
    private String msg;
    @SerializedName("expire_time")
    private Double expire_time;




    public CheckAuthResponse() {
    }

    public CheckAuthResponse(Integer status, String token, String msg, Double expire_time) {
        this.status = status;
        this.token = token;
        this.msg = msg;
        this.expire_time = expire_time;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

//    public void setToken(String token) {
//        this.token = token;
//    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Double getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Double expire_time) {
        this.expire_time = expire_time;
    }

    @Override
    public String toString() {
        return "CheckAuthResponse{" +
                "status=" + status +
                ", token='" + token + '\'' +
                ", msg='" + msg + '\'' +
                ", expire_time=" + expire_time +
                '}';
    }
}
