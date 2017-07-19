package com.example.puretone;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Created by xiaoniu on 2017/7/6.
 */

public class PlayThread extends Thread {
    public static final int RATE = 44100;
//    public static final float MAXVOLUME = 100f;

    AudioTrack mAudioTrack;
    public static boolean ISPLAYSOUND;

    /**
     * 总长度
     **/
    int length;
    /**
     * 一个正弦波的长度
     **/
    int waveLen;
    /**
     * 频率
     **/
    int Hz;
    /**
     * 正弦波
     **/
    byte[] wave;

    /**
     * 初始化
     * @param rate 频率
     */
    public PlayThread(int rate) {
        if (rate > 0) {
            Hz = rate;
            waveLen = RATE / Hz;
            length = waveLen * Hz;
            wave = new byte[RATE];
            mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, RATE,
                    AudioFormat.CHANNEL_CONFIGURATION_STEREO, // CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_8BIT, length, AudioTrack.MODE_STREAM);
            ISPLAYSOUND = true;
            wave = SinWave.sin(wave, waveLen, length);
        } else {
            return;
        }

    }

    @Override
    public void run() {
        super.run();
        if (null != mAudioTrack)
            mAudioTrack.play();
        //一直播放
        while (ISPLAYSOUND) {
            mAudioTrack.write(wave, 0, length);
        }

    }

    /**
     * 设置左右声道，左声道时设置右声道音量为0，右声道设置左声道音量为0
     *
     * @param left  左声道
     * @param right 右声道
     */
    public void setChannel(boolean left, boolean right) {
        if (null != mAudioTrack) {
            mAudioTrack.setStereoVolume(left ? 1 : 0, right ? 1 : 0);
        }
    }

    //设置音量
    public void setVolume(float left, float right) {
        if (null != mAudioTrack) {
            mAudioTrack.setStereoVolume(left,right);
        }
    }

    public void stopPlay() {
        ISPLAYSOUND = false;
        releaseAudioTrack();
    }

    private void releaseAudioTrack() {
        if (null != mAudioTrack) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }
}
