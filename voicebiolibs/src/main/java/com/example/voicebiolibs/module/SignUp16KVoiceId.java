package com.example.voicebiolibs.module;



import com.example.voicebiolibs.connect.ModuleProvider;
import com.example.voicebiolibs.connect.SignUp16KCommunication;
import com.example.voicebiolibs.connect.SignUp16KResponse;

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
public class SignUp16KVoiceId {

    SignUp16KResultResponse responseResult = null;
    String phoneString;
    File file1, file2, file3;

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public File getFile1() {
        return file1;
    }

    public void setFile1(File file1) {
        this.file1 = file1;
    }

    public File getFile2() {
        return file2;
    }

    public void setFile2(File file2) {
        this.file2 = file2;
    }

    public File getFile3() {
        return file3;
    }

    public void setFile3(File file3) {
        this.file3 = file3;
    }

    public SignUp16KResultResponse signUp16KVoiceId() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<SignUp16KResultResponse> future = threadPoll.submit(new Callable<SignUp16KResultResponse>() {
            @Override
            public SignUp16KResultResponse call() throws Exception {
                SignUp16KCommunication signUpService = ModuleProvider.self().getRetrofit().create(SignUp16KCommunication.class);
                RequestBody phone = RequestBody.create(MediaType.parse("multipart/form-data"), phoneString);

                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file1", file1.getName(), requestFile1);

                RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
                MultipartBody.Part reqFile2 = MultipartBody.Part.createFormData("file2", file2.getName(), requestFile2);

                RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
                MultipartBody.Part reqFile3 = MultipartBody.Part.createFormData("file3", file3.getName(), requestFile3);

                Call<SignUp16KResponse> call = signUpService.enroll(phone, reqFile1, reqFile2, reqFile3);
                // Call<SignUp16KResponse> call = signUpService.enroll(phone, reqFile1, reqFile2, reqFile3);
                Response<SignUp16KResponse> response = call.execute();
                SignUp16KResultResponse responseResult = new SignUp16KResultResponse();
                //      System.out.println("res in: " + response.code() + " " + response.body().toString());
                switch (response.code()) {
                    case 200:
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus_code(response.body().getStatus_code());
                        responseResult.setCode(response.body().getCode());
                        responseResult.setError(response.body().getError());
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

        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new SignUp16KResultResponse();
            responseResult.setMsg("System Error");
        }
        //    System.out.println("Result: " + responseResult);
        return responseResult;
    }

}

