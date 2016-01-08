package com.lling.eventbusdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import de.greenrobot.event.EventBus;

public class StickyModeActivity extends AppCompatActivity {

    int index = 0;
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

}
