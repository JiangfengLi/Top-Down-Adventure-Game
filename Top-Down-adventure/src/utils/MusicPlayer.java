package utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Wrapper class for JavaFX MediaPlayer. Provides
 * methods for starting and stopping music files when
 * given a string representing the relative file path
 * to the audio.
 * @author JiaZhuoMi, Wes Rodgers
 *
 */
public class MusicPlayer {

    private MediaPlayer mediaPlayer;


    /**
     * Create the MediaPlayer class and play the music file specified by music File
     * passing in 0 will make it indefinite, any other number is the times to repeat.
     * @param musicFile the file to play
     * @param i the number of times to repeat
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
    
    /**
     * sets the music's volume 
     * @param i the percentage to set the volume to 
     */
    public void setVolume(double i) {
    	mediaPlayer.setVolume(i);
    }
}
