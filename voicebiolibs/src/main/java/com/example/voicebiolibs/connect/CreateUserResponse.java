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
public class CreateUserResponse {
    @SerializedName("status")
    private Integer status;
    @SerializedName("msg")
    private String msg;

    public Integer getStatus_code() {
        return status;
    }

    public void setStatus_code(Integer status) {
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
        return "CreateUserResponse{" + "v=" + status + ", msg=" + msg + '}';
    }








}
