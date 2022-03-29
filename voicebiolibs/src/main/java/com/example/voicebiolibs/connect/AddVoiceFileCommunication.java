package com.example.voicebiolibs.connect;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 * @author thangth
 */
public interface AddVoiceFileCommunication {

    @POST("enroll/add_file")
    @Multipart
    Call<AddVoiceFileResponse> enroll(
            @Part("user_code") RequestBody user_code,
            @Part MultipartBody.Part file,
            @Part("is_filter") RequestBody is_filter
    );
}