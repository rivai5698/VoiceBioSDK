package com.example.voicebiolibs.module;

import com.example.voicebiolibs.connect.CheckAudioCommunication;
import com.example.voicebiolibs.connect.CheckAudioResponse;
import com.example.voicebiolibs.connect.EnrollUserCommunication;
import com.example.voicebiolibs.connect.EnrollUserResponse;
import com.example.voicebiolibs.connect.ModuleProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class EnrollUser {
    String userCode;

    EnrollUserResultResponse resultResponse = new EnrollUserResultResponse();

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public EnrollUserResultResponse getResultResponse() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<EnrollUserResultResponse> future = threadPoll.submit(new Callable<EnrollUserResultResponse>() {
            @Override
            public EnrollUserResultResponse call() throws Exception {
                EnrollUserCommunication enrollUserCommunication = ModuleProvider.self().getRetrofit().create(EnrollUserCommunication.class);
                RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), userCode);
                Call<EnrollUserResponse> call = enrollUserCommunication.enroll(user_code);
                Response<EnrollUserResponse> response = call.execute();
                switch (response.code()) {
                    case 200:
                        System.out.println("res in: " + response.body());
                        resultResponse.setMsg(response.body().getMsg());
                        resultResponse.setStatus(response.body().getStatus());
                        break;
                    case 401:
                        resultResponse.setMsg("Access token is wrong or missing");

                        break;
                    case 422:
                        resultResponse.setMsg("Missing params");
                    default:
                        resultResponse.setMsg("System Error");
                        break;
                }
                return resultResponse;
            }
        });
        EnrollUserResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new EnrollUserResultResponse();
            responseResult.setMsg("System Error");
        }
        System.out.println("Result: " + responseResult);
        return responseResult;
    }
}
