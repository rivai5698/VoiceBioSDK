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
    String userCode;
    String isFilter;
    File fileName;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getIsFilter() {
        return isFilter;
    }

    public void setIsFilter(String isFilter) {
        this.isFilter = isFilter;
    }

    public File getFileName() {
        return fileName;
    }

    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    public VerifyVoiceResultResponse verifyResultResponse() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<VerifyVoiceResultResponse> future = threadPoll.submit(new Callable<VerifyVoiceResultResponse>() {
            @Override
            public VerifyVoiceResultResponse call() throws Exception {
                VerifyVoiceCommunication verifyService = ModuleProvider.self().getRetrofit().create(VerifyVoiceCommunication.class);
                RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), userCode);
                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), fileName);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file", fileName.getName(), requestFile1);
                RequestBody is_filter = RequestBody.create(MediaType.parse("multipart/form-data"), isFilter);
                Call<VerifyVoiceResponse> call = verifyService.verify(user_code, reqFile1,is_filter);
                Response<VerifyVoiceResponse> response = call.execute();

                switch (response.code()) {
                    case 200:
                         System.out.println("res in: " + response.body());
                        responseResult.setCode(response.body().getCode());
                        responseResult.setStatus(response.body().getStatus());
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setMatching(response.body().getResult().getMatching());
                        responseResult.setIs_proofing(response.body().getIs_proofing());
                        responseResult.setThreshold(response.body().getResult().getThreshold());
                        responseResult.setMatching(response.body().getResult().getMatching());
                        responseResult.setScore(response.body().getResult().getScore());

//                        if (response.body().getStatus_code() == 1) {
//                            Double score = Math.round(response.body().getScore() * 100.0) / 100.0;
//                            responseResult.setScore(score);
//                            responseResult.setMsg(response.body().getMsg());
//                            responseResult.setStatus_code(response.body().getStatus_code());
//                            responseResult.setError(response.body().getError());
//                            responseResult.setCode(response.body().getCode());
//                        } else {
//                            Double score = 0.0;
//                            responseResult.setScore(score);
//                            responseResult.setMsg(response.body().getMsg());
//                            responseResult.setStatus_code(response.body().getStatus_code());
//                            responseResult.setError(response.body().getError());
//                            responseResult.setCode(response.body().getCode());
//                        }
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        break;
                    case 422:
                        responseResult.setMsg("Missing params");
                    default:
                        responseResult.setMsg("System Error");
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
            float a = 0;
            responseResult.setScore(a);
        }
        //    System.out.println("Result: " + responseResult);
        return responseResult;
    }
}

