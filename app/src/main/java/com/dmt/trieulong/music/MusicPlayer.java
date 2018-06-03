package com.dmt.trieulong.music;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

public class MusicPlayer implements MediaPlayer.OnCompletionListener {
        public static final int PLAYER_IDLE = -1;
        public static final int PLAYER_PLAY = 1;
        public static final int PLAYER_PAUSE = 2;
        private MediaPlayer mediaPlayer;
        private int state;
        private OnCompletionListener onCompletionListener;
        private boolean isEnd;

        public MusicPlayer(){
        }

        public int getState() {
            return state;
        }

        public void setup(String path) {
            try {
                state = PLAYER_IDLE;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(path);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(this);
                isEnd = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public int getTimeTotal() {
            return mediaPlayer.getDuration() / 1000;
        }

        public void play() {
            if (state == PLAYER_IDLE || state == PLAYER_PAUSE) {
                state = PLAYER_PLAY;
                mediaPlayer.start();
            }
        }

        public void stop() {
            if (state == PLAYER_PLAY || state == PLAYER_PAUSE) {
                state = PLAYER_IDLE;
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        public void pause() {
            if (state == PLAYER_PLAY) {
                mediaPlayer.pause();
                state = PLAYER_PAUSE;
            }
        }

        public int getTimeCurrent() {
            if (state != PLAYER_IDLE) {
                return mediaPlayer.getCurrentPosition() / 1000;
            } else
                return 0;
        }

        public void seek(int time) {
            mediaPlayer.seekTo(time);
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            // khi ket thuc bai hat no se vào hàm này, ta viết 1 interface để cho activity
            // biết khi nào kết thúc bài hát để chuyển bài
            // gọi interface
            if(isEnd) {
                onCompletionListener.OnEndMusic();
                isEnd = false;
            }
        }

        public void setOnCompletionListener(OnCompletionListener onCompletionListener){
            this.onCompletionListener = onCompletionListener;
        }

public interface OnCompletionListener{
    void OnEndMusic();
}
}
