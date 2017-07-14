package com.example.puretone;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class PureToneActivity extends Activity implements View.OnClickListener {
    private PlayThread mPlayThread;

    Button btnPlay;
    Button btnLeft;
    Button btnRight;
    Button btnStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pure_tone);

        btnPlay = (Button) findViewById(R.id.btn_play);
        btnLeft = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnPlay.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnStop.setOnClickListener(this);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            if (null != mPlayThread) {
                mPlayThread.stopPlay();
                mPlayThread = null;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                playSound(true,true);
                break;
            case R.id.btn_left:
                playSound(true,false);
                break;
            case R.id.btn_right:
                playSound(false,true);
                break;
            case R.id.btn_stop:
                if (null != mPlayThread) {
                    mPlayThread.stopPlay();
                    mPlayThread = null;
                }
                break;
        }
    }

    private void playSound(boolean left, boolean right) {
        if (null != mPlayThread) {
            mPlayThread.stopPlay();
            mPlayThread = null;
        }
        mPlayThread = new PlayThread(400);
        mPlayThread.setChannel(left,right);
        mPlayThread.start();
    }


}