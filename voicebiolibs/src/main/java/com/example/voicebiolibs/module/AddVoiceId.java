package com.example.voicebiolibs.module;



import com.example.voicebiolibs.connect.ModuleProvider;
import com.example.voicebiolibs.connect.AddVoiceFileCommunication;
import com.example.voicebiolibs.connect.AddVoiceFileResponse;

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
public class AddVoiceId {
    AddVoiceIdResultResponse responseResult = new AddVoiceIdResultResponse();
    String userCode;
    File fileName;
    String filterOption;


    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public File getFileName() {
        return fileName;
    }
    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    public String getFilter() {
        return filterOption;
    }

    public void setFilter(String filter) {
        this.filterOption = filter;
    }

    public AddVoiceIdResultResponse addFileVoiceId() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<AddVoiceIdResultResponse> future = threadPoll.submit(new Callable<AddVoiceIdResultResponse>() {
            @Override
            public AddVoiceIdResultResponse call() throws Exception {
                AddVoiceFileCommunication signUpService = ModuleProvider.self().getRetrofit().create(AddVoiceFileCommunication.class);
                RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), userCode);

                RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), fileName);
                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file", fileName.getName(), requestFile1);

//                RequestBody requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
//                MultipartBody.Part reqFile2 = MultipartBody.Part.createFormData("file2", file2.getName(), requestFile2);
//
//                RequestBody requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), file3);
//                MultipartBody.Part reqFile3 = MultipartBody.Part.createFormData("file3", file3.getName(), requestFile3);

                RequestBody filter = RequestBody.create(MediaType.parse("multipart/form-data"),filterOption);


                Call<AddVoiceFileResponse> call = signUpService.enroll(user_code, reqFile1, filter);
                Response<AddVoiceFileResponse> response = call.execute();
                 //          System.out.println("res in: " + response.code() + " " + response.body().toString());
                switch (response.code()) {
                    case 200:
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus(response.body().getStatus());
                        responseResult.setCode(response.body().getCode());
                        responseResult.setFile_code(response.body().getFile_code());
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
        AddVoiceIdResultResponse responseResult = null;
        try {
            responseResult = future.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new AddVoiceIdResultResponse();
            responseResult.setMsg("System Error");
        }
        //     System.out.println("Result: " + responseResult);
        return responseResult;
    }

}

