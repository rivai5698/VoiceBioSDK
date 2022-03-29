package com.example.voicebiolibs.connect;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("score")
    Float score;
    @SerializedName("threshold")
    Float threshold;
    @SerializedName("matching")
    Boolean matching;
}
