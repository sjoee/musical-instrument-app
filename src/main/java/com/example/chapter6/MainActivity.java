package com.example.chapter6;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button button1, button2;
    MediaPlayer mpUkulele, mpDrums;
    int playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        button1 = (Button)findViewById(R.id.btnUkulele);
        button2 = (Button)findViewById(R.id.btnDrums);
        button1.setOnClickListener(bUkulele);
        button2.setOnClickListener(bDrums);

        mpUkulele = new MediaPlayer();
        mpUkulele = MediaPlayer.create(this, R.raw.ukulele);

        //get the mp3 file from res folder and raw folder
        mpDrums = new MediaPlayer();
        mpDrums = MediaPlayer.create(this, R.raw.drums);

        playing = 0;
    }
    // what happen when the user click on button1

    Button.OnClickListener bUkulele= new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(playing) {
                case 0:  // if playing = 0, not music play yet
                    mpUkulele.start(); // start playing the music
                    playing = 1; // set value to 1 because music already played
                    button1.setText("Pause Ukulele Song");  // change button caption
                    button2.setVisibility(View.INVISIBLE);  // hide button2 because we cannot play 2 different music concurrently
                    break;
                case 1:  // if playing = 1, music already play
                    mpUkulele.pause();  // pause the current music
                    playing = 0;  // set value to 0 because music no longer played
                    button1.setText("Play Ukulele Song");  // change button caption
                    button2.setVisibility(View.VISIBLE); // show the button2 because we want the user to choose which button to click
                    break;
            }
        }
    };
    Button.OnClickListener bDrums = new Button.OnClickListener( ) {

        @Override
        public void onClick(View v) {
            switch(playing) {
                case 0:
                    mpDrums.start();
                    playing = 1;
                    button2.setText("Pause Drums Song");
                    button1.setVisibility(View.INVISIBLE);
                    break;
                case 1:
                    mpDrums.pause();
                    playing = 0;
                    button2.setText("Play Drums Song");
                    button1.setVisibility(View.VISIBLE);
                    break;
            }
        }

    };
}