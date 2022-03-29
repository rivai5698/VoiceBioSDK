package com.example.voicebiolibs.connect;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DeleteVoiceFileCommunication {
    @POST("enroll/delete_file")
    @Multipart
    Call<DeleteVoiceFileResponse> del(
            @Part("user_code") RequestBody user_code,
            @Part("file_code") RequestBody file_code
    );
}
