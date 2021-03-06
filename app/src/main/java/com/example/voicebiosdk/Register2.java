package com.example.voicebiosdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.voicebiolibs.module.CreateUser;
import com.example.voicebiolibs.module.CreateUserResult;
import com.tapadoo.alerter.Alerter;

import java.util.Timer;
import java.util.TimerTask;

public class Register2 extends AppCompatActivity {

    Button btnCtn;
    ImageView btnBack;
    EditText etPhone, etName, etMail;
    String phoneStr, nameStr;
    Boolean checkProgress = false;
    String accessToken;
    CreateUser createUser = new CreateUser();
    String checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        accessToken = getIntent().getStringExtra("access_token");

        initView();
    }

    private void initView() {
        btnCtn = findViewById(R.id.btn_register3);
        etPhone = findViewById(R.id.et_phone);
        etName = findViewById(R.id.et_name);
        // etMail = findViewById(R.id.et_mail);
        btnBack = findViewById(R.id.im_back);
        //   btnBack2 = findViewById(R.id.btnBack2);
        btnCtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // progressBar.setVisibility(View.VISIBLE);

                phoneStr = etPhone.getText().toString();
                nameStr = etName.getText().toString();
//                mailStr = etMail.getText().toString();
                if (phoneStr.length() > 0 && nameStr.length() > 0) {
                    createUser.setUserCode(phoneStr);
                    createUser.setNameStr(nameStr);
                    CreateUserResult myResult = createUser.createUserResult();
                    System.out.println("result: " + myResult);
                    if (myResult != null) {
                        if (myResult.getStatus_code() != null) {
                            switch (myResult.getStatus_code()) {
                                case 0:
                                    checkProgress = true;
                                    checked = "true";
                                    showAlertTextOnly("????ng k?? th??nh c??ng");
                                    checked = "true2";
                                    checkProgress = true;
                                    Timer buttonTimer = new Timer();
                                    buttonTimer.schedule(new TimerTask() {

                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    sendUserToSignUp();
                                                }
                                            });
                                        }
                                    }, 1000);
                                    break;
                                case 400:
                                    showAlertTextOnly("M?? ng?????i d??ng ho???c s??? ??i???n tho???i ???? ???????c s??? d???ng");
                                    checked = "true2";
                                    checkProgress = true;
                                    Timer buttonTimer2 = new Timer();
                                    buttonTimer2.schedule(new TimerTask() {

                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {

                                                @Override
                                                public void run() {
                                                    sendUserToSignUp();
                                                }
                                            });
                                        }
                                    }, 1000);
                                    break;
//                                case 2:
//                                    showAlertTextOnlyError("S??? ??i???n tho???i ch??a ????ng ?????nh d???ng");
//                                    checkProgress = true;
//                                    break;
//                                case 3:
//                                    showAlertTextOnlyError("S??? ??i???n tho???i ???? t???n t???i");
//
//                                    checked = "true2";
//                                    sendUserToSignUp();
//                                    checkProgress = true;
//                                    break;
//                                case 4:
//                                    showAlertTextOnlyError("Email ???? t???n t???i");
//                                    checkProgress = true;
//                                    break;
                                default:
                                    showAlertTextOnlyError("L???i h??? th???ng");
                                    checkProgress = true;
                            }
                        } else {
                            showAlertTextOnly("L???i h??? th???ng");
                        }

                    }
                }
//                else if (phoneStr.length() <= 0) {
//                    showAlertTextOnlyError("Vui l??ng nh???p ????ng ?????nh d???ng s??? ??i???n tho???i");
//                    checkProgress = true;
//                }
//                else if(phoneStr.length() ==0) {
//                    showAlertTextOnly("Vui l??ng nh???p ????ng ?????nh d???ng s??? ??i???n tho???i");
//                    checkProgress = true;
//                }
//                else {
//                    showAlertTextOnlyError("Vui l??ng ??i???n ?????y ????? th??ng tin");
//                    checkProgress = true;
//                }

                if (checkProgress) {

                    checkProgress = false;
                }
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

        Intent mainIntent = new Intent(Register2.this, MainActivity.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr", phoneStr);
        mainIntent.putExtra("checked", checked);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(Register2.this, SignUpActivity.class);
        intent.putExtra("access_token", accessToken);
        intent.putExtra("phoneStr", phoneStr);
        intent.putExtra("checked", checked);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.

        Intent mainIntent = new Intent(Register2.this, MainActivity.class);
        mainIntent.putExtra("access_token", accessToken);
        mainIntent.putExtra("phoneStr", phoneStr);
        startActivity(mainIntent);
        finish();
    }

    private void showAlertTextOnly(String msg) {
        Alerter.create(this).setBackgroundColorRes(R.color.colorAccent)
                .setText(msg)
                .show();
    }

    private void showAlertTextOnlyError(String msg) {
        Alerter.create(this).setBackgroundColorRes(R.color.redColor)
                .setText(msg)
                .show();
    }

}