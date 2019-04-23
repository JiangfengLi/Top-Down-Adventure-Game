package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;


    /**
     * Create the MediaPlayer class and play the music file specified by music File
     * @param musicFile
     */
    public void playMusic(String musicFile) {
        try {
            Media sound = new Media(new File(musicFile).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * close music
     */
    public void stopMusic() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
