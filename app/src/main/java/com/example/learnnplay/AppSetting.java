package com.example.learnnplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;

public class AppSetting extends AppCompatActivity {
    private Button backButton, progressButton,logout;
    private AirplaneModeReceiver receiver;

    private static final int ACTION_MANAGE_WRITE_SETTINGS_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
         logout=findViewById(R.id.logoutbtn);
        backButton = findViewById(R.id.backbutton);
        progressButton = findViewById(R.id.buttonProgress);
         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(AppSetting.this, Login.class));
             }
         });
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppSetting.this, QuizProgressActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AppSetting.this, HomeScreen.class));
                finish();
            }
        });
        Button broadcastButton = findViewById(R.id.broadcastButton);
        broadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAirplaneModeOn()) {
                    Toast.makeText(AppSetting.this, "Airplane mode is ON", Toast.LENGTH_SHORT).show();
                } else {
                    broadcastIntent();
                }
            }
        });

        receiver = new AirplaneModeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, filter);
    }

    private void broadcastIntent() {
        sendBroadcast(new Intent("com.example.broadcastreceivers.MY_CUSTOM_ACTION"));
    }

    private boolean isAirplaneModeOn() {
        return Settings.Global.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
