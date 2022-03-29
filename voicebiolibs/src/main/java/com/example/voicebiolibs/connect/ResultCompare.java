package com.example.voicebiolibs.connect;

import com.google.gson.annotations.SerializedName;

public class ResultCompare {
    @SerializedName("score")
    Float score;
    @SerializedName("threshold")
    Float threshold;
    @SerializedName("matching")
    Boolean matching;


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
        return "Result{" +
                "score=" + score +
                ", threshold=" + threshold +
                ", matching=" + matching +
                '}';
    }
}
