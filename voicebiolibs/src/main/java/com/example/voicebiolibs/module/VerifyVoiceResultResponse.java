package com.example.voicebiolibs.module;

/**
 *
 * @author thangth
 */
public class VerifyVoiceResultResponse {
    Integer status;
    String msg;
    String code;
    Boolean is_proofing;
    Float score;
    Float threshold;
    Boolean matching;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIs_proofing() {
        return is_proofing;
    }

    public void setIs_proofing(Boolean is_proofing) {
        this.is_proofing = is_proofing;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getThreshold() {
        return threshold;
    }

    public void setThreshold(Float threshold) {
        this.threshold = threshold;
    }

    public Boolean getMatching() {
        return matching;
    }

    public void setMatching(Boolean matching) {
        this.matching = matching;
    }

    @Override
    public String toString() {
        return "VerifyVoiceResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", is_proofing=" + is_proofing +
                ", score=" + score +
                ", threshold=" + threshold +
                ", matching=" + matching +
                '}';
    }
}

