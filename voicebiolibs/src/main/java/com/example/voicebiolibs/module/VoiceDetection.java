package com.example.voicebiolibs.module;

import com.example.voicebiolibs.connect.AddVoiceFileCommunication;
import com.example.voicebiolibs.connect.AddVoiceFileResponse;
import com.example.voicebiolibs.connect.DetectVoiceResponse;
import com.example.voicebiolibs.connect.ModuleProvider;
import com.example.voicebiolibs.connect.VoiceDetectCommunication;

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

public class VoiceDetection {
    VoiceDetectionResultResponse responseResult = new VoiceDetectionResultResponse();
    File fileName;

    public File getFileName() {
        return fileName;
    }

    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    public VoiceDetectionResultResponse detectVoiceId() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<VoiceDetectionResultResponse> future = threadPoll.submit(new Callable<VoiceDetectionResultResponse>() {
            @Override
            public VoiceDetectionResultResponse call() throws Exception {
                VoiceDetectCommunication signUpService = ModuleProvider.self().getRetrofit().create(VoiceDetectCommunication.class);
                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), fileName);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file", fileName.getName(), requestFile1);


                Call<DetectVoiceResponse> call = signUpService.detect(reqFile1);
                Response<DetectVoiceResponse> response = call.execute();
                //          System.out.println("res in: " + response.code() + " " + response.body().toString());
                switch (response.code()) {
                    case 200:
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus(response.body().getStatus());
                        responseResult.setIs_spoofing(response.body().getIs_spoofing());
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
        VoiceDetectionResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new VoiceDetectionResultResponse();
            responseResult.setMsg("System Error");
        }
        //     System.out.println("Result: " + responseResult);
        return responseResult;
    }
}
