package com.bomberman.control;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    public static Sound BGM = new Sound("/sound/background.wav");
    public static Sound Exp = new Sound("/sound/explosion.wav");
    public static Sound placeB = new Sound("/sound/place_bomb.wav");
    public static Sound Die = new Sound("/sound/player_die.wav");
    public static Sound PUP = new Sound("/sound/power_up.wav");
    public static Sound RUN = new Sound("/sound/running.wav");

    public static enum Volume {
        MUTE, ON
    }

    public static Volume volume = Volume.ON;

    private Clip clip;


    public Sound(String soundFileName) {
        try {
            URL url = Sound.class.getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play(Boolean loop) {
        if (volume != Volume.MUTE) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
            if(loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }

    }

    public void stop() {
        clip.stop();
        clip.setFramePosition(0);
    }

    public void mute() {
        volume = Volume.MUTE;
    }

    public boolean isPlaying() {
        return clip.isRunning();
    }
}
