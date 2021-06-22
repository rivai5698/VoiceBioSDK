package com.example.voicebiosdk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.voicebiolibs.module.CreateUser;
import com.example.voicebiolibs.module.CreateUserResult;

import java.util.Timer;
import java.util.TimerTask;


public class RegisterActivity extends AppCompatActivity {
    ImageButton btnCtn;
    Button btnBack,btnBack2;
    EditText etPhone,etName,etMail;
    ProgressBar progressBar;
    String phoneStr,nameStr,mailStr;
    Boolean checkProgress=false;
    String accessToken;
    ConstraintLayout constraintLayout1,constraintLayout2;
    CreateUser createUser = new CreateUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accessToken = getIntent().getStringExtra("access_token");

        initView();
    }
    private void initView() {
        btnCtn = findViewById(R.id.ctn1);
        etPhone = findViewById(R.id.phoneText);
        etName = findViewById(R.id.nameText);
        etMail = findViewById(R.id.mailText);
        progressBar =findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        btnBack = findViewById(R.id.btnBack);
        btnBack2 = findViewById(R.id.btnBack2);
        constraintLayout1 = findViewById(R.id.cl1);
        constraintLayout2 = findViewById(R.id.cl2);
        constraintLayout2.setVisibility(View.INVISIBLE);
        btnCtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                phoneStr = etPhone.getText().toString();
                nameStr = etName.getText().toString();
                mailStr = etMail.getText().toString();
                if (phoneStr.length()==10&&nameStr.length()>0&&mailStr.length()>0){
                    createUser.setPhoneString(phoneStr);
                    createUser.setNameString(nameStr);
                    createUser.setEmailString(mailStr);
                    CreateUserResult myResult = createUser.createUserResult();
                    System.out.println("result: " + myResult);
                    if (myResult!=null){
                        switch (myResult.getStatus_code()){
                            case 0:
                                Toast.makeText(RegisterActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                checkProgress = true;
                                break;
                            case 1:
                                Toast.makeText(RegisterActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                                constraintLayout1.setVisibility(View.INVISIBLE);
                                constraintLayout2.setVisibility(View.VISIBLE);
                                checkProgress = true;
                                break;
                            case 2:
                                Toast.makeText(RegisterActivity.this, "Số điện thoại chưa đúng định dạng", Toast.LENGTH_SHORT).show();
                                checkProgress = true;
                                break;
                            case 3:
                                Toast.makeText(RegisterActivity.this, "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
                                checkProgress = true;
                                break;
                            case 4:
                                Toast.makeText(RegisterActivity.this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                                checkProgress = true;
                                break;
                            default:
                                Toast.makeText(RegisterActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                                checkProgress = true;
                        }
                    }
                }else if(phoneStr.length()<10){
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đúng định dạng sđt", Toast.LENGTH_SHORT).show();
                    checkProgress = true;
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền thông tin", Toast.LENGTH_SHORT).show();
                    checkProgress = true;
                }

                if (checkProgress){
                    progressBar.setVisibility(View.INVISIBLE);
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
        btnBack2.setOnClickListener(new View.OnClickListener() {
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
                                btnBack2.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

    }

    private void sendUsertoMain() {
        constraintLayout1.setVisibility(View.VISIBLE);
        constraintLayout2.setVisibility(View.INVISIBLE);
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.putExtra("access_token",accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        startActivity(mainIntent);
        finish();
    }


    @Override
    public void onBackPressed() {
        // your code.
        constraintLayout1.setVisibility(View.VISIBLE);
        constraintLayout2.setVisibility(View.INVISIBLE);
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.putExtra("access_token",accessToken);
        mainIntent.putExtra("phoneStr",phoneStr);
        startActivity(mainIntent);
        finish();
    }
}