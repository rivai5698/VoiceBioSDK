package com.example.voicebiolibs.module;



import com.example.voicebiolibs.connect.CreateUserCommunication;
import com.example.voicebiolibs.connect.CreateUserResponse;
import com.example.voicebiolibs.connect.ModuleProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 *
 * @author thangth
 */
public class CreateUser {

    String userCode, nameStr, genderStr;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public String getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(String genderStr) {
        this.genderStr = genderStr;
    }

    public CreateUserResult createUserResult() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<CreateUserResult> future = threadPoll.submit(new Callable<CreateUserResult>() {
            @Override
            public CreateUserResult call() throws Exception {
                CreateUserCommunication createUserCommunication = ModuleProvider.self().getRetrofit().create(CreateUserCommunication.class);

                RequestBody user_code = RequestBody.create(MediaType.parse("multipart/form-data"), userCode);
                RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), nameStr);
                RequestBody gender = RequestBody.create(MediaType.parse("multipart/form-data"), genderStr);

                Call<CreateUserResponse> call = createUserCommunication.createUser(user_code, name, gender);
                Response<CreateUserResponse> response = call.execute();
                CreateUserResult responseResult = new CreateUserResult();
                switch (response.code()) {
                    case 200:
                        //System.out.println("res in: " + response.body());
                        responseResult.setMsg(response.body().getMsg());
                        responseResult.setStatus_code(response.body().getStatus_code());
                        break;
                    case 401:
                        responseResult.setMsg("Access token is wrong or missing");
                        break;
                    case 422:
                        responseResult.setMsg("Missing params");
                        break;
                    default:
                        responseResult.setMsg("System Error");
                        break;
                }

                return responseResult;

            }
        });
        CreateUserResult responseResult = null;
        try {
            responseResult = future.get();
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new CreateUserResult();
        }
        // System.out.println("Result: " + responseResult);
        return responseResult;
    }
}
