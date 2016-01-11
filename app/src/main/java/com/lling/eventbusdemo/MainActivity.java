package com.lling.eventbusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private TextView mMessageView;
    private MessageBroadcastReceiver mBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册事件
        EventBus.getDefault().register(this);
        //注册广播
        IntentFilter intentFilter = new IntentFilter("message_broadcast");
        mBroadcastReceiver = new MessageBroadcastReceiver();
        registerReceiver(mBroadcastReceiver, intentFilter);
        mMessageView = (TextView) findViewById(R.id.message);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button2sticky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StickyModeActivity.class);
                startActivity(intent);
            }
        });
    }

    //在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.PostThread)
    public void onMessageEventPostThread(MessageEvent messageEvent) {
        Log.e("PostThread", Thread.currentThread().getName());
    }

    //在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        Log.e("MainThread", Thread.currentThread().getName());
    }

    //如果产生事件的是UI线程，则在新的线程中执行。如果产生事件的是非UI线程，则在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onMessageEventBackgroundThread(MessageEvent messageEvent) {
        Log.e("BackgroundThread", Thread.currentThread().getName());
    }

    //无论产生事件的是否是UI线程，都在新的线程中执行
    @Subscribe(threadMode = ThreadMode.Async)
    public void onMessageEventAsync(MessageEvent messageEvent) {
        Log.e("Async", Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
        //取消广播注册
        unregisterReceiver(mBroadcastReceiver);
    }

    public class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mMessageView.setText("Message from SecondActivity:" + intent.getStringExtra("message"));
        }
    }
}
