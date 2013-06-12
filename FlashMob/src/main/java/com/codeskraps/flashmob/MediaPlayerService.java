package com.codeskraps.flashmob;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by codeskraps on 12/06/13.
 */
public class MediaPlayerService extends Service implements MediaPlayer.OnPreparedListener {
    private static final String TAG = MediaPlayerService.class.getSimpleName();

    private MediaPlayer mMediaPlayer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            Uri url = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(getApplicationContext(), url);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync(); // prepare async to not block main thread

            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                    new Intent(getApplicationContext(), MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("song")
                    .setContentText("pretty cool song")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pi)
                    .build();
            // .setLargeIcon(R.drawable.ic_launcher)

            notification.tickerText = "some song";
            notification.flags |= Notification.FLAG_ONGOING_EVENT;

            startForeground((int) System.currentTimeMillis(), notification);

        } catch (Exception e) {
            Log.i(TAG, "Handled: MediaPlayer not set");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }
}
