package com.example.voicebiolibs.connect;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 * @author thangth
 */
public interface CreateUserCommunication {

    @POST("user/create")
    @Multipart
    Call<CreateUserResponse> createUser(

            @Part("user_code") RequestBody user_code,
            @Part("name") RequestBody name
            //@Part("gender") RequestBody gender
    );
}

