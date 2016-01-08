package com.lling.eventbusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import de.greenrobot.event.EventBus;

public class StickyModeActivity extends AppCompatActivity {

    int index = 0;
    private MessageBroadcastReceiver mBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_mode);
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("test" + index++));
            }
        });
        findViewById(R.id.regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().registerSticky(StickyModeActivity.this);
            }
        });

        findViewById(R.id.unregist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().unregister(StickyModeActivity.this);
            }
        });

        findViewById(R.id.send_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("sticky_broadcast");
                intent.putExtra("message", "test" + index++);
                sendStickyBroadcast(intent);
            }
        });

        findViewById(R.id.regist_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter("sticky_broadcast");
                mBroadcastReceiver = new MessageBroadcastReceiver();
                registerReceiver(mBroadcastReceiver, intentFilter);
            }
        });

        findViewById(R.id.unregist_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(mBroadcastReceiver);
            }
        });
    }

    public void onEvent(MessageEvent messageEvent) {
        Log.e("onEvent", messageEvent.getMessage());
    }

    public void onEventMainThread(MessageEvent messageEvent) {
        Log.e("onEventMainThread", messageEvent.getMessage());
    }

    public void onEventBackgroundThread(MessageEvent messageEvent) {
        Log.e("onEventBackgroundThread", messageEvent.getMessage());
    }

    public void onEventAsync(MessageEvent messageEvent) {
        Log.e("onEventAsync", messageEvent.getMessage());
    }


    public class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive", intent.getStringExtra("message"));
        }
    }

}
