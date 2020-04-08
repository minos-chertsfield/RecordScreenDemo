package com.minos.recordscreendemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = (ToggleButton) findViewById(R.id.btn_record);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    Toast.makeText(MainActivity.this, "录屏开始", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(MainActivity.this, "录屏结束", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 开始录屏
     */
    private void startRecord() {


    }

    /**
     * 显示悬浮窗
     */
    private void createFloatingWindow() {


    }
}
