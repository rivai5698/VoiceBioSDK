package com.example.voicebiosdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.voicebiolibs.module.CheckAuth;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_PERMISSION_CODE = 1000;
    Button btnSignUp,btnRegister,btnVerify;
    CheckAuth checkAuth = new CheckAuth();
    String accessToken,phoneStr;
    String checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAuth.setUsername("sdk");
        checkAuth.setPassword("123456aA@");
        checked = getIntent().getStringExtra("checked");
        System.out.println("Check: "+checked);
        System.out.println("Access-token: " + checkAuth.checkAuthResponse());
        accessToken = checkAuth.checkAuthResponse().getToken();
        phoneStr = getIntent().getStringExtra("phoneStr");
        if (!checkPermissionFromDevice())
            requestPermission();
        initView();
    }

    private void initView() {
        btnRegister = findViewById(R.id.btn_register);
        btnSignUp = findViewById(R.id.btn_signup);
        btnVerify = findViewById(R.id.btn_verify);
        
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToVerify();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnVerify.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToSignUp();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnSignUp.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegister();
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btnRegister.setEnabled(true);
                            }
                        });
                    }
                }, 500);
            }
        });
        
        
        
    }

    private void sendUserToRegister() {
        Intent intent = new Intent(MainActivity.this,Register2.class);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("phoneStr",phoneStr);
        startActivity(intent);
        finish();
    }

    private void sendUserToSignUp() {
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("checked",checked);
        startActivity(intent);
        finish();
    }

    private void sendUserToVerify() {
        Intent intent = new Intent(MainActivity.this,VerifyActivity.class);
        intent.putExtra("access_token",accessToken);
        intent.putExtra("phoneStr",phoneStr);
        intent.putExtra("checked",checked);

        startActivity(intent);
        finish();
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Cấp quyền thành công", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Cấp quyền thất bại", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

}