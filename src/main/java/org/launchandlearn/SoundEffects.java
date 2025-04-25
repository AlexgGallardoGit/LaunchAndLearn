package org.launchandlearn;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class SoundEffects {
    public void playHitSound() {
        try {
            URL hitSoundUrl = getClass().getResource("/audio/Goodsound.mp3");
            if (hitSoundUrl == null) throw new RuntimeException("Hit sound not found!");

            Media hitMedia = new Media(hitSoundUrl.toString());
            MediaPlayer hitPlayer = new MediaPlayer(hitMedia);

            hitPlayer.setVolume(1.0); // FULL VOLUME
            hitPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playMissSound() {
        try {
            URL missSoundUrl = getClass().getResource("/audio/Errorsound.mp3");
            if (missSoundUrl == null) throw new RuntimeException("Miss sound not found!");

            Media missMedia = new Media(missSoundUrl.toString());
            MediaPlayer missPlayer = new MediaPlayer(missMedia);

            missPlayer.setVolume(1.0); // FULL VOLUME
            missPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
