package org.launchandlearn;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class BackgroundMusic {
    private MediaPlayer mediaPlayer;

    public BackgroundMusic() {
        try {
            URL mediaUrl = getClass().getResource("/audio/gamesound.mp3");
            if (mediaUrl == null) {
                throw new RuntimeException("Audio file not found!");
            }


            Media media = new Media(mediaUrl.toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop
            mediaPlayer.setVolume(0.5); // Optional: adjust volume
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (mediaPlayer != null) mediaPlayer.play();
    }

    public void stop() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    public void pause() {
        if (mediaPlayer != null) mediaPlayer.pause();
    }
}
