package com.example.voicebiolibs.module;

/**
 *
 * @author thangth
 */
public class CheckAuthResponseResult {
    String result;
    Integer status;
    String msg;
    String token;
    Double expire_time;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Double expire_time) {
        this.expire_time = expire_time;
    }

    @Override
    public String toString() {
        return "CheckAuthResponseResult{" +
                "result='" + result + '\'' +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", token='" + token + '\'' +
                ", expire_time=" + expire_time +
                '}';
    }
}

