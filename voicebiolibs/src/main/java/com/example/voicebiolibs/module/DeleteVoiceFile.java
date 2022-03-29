package com.example.voicebiolibs.module;

import com.example.voicebiolibs.connect.DeleteVoiceFileCommunication;
import com.example.voicebiolibs.connect.DeleteVoiceFileResponse;
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

public class DeleteVoiceFile {
    String userCode;
    String fileCOde;

    DelVoiceIdResultResponse responseResult = new DelVoiceIdResultResponse();

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFileCOde() {
        return fileCOde;
    }

    public void setFileCOde(String fileCOde) {
        this.fileCOde = fileCOde;
    }

    public DelVoiceIdResultResponse delFileVoiceId() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<DelVoiceIdResultResponse> future = threadPoll.submit(new Callable<DelVoiceIdResultResponse>() {
            @Override
            public DelVoiceIdResultResponse call() throws Exception {
                DeleteVoiceFileCommunication signUpService = ModuleProvider.self().getRetrofit().create(DeleteVoiceFileCommunication.class);
                RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), userCode);
                RequestBody file_code = RequestBody.create(MediaType.parse("multipart/form-data"),fileCOde);


                Call<DeleteVoiceFileResponse> call = signUpService.del(user_code, file_code);
                Response<DeleteVoiceFileResponse> response = call.execute();
                //          System.out.println("res in: " + response.code() + " " + response.body().toString());
                switch (response.code()) {
                    case 200:
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus(response.body().getStatus());
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
        DelVoiceIdResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new DelVoiceIdResultResponse();
            responseResult.setMsg("System Error");
        }
        //     System.out.println("Result: " + responseResult);
        return responseResult;
    }

}
