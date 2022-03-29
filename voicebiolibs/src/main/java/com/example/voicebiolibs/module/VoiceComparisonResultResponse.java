package com.example.voicebiolibs.module;

public class VoiceComparisonResultResponse {
    Integer status;
    String msg;
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
        return "VoiceComparisonResultResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", score=" + score +
                ", threshold=" + threshold +
                ", matching=" + matching +
                '}';
    }
}
