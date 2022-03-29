package com.example.voicebiolibs.connect;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CompareVoiceCommunication {
    @POST("do_compare")
    @Multipart
    Call<CompareVoiceResponse> compareVoice(
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part("threshold") RequestBody gender
    );
}
