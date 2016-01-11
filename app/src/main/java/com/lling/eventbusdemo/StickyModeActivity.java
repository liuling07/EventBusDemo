package com.lling.eventbusdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

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
                EventBus.getDefault().register(StickyModeActivity.this);
            }
        });

        findViewById(R.id.unregist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().unregister(StickyModeActivity.this);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.PostThread, sticky = true)
    public void onMessageEventPostThread(MessageEvent messageEvent) {
        Log.e("PostThread", messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        Log.e("MainThread", messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.BackgroundThread, sticky = true)
    public void onMessageEventBackgroundThread(MessageEvent messageEvent) {
        Log.e("BackgroundThread", messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.Async, sticky = true)
    public void onMessageEventAsync(MessageEvent messageEvent) {
        Log.e("Async", messageEvent.getMessage());
    }

}
