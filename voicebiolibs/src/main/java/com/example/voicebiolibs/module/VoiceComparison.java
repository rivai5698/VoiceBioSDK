package com.example.voicebiolibs.module;

import com.example.voicebiolibs.connect.CompareVoiceCommunication;
import com.example.voicebiolibs.connect.CompareVoiceResponse;
import com.example.voicebiolibs.connect.ModuleProvider;

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

public class VoiceComparison {
    VoiceComparisonResultResponse responseResult = new VoiceComparisonResultResponse();
    File fileName1, fileName2;
    String thresholdStr;

    public File getFileName1() {
        return fileName1;
    }

    public void setFileName1(File fileName1) {
        this.fileName1 = fileName1;
    }

    public File getFileName2() {
        return fileName2;
    }

    public void setFileName2(File fileName2) {
        this.fileName2 = fileName2;
    }

    public String getThresholdStr() {
        return thresholdStr;
    }

    public void setThresholdStr(String thresholdStr) {
        this.thresholdStr = thresholdStr;
    }

    public VoiceComparisonResultResponse compareVoice() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<VoiceComparisonResultResponse> future = threadPoll.submit(new Callable<VoiceComparisonResultResponse>() {
            @Override
            public VoiceComparisonResultResponse call() throws Exception {
                CompareVoiceCommunication signUpService = ModuleProvider.self().getRetrofit().create(CompareVoiceCommunication.class);
                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), fileName1);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file1", fileName1.getName(), requestFile1);
                RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), fileName2);
                MultipartBody.Part reqFile2 = MultipartBody.Part.createFormData("file2", fileName2.getName(), requestFile2);
                RequestBody threshold = RequestBody.create(MediaType.parse("multipart/form-data"), thresholdStr);
                Call<CompareVoiceResponse> call = signUpService.compareVoice(reqFile1,reqFile2,threshold);
                Response<CompareVoiceResponse> response = call.execute();
                //          System.out.println("res in: " + response.code() + " " + response.body().toString());
                switch (response.code()) {
                    case 200:
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus(response.body().getStatus());
                        responseResult.setScore(response.body().getResultCompare().getScore());
                        responseResult.setThreshold(response.body().getResultCompare().getThreshold());
                        responseResult.setMatching(response.body().getResultCompare().getMatching());
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        break;
                    case 422:
                        System.out.println("res in: " + response.code() + " " + new String(response.errorBody().bytes()));
                        responseResult.setMsg("Missing params");
                        break;
                    default:
                        responseResult.setMsg("System Error");
                        break;
                }
                return responseResult;
            }
        });
        VoiceComparisonResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new VoiceComparisonResultResponse();
            responseResult.setMsg("System Error");
        }
        //     System.out.println("Result: " + responseResult);
        return responseResult;
    }
}
