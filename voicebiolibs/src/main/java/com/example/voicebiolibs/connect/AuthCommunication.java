package com.example.voicebiolibs.connect;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AuthCommunication {
    @POST("api/voicebio/auth")
    @Multipart
    Call<CheckAuthResponse> checkAuth(
            @Part("username") RequestBody username,
            @Part("password") RequestBody password
    );
}
