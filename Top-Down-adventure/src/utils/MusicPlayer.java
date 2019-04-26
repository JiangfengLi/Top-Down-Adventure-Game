package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayer {

    private MediaPlayer mediaPlayer;


    /**
     * Create the MediaPlayer class and play the music file specified by music File
     * passing in 0 will make it indefinite, any other number is the times to repeat.
     * @param musicFile
     */
    public void playMusic(String musicFile, int i) {
        try {
            Media sound = new Media(MusicPlayer.class.getResource(musicFile).toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(i == 0 ? MediaPlayer.INDEFINITE : i);
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
    
    public void setVolume(double i) {
    	mediaPlayer.setVolume(i);
    }
}
