package com.example.voicebiolibs.module;



import com.example.voicebiolibs.connect.AuthCommunication;
import com.example.voicebiolibs.connect.CheckAuthResponse;
import com.example.voicebiolibs.connect.NetworkProvider;
import com.example.voicebiolibs.connect.TokenAccessGetting;

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
public class CheckAuth {

    TokenAccessGetting tokenAccessGetting = TokenAccessGetting.getInstance();
    String usernameStr,passwordStr;

    public String getUsername() {
        return usernameStr;
    }

    public void setUsername(String usernameStr) {
        this.usernameStr = usernameStr;
    }

    public String getPassword() {
        return passwordStr;
    }

    public void setPassword(String passwordStr) {
        this.passwordStr = passwordStr;
    }

    public CheckAuthResponseResult checkAuthResponse() {
        ExecutorService threadPoll = Executors.newFixedThreadPool(1);
        Future<CheckAuthResponseResult> future = threadPoll.submit(new Callable<CheckAuthResponseResult>() {
            @Override
            public CheckAuthResponseResult call() throws Exception {
                if (usernameStr != null&&passwordStr!=null) {
                    AuthCommunication authCommunication = NetworkProvider.self().getRetrofit().create(AuthCommunication.class);
                    RequestBody username = RequestBody.create(MediaType.parse("multipart/form-data"), usernameStr);
                    RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), passwordStr);

                    Call<CheckAuthResponse> call = authCommunication.checkAuth(username,password);

                    Response<CheckAuthResponse> response = call.execute();
                    CheckAuthResponseResult responseResult = new CheckAuthResponseResult();
                    switch (response.code()) {
                        case 200:
                            responseResult.setResult("success");
                            responseResult.setMsg(response.body().getMsg());
                            responseResult.setExpire_time(response.body().getExpire_time());
                            responseResult.setStatus(response.body().getStatus());
                            responseResult.setToken(response.body().getToken());

                             tokenAccessGetting.setToken(response.body().getToken());
                            break;
                        case 401:
                            responseResult.setResult("Incorrect password or username");
                            break;
                        default:
                            responseResult.setResult("failed");
                            break;
                    }
                    return responseResult;
                }else{
                    CheckAuthResponseResult responseResult = new CheckAuthResponseResult();
                    responseResult.setResult("error");
                    return responseResult;
                }
            }
        });
        CheckAuthResponseResult responseResult = null;
        try {
            responseResult = future.get();
        } catch (Exception e) {
            e.printStackTrace();
            responseResult = new CheckAuthResponseResult();
        }
        //System.out.println("Result: " + responseResult);
        return responseResult;
    }

}

