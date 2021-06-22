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
import android.widget.TextView;
import android.widget.Toast;

import com.example.voicebiolibs.init.RecordFunction;
import com.example.voicebiolibs.module.Verify16KResultResponse;
import com.example.voicebiolibs.module.Verify16KVoiceId;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class VerifyActivity extends AppCompatActivity {
    String accessToken,phoneStr,pathSavePCM,pathSaveWAV;
    RecordFunction recordFunction;
    Button btnBack, btnRecord, btnStop,btnCtn;
    Boolean clicked1 = false;
    TextView tvGuide,tvResultPercent,textView7;
    EditText editText7;
    File fileWAV;
    Verify16KVoiceId verify16KVoiceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        accessToken = getIntent().getStringExtra("access_token");
        recordFunction = new RecordFunction();
        initView();
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_VVback);
        btnCtn = findViewById(R.id.btn_ctnVV);
        btnRecord = findViewById(R.id.btn_recordVV);
        btnStop = findViewById(R.id.btn_stopRecordVV);
        tvGuide = findViewById(R.id.tv_guideVerification);
        textView7 = findViewById(R.id.tv7);
        editText7 = findViewById(R.id.et7);

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

                phoneStr = editText7.getText().toString();
                if (phoneStr.length()==10){
                    textView7.setVisibility(View.INVISIBLE);
                    editText7.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(VerifyActivity.this,"Vui lòng nhập lại số điện thoại",Toast.LENGTH_SHORT).show();
                }
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


                btnCtn.setEnabled(true);
                btnBack.setEnabled(true);
//                    btnStop.setEnabled(true);
//                    btnPlay.setEnabled(true);







//                System.out.println("file path: -----------" + fileWAV.getAbsolutePath());


                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

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
                btnBack.setEnabled(false);
                verify16KVoiceId = new Verify16KVoiceId();
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


                phoneStr = editText7.getText().toString();
                if (phoneStr.length()==10){
                    if(fileWAV!=null){
                        if(fileWAV.isFile()){
                            System.out.println("isFile");
                        }else {
                            System.out.println("notFile");
                        }
                    }else {
                        System.out.println("nullFile");
                    }
                    verify16KVoiceId.setMyFileRecorder(fileWAV);
                    verify16KVoiceId.setPhoneStr(phoneStr);
                    Verify16KResultResponse rR = verify16KVoiceId.verify16KResultResponse();
                    System.out.println("result: "+rR);
//                Double score = verifyVoiceId.verify16KResultResponse().getScore();
                    if(rR!=null){

                        String rS = rR.getError();
                        Double rP = rR.getScore();
                        String rM = rR.getMsg();
                        Float rPFloat = Float.valueOf(String.valueOf(rP));

                        if (rP==0.0){
                            tvResultPercent.setTextColor(Color.RED);
                            tvResultPercent.setText(rM+" "+rS);
                        }else if(rPFloat>=90.0){
                            tvResultPercent.setTextColor(Color.GREEN);
                            tvResultPercent.setText("Độ chính xác: "+"\n"+ rP+"%");
                        }else {
                            tvResultPercent.setTextColor(Color.RED);
                            tvResultPercent.setText("Độ chính xác: "+"\n"+ rP+"%");
                        }

                        btnCtn.setVisibility(View.INVISIBLE);
                        tvResultPercent.setVisibility(View.VISIBLE);
                        btnBack.setEnabled(true);
                        // filePCM.delete();
                        //   btnStop.setVisibility(View.INVISIBLE);
                        btnRecord.setVisibility(View.INVISIBLE);
                        System.out.println("rS: " + rS + " " +rP);
                        Toast.makeText(VerifyActivity.this, rM, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(VerifyActivity.this,"Thất bại",Toast.LENGTH_SHORT).show();
                        // filePCM.delete();
                        btnBack.setEnabled(true);
                        //    btnStop.setVisibility(View.VISIBLE);
                        btnRecord.setVisibility(View.VISIBLE);
                        //    btnPlay.setVisibility(View.VISIBLE);
                    }




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
                    }, 500);
                }else {
                    Toast.makeText(VerifyActivity.this,"Vui lòng nhập lại số điện thoại",Toast.LENGTH_SHORT).show();
                    btnBack.setEnabled(true);
                }

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
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent mainIntent = new Intent(VerifyActivity.this, MainActivity.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        startActivity(mainIntent);
        finish();
    }
}