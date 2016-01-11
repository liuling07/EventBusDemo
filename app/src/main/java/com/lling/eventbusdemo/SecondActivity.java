package com.lling.eventbusdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import de.greenrobot.event.EventBus;

public class SecondActivity extends AppCompatActivity {

    private EditText mMessageET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mMessageET = (EditText) findViewById(R.id.messageET);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("postEvent", Thread.currentThread().getName());
//                        String message = mMessageET.getText().toString();
//                        if(TextUtils.isEmpty(message)) {
//                            message = "defaule message";
//                        }
//                        EventBus.getDefault().post(new MessageEvent(message));
//                    }
//                }).start();
                Log.e("postEvent", Thread.currentThread().getName());
                String message = mMessageET.getText().toString();
                if(TextUtils.isEmpty(message)) {
                    message = "defaule message";
                }
                EventBus.getDefault().post(new MessageEvent(message));
            }
        });

        findViewById(R.id.send_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessageET.getText().toString();
                if(TextUtils.isEmpty(message)) {
                    message = "defaule message";
                }
                Intent intent = new Intent();
                intent.setAction("message_broadcast");
                intent.putExtra("message", message);
                sendBroadcast(intent);
            }
        });
    }

}
