package com.example.voicebiolibs.module;



import com.example.voicebiolibs.connect.ModuleProvider;
import com.example.voicebiolibs.connect.VerifyVoiceCommunication;
import com.example.voicebiolibs.connect.VerifyVoiceResponse;

import java.io.File;
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

/**
 *
 * @author thangth
 */
public class VerifyVoiceId {

    VerifyVoiceResultResponse responseResult = new VerifyVoiceResultResponse();
    String phoneStr;
    File myFileRecorder;

    public String getPhoneStr() {
        return phoneStr;
    }

    public void setPhoneStr(String phoneStr) {
        this.phoneStr = phoneStr;
    }

    public File getMyFileRecorder() {
        return myFileRecorder;
    }

    public void setMyFileRecorder(File myFileRecorder) {
        this.myFileRecorder = myFileRecorder;
    }

    public VerifyVoiceResultResponse verify8KResultResponse() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<VerifyVoiceResultResponse> future = threadPoll.submit(new Callable<VerifyVoiceResultResponse>() {
            @Override
            public VerifyVoiceResultResponse call() throws Exception {
                VerifyVoiceCommunication verifyService = ModuleProvider.self().getRetrofit().create(VerifyVoiceCommunication.class);
                RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneStr);
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), myFileRecorder);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file", myFileRecorder.getName(), requestFile);
                Call<VerifyVoiceResponse> call = verifyService.verify(phone, reqFile1);
                Response<VerifyVoiceResponse> response = call.execute();

                switch (response.code()) {
                    case 200:
                        // System.out.println("res in: " + response.body());
                        if (response.body().getStatus_code() == 1) {
                            Double score = Math.round(response.body().getScore() * 100.0) / 100.0;
                            responseResult.setScore(score);
                            responseResult.setMsg(response.body().getMsg());
                            responseResult.setStatus_code(response.body().getStatus_code());
                            responseResult.setError(response.body().getError());
                            responseResult.setCode(response.body().getCode());
                        } else {
                            Double score = 0.0;
                            responseResult.setScore(score);
                            responseResult.setMsg(response.body().getMsg());
                            responseResult.setStatus_code(response.body().getStatus_code());
                            responseResult.setError(response.body().getError());
                            responseResult.setCode(response.body().getCode());
                        }
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        responseResult.setScore(0.0);
                        break;
                    case 422:
                        responseResult.setMsg("Missing params");
                        responseResult.setScore(0.0);
                    default:
                        responseResult.setMsg("System Error");
                        responseResult.setScore(0.0);
                        break;
                }
                return responseResult;
            }
        });
        VerifyVoiceResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new VerifyVoiceResultResponse();
            responseResult.setMsg("System Error");
            responseResult.setScore(0.0);
        }
        //    System.out.println("Result: " + responseResult);
        return responseResult;
    }
}

