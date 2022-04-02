package com.example.voicebiosdk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//
//import com.example.voicebiolibs.init.RecordFunction;
//import com.example.voicebiolibs.module.Verify16KResultResponse;
//import com.example.voicebiolibs.module.Verify16KVoiceId;
import com.example.voicebiolibs.init.RecordFunction;
import com.example.voicebiolibs.module.Verify16KResultResponse;
import com.example.voicebiolibs.module.Verify16KVoiceId;
import com.example.voicebiolibs.module.VerifyVoiceId;
import com.example.voicebiolibs.module.VerifyVoiceResultResponse;
import com.tapadoo.alerter.Alerter;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class VerifyActivity extends AppCompatActivity {
    String accessToken,phoneStr,pathSavePCM,pathSaveWAV,checked;
    RecordFunction recordFunction;
    Button  btnRecord, btnStop,btnCtn;
    ImageView btnBack;
    Boolean clicked1 = false;
    TextView tvGuide,tvResultPercent,textView7;
    TextView tv7;
    EditText etPhone;
    File fileWAV;
    VerifyVoiceId verify16KVoiceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        accessToken = getIntent().getStringExtra("access_token");
        phoneStr = getIntent().getStringExtra("phoneStr");
        checked = getIntent().getStringExtra("checked");
        etPhone = findViewById(R.id.et_phone2);
        textView7 = findViewById(R.id.et7);
        textView7.setText(phoneStr);

        if(checked==null){
           // showAlertTextOnlyError("Hãy đăng ký người dùng trước đó");
            checked="true2";
            etPhone.setVisibility(View.VISIBLE);
        }else if (checked.equalsIgnoreCase("true")){
            etPhone.setVisibility(View.VISIBLE);
            etPhone.setText(phoneStr);
           // showAlertTextOnly("Đăng ký giọng nói thành công");
            checked = "true1";
        }else {
            checked = "true1";
        }
        recordFunction = new RecordFunction();
        initView();
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_VVback);
        btnCtn = findViewById(R.id.btn_ctnVV);
        btnRecord = findViewById(R.id.btn_recordVV);
        btnStop = findViewById(R.id.btn_stopRecordVV);
        tvGuide = findViewById(R.id.tv_guideVerification);

//        editText7 = findViewById(R.id.et7);

        btnCtn.setEnabled(false);
        tvResultPercent = findViewById(R.id.tv_resultPercent);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        pathSavePCM = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS) +"/"+ UUID.randomUUID().toString() + "_audio_record.pcm";
                        pathSaveWAV = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DOWNLOADS) +"/"+UUID.randomUUID().toString() + "_audio_record.wav";

                        recordFunction.startRecording2(pathSavePCM,pathSaveWAV);
                    } else {
                        pathSavePCM = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.pcm";
                        pathSaveWAV = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + UUID.randomUUID().toString() + "_audio_record.wav";
                        recordFunction.startRecording(pathSavePCM,pathSaveWAV);
                    }


                    btnRecord.setVisibility(View.INVISIBLE);
                    btnStop.setVisibility(View.VISIBLE);
                    btnStop.setEnabled(false);
                    btnBack.setEnabled(false);
                    Toast.makeText(VerifyActivity.this,"Đang ghi âm...",Toast.LENGTH_SHORT).show();

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnRecord.setEnabled(true);
                            }
                        });
                    }
                }, 500);

                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        btnStop.setEnabled(true);
                    }

                }.start();

            }

        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clicked1 = true;
                try {
                    recordFunction.stopRecording(pathSavePCM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //mediaRecorder.stop();
                btnStop.setVisibility(View.INVISIBLE);
                btnCtn.setVisibility(View.VISIBLE);
                tvGuide.setVisibility(View.INVISIBLE);

                fileWAV = new File(pathSaveWAV);

//                phoneStr = editText7.getText().toString();
//                if (phoneStr.length()==10){
//                    textView7.setVisibility(View.INVISIBLE);
//                    editText7.setVisibility(View.INVISIBLE);
//                }else {
//                    btnCtn.setVisibility(View.VISIBLE);
//                    Toast.makeText(VerifyActivity.this,"Vui lòng nhập lại số điện thoại",Toast.LENGTH_SHORT).show();
//                }




//                checkAudio.setMyRecordFile(fileWAV);
//                AudioCheckResultResponse aR = checkAudio.solveAudioFile();
//
//                System.out.println("aR1: "+aR);
//                if(aR!=null) {
////                    int code = aR.getCode();
////                    int status = aR.getStatus();
////                    String msg = aR.getMsg();
//                    if (aR == null) {
//                        Toast.makeText(VerifyVoiceActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (aR.getStatus() == 400) {
//                            Toast.makeText(VerifyVoiceActivity.this, "Gửi file thất bại", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            if (aR.getCode() == 0) {
//                                clicked1 = true;
//                                Toast.makeText(VerifyVoiceActivity.this, "File đạt chuẩn", Toast.LENGTH_SHORT).show();
//                            } else if (aR.getCode() == 1) {
//                                Toast.makeText(VerifyVoiceActivity.this, "Nói không rõ từ", Toast.LENGTH_SHORT).show();
//                            } else if (aR.getCode() == 2) {
//                                Toast.makeText(VerifyVoiceActivity.this, "Nói quá nhỏ", Toast.LENGTH_SHORT).show();
//                            } else if (aR.getCode() == 3) {
//                                Toast.makeText(VerifyVoiceActivity.this, "Mỗi trường quá ồn", Toast.LENGTH_SHORT).show();
//                            } else if (aR.getCode() == 4) {
//                                Toast.makeText(VerifyVoiceActivity.this, "Nói chưa đủ 3s", Toast.LENGTH_SHORT).show();
//                            } else if (aR.getCode() == 5) {
//                                Toast.makeText(VerifyVoiceActivity.this, "Nói chưa đủ số từ tối thiểu", Toast.LENGTH_SHORT).show();
//                            } else if (aR.getCode() == 6) {
//                                Toast.makeText(VerifyVoiceActivity.this, "Audio quá dài", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(VerifyVoiceActivity.this, "Lỗi khác", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                } else {
//                System.out.println("aRVV: "+aR);
//            }




                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnCtn.setEnabled(true);
                            }
                        });
                    }
                }, 1000);

                btnBack.setEnabled(true);
//                    btnStop.setEnabled(true);
//                    btnPlay.setEnabled(true);







//                System.out.println("file path: -----------" + fileWAV.getAbsolutePath());


                Timer buttonTimer2 = new Timer();
                buttonTimer2.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnStop.setEnabled(true);
                            }
                        });
                    }
                }, 500);

            }
        });
        btnCtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("file path: -----------" + fileWAV.getAbsolutePath());
                btnBack.setEnabled(false);
                if(checked.equalsIgnoreCase("true2")){
                    phoneStr = etPhone.getText().toString();
                }else {
                    phoneStr = textView7.getText().toString();
                }
                System.out.println("phone: "+ phoneStr);
                if (phoneStr != null) {
                    verify16KVoiceId = new VerifyVoiceId();
                    if(fileWAV!=null){
                        if(fileWAV.isFile()){
                            System.out.println("isFile");
                        }else {
                            System.out.println("notFile");
                        }
                    }else {
                        System.out.println("nullFile");
                    }

                    if(phoneStr.length()>0){
                        verify16KVoiceId.setFileName(fileWAV);
                        verify16KVoiceId.setUserCode(phoneStr);
                        verify16KVoiceId.setIsFilter("true");
                        VerifyVoiceResultResponse rR = verify16KVoiceId.verifyResultResponse();
                        System.out.println("result: "+rR);
                Float score = verify16KVoiceId.verifyResultResponse().getScore();
                    if(rR!=null){

                        Integer rS = rR.getStatus();
                        Float rP = rR.getScore();
                        String rM = rR.getMsg();
                        String rC = rR.getCode();
                        Float rPFloat = Float.valueOf(String.valueOf(rP));

                        if (rP==0.0){
                            if(rS==null){
                                tvResultPercent.setTextColor(Color.RED);
                                tvResultPercent.setText(rM);
                            }else {
                                tvResultPercent.setTextColor(Color.RED);
                                switch (rC){
                                    case "0":
                                        showAlertTextOnly("Thành công");
                                        break;
                                    case "1":
                                        tvResultPercent.setText("Nói không rõ từ, yêu cầu nói rõ hơn");
                                        break;
                                    case "2":
                                        tvResultPercent.setText("Nói quá nhỏ, yêu cầu nói to hơn");
                                        break;
                                    case "3":
                                        tvResultPercent.setText("Môi trường nhiễu, yêu cầu ghi âm ở môi trường yên tĩnh hơn");
                                        break;
                                    case "4":
                                        tvResultPercent.setText("Độ dài audio không đủ 2s, yêu cầu ghi âm dài hơn");
                                        break;
                                    case "5":
                                        tvResultPercent.setText("Nói không đủ số từ tối thiểu, yêu cầu nói nhiều từ hơn");
                                        break;
                                    case "6":
                                        tvResultPercent.setText("Độ dài audio quá dài (hơn 20p), yêu cầu nói ngắn lại");
                                        break;
                                    case "7":
                                        tvResultPercent.setText("Lỗi khác");
                                        break;
                                    default:
                                        tvResultPercent.setText("Lỗi hệ thống");
                                }

                            }
                        }else if(rPFloat>=80.0){
                            tvResultPercent.setTextColor(Color.GREEN);
                            tvResultPercent.setText("Điểm xác thực: "+"\n"+ rP);
                        }else {
                            tvResultPercent.setTextColor(Color.RED);
                            tvResultPercent.setText("Điểm xác thực: "+"\n"+ rP);
                        }


                        btnCtn.setVisibility(View.INVISIBLE);
                        tvResultPercent.setVisibility(View.VISIBLE);
                        btnBack.setEnabled(true);
                        // filePCM.delete();
                        //   btnStop.setVisibility(View.INVISIBLE);
                        btnRecord.setVisibility(View.INVISIBLE);
                        System.out.println("rS: " + rS + " " +rP);
                        if(rC!=null){
                            if(rC.equalsIgnoreCase("")){
                                showAlertTextOnly(rM);
                                btnRecord.setVisibility(View.VISIBLE);
                                btnRecord.setEnabled(true);
                            }else {
                                switch (rC){
                                    case "0":
                                        showAlertTextOnly("Thành công");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "1":
                                        showAlertTextOnlyError("Nói không rõ từ, yêu cầu nói rõ hơn");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "2":
                                        showAlertTextOnlyError("Nói quá nhỏ, yêu cầu nói to hơn");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "3":
                                        showAlertTextOnlyError("Môi trường nhiễu, yêu cầu ghi âm ở môi trường yên tĩnh hơn");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "4":
                                        showAlertTextOnlyError("Độ dài audio không đủ 2s, yêu cầu ghi âm dài hơn");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "5":
                                        showAlertTextOnlyError("Nói không đủ số từ tối thiểu, yêu cầu nói nhiều từ hơn");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "6":
                                        showAlertTextOnlyError("Độ dài audio quá dài (hơn 20p), yêu cầu nói ngắn lại");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    case "7":
                                        showAlertTextOnlyError("Lỗi khác");
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        break;
                                    default:
                                        btnRecord.setVisibility(View.VISIBLE);
                                        btnRecord.setEnabled(true);
                                        showAlertTextOnlyError("Lỗi hệ thống");
                                }
                            }
                        }else {
                            showAlertTextOnlyError("Lỗi hệ thống");
                            btnRecord.setVisibility(View.VISIBLE);
                            btnRecord.setEnabled(true);
                        }

                    }else {
                        showAlertTextOnlyError("Lỗi hệ thống");
                        btnBack.setEnabled(true);
                        //    btnStop.setVisibility(View.VISIBLE);
                        btnRecord.setVisibility(View.VISIBLE);
                        //    btnPlay.setVisibility(View.VISIBLE);
                    }
                    }else {
                        showAlertTextOnlyError("Vui lòng nhập mã người dùng hoặc số điện thoại");
                    }

//                Double score = verifyVoiceId.verify16KResultResponse().getScore();
//                    if(rR!=null){
//
//                        Integer rS = rR.getStatus();
//                        Float rP = rR.getScore();
//                        String rM = rR.getMsg();
//                        Float rPFloat = Float.valueOf(String.valueOf(rP));
//
//                        if (rP==0.0){
//                            if(rS==null){
//                                tvResultPercent.setTextColor(Color.RED);
//                                tvResultPercent.setText(rM);
//                            }else {
//                                tvResultPercent.setTextColor(Color.RED);
//                                tvResultPercent.setText(rS);
//                            }
//                        }else if(rPFloat>=90.0){
//                            tvResultPercent.setTextColor(Color.GREEN);
//                            tvResultPercent.setText("Điểm xác thực: "+"\n"+ rP);
//                        }else {
//                            tvResultPercent.setTextColor(Color.RED);
//                            tvResultPercent.setText("Điểm xác thực: "+"\n"+ rP);
//                        }
//
//                        btnCtn.setVisibility(View.INVISIBLE);
//                        tvResultPercent.setVisibility(View.VISIBLE);
//                        btnBack.setEnabled(true);
//                        // filePCM.delete();
//                        //   btnStop.setVisibility(View.INVISIBLE);
//                        btnRecord.setVisibility(View.INVISIBLE);
//                        System.out.println("rS: " + rS + " " +rP);
//                        if(rM.equalsIgnoreCase("success")){
//                            showAlertTextOnly(rM);
//                        }else {
//                            showAlertTextOnlyError(rM);
//                        }
//                    }else {
//                        showAlertTextOnlyError("Lỗi hệ thống");
//                        btnBack.setEnabled(true);
//                        //    btnStop.setVisibility(View.VISIBLE);
//                        btnRecord.setVisibility(View.VISIBLE);
//                        //    btnPlay.setVisibility(View.VISIBLE);
//                    }
                }


//                btnStop.setEnabled(false);
//                btnPlay.setEnabled(false);
//                VerifyCommunication verifyService = NetworkProvider.self().getRetrofit().create(VerifyCommunication.class);
//                RequestBody verify_type = RequestBody.create(MediaType.parse("multipart/form-data"),verify_typeStr);
//                RequestBody speaker = RequestBody.create(MediaType.parse("multipart/form-data"),speakerStr);
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileWAV);
//                MultipartBody.Part reqFile1 = MultipartBody.Part.createFormData("file", fileWAV.getName(), requestFile);
//                verifyService.verify(reqFile1,speaker,verify_type)
//                        .enqueue(new Callback<VerifyResponse>() {
//                            @Override
//                            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
//
//                                if (response.body().getStatus()==1){
//                                    Double score = Math.round(response.body().getScore() * 100.0) / 100.0;
//                                    tvResultPercent.setText("Độ chính xác: "+ score.toString()+"%");
//                                    btnCtn.setVisibility(View.INVISIBLE);
//
//                                    if(response.body().getResult().equalsIgnoreCase("true")){
//                                        tvResultText.setText("Kết quả chính xác.");
//                                    }
//                                    else {
//                                        tvResultText.setText("Kết quả chưa chính xác.");
//                                    }
//
//                                    tvResultPercent.setVisibility(View.VISIBLE);
//                                    // tvResultText.setVisibility(View.VISIBLE);
//                                }
//                                else {
//                                    tvResultText.setText("Thời gian thu âm quá ngắn.");
//                                    tvResultText.setVisibility(View.VISIBLE);
//                                    Toast.makeText(VerifyVoiceActivity.this,"Thất bại",Toast.LENGTH_SHORT).show();
//                                }
//                                System.out.println(response.body());
//                                btnBack.setEnabled(true);
//
//                                fileWAV.delete();
//                            }
//
//
//                            @Override
//                            public void onFailure(Call<VerifyResponse> call, Throwable t) {
//                                Toast.makeText(VerifyVoiceActivity.this,"Thất bại",Toast.LENGTH_SHORT).show();
//                                System.out.println(t);
//                                btnBack.setEnabled(true);
//                                fileWAV.delete();
//                                filePCM.delete();
//                            }
//
//                        });


         //       phoneStr = editText7.getText().toString();
//                if (phoneStr.length()==10){





//                tvResultText.setText(verifyVoiceId.getResultTextResponse());


                    Timer buttonTimer = new Timer();
                    buttonTimer.schedule(new TimerTask() {

                        @Override
                        public void run() {

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    btnCtn.setEnabled(true);
                                }
                            });
                        }
                    }, 2000);
//                }else {
//                    Toast.makeText(VerifyActivity.this,"Vui lòng nhập lại số điện thoại",Toast.LENGTH_SHORT).show();
//                    btnBack.setEnabled(true);
//                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUsertoMain();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnBack.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

    }

    private void sendUsertoMain() {
        Intent mainIntent = new Intent(VerifyActivity.this, MainActivity.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        mainIntent.putExtra("checked",checked);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent mainIntent = new Intent(VerifyActivity.this, MainActivity.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        mainIntent.putExtra("checked",checked);
        startActivity(mainIntent);
        finish();
    }
    private void showAlertTextOnly(String msg){
        Alerter.create(this).setBackgroundColorRes(R.color.colorAccent)
                .setText(msg)
                .show();
    }
    private void showAlertTextOnlyError(String msg){
        Alerter.create(this).setBackgroundColorRes(R.color.redColor)
                .setText(msg)
                .show();
    }
}