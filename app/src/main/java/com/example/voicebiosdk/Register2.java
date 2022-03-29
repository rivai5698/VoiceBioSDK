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
    EditText etPhone,etName,etMail;
    String phoneStr,nameStr,mailStr;
    Boolean checkProgress=false;
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
        etMail = findViewById(R.id.et_mail);
        btnBack = findViewById(R.id.im_back);
        //   btnBack2 = findViewById(R.id.btnBack2);
        btnCtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // progressBar.setVisibility(View.VISIBLE);

                phoneStr = etPhone.getText().toString();
                nameStr = etName.getText().toString();
                mailStr = etMail.getText().toString();
                if (phoneStr.length() == 10 && nameStr.length() > 0 && mailStr.length() > 0) {
                    createUser.setUserCode(phoneStr);
                    createUser.setNameStr(nameStr);
                    createUser.setGenderStr(mailStr);
                    CreateUserResult myResult = createUser.createUserResult();
                    System.out.println("result: " + myResult);
                    if (myResult != null) {
                        if(myResult.getStatus_code()!=null){
                            switch (myResult.getStatus_code()) {
                                case 0:
                                    checkProgress = true;
                                    checked = "true";
                                    sendUserToSignUp();
                                    break;
                                case 400:
                                    showAlertTextOnlyError("User_code đã được sử dụng");
                                    checked = "true2";
                                    checkProgress = true;
                                    sendUserToSignUp();
                                    break;
                                case 2:
                                    showAlertTextOnlyError("Số điện thoại chưa đúng định dạng");
                                    checkProgress = true;
                                    break;
                                case 3:
                                    showAlertTextOnlyError("Số điện thoại đã tồn tại");

                                    checked = "true2";
                                    sendUserToSignUp();
                                    checkProgress = true;
                                    break;
                                case 4:
                                    showAlertTextOnlyError("Email đã tồn tại");
                                    checkProgress = true;
                                    break;
                                default:
                                    showAlertTextOnlyError("Lỗi hệ thống");
                                    checkProgress = true;
                            }
                        }else {
                            showAlertTextOnly("Lỗi hệ thống");
                        }

                    }
                } else if (phoneStr.length() < 10&&phoneStr.length()>0) {
                    showAlertTextOnlyError("Vui lòng nhập đúng định dạng số điện thoại");
                    checkProgress = true;
                }
//                else if(phoneStr.length() ==0) {
//                    showAlertTextOnly("Vui lòng nhập đúng định dạng số điện thoại");
//                    checkProgress = true;
//                }
                else
                 {
                    showAlertTextOnlyError("Vui lòng điền đầy đủ thông tin");
                    checkProgress = true;
                }

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
        mainIntent.putExtra("access_token",accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        mainIntent.putExtra("checked",checked);
        startActivity(mainIntent);
        finish();
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(Register2.this,SignUpActivity.class);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("checked",checked);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.

        Intent mainIntent = new Intent(Register2.this, MainActivity.class);
        mainIntent.putExtra("access_token",accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
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