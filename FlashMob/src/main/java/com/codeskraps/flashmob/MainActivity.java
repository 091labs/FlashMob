package com.codeskraps.flashmob;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    private SharedPreferences prefs = null;
    private Button btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        btn.setText(getText());
        btn.setOnClickListener(this);
    }

    private String getText() {
        boolean isPlaying = !isPlaying();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isPlaying", isPlaying);
        editor.commit();
        return isPlaying ? "Stop" : "Start";
    }

    private boolean isPlaying(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getBoolean("isPlaying", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent playerService = new Intent(this, MediaPlayerService.class);
        if (isPlaying())
            stopService(playerService);
        else
            startService(playerService);

        btn.setText(getText());
    }
}
