package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    //    --------------------------------------------------------
    //                       STATIC FIELD
    //    --------------------------------------------------------

    public static final int SOUND = 0;
    public static final int MUSIC = 1;

    private static boolean isSoundActive = SoundConfig.getInstance().isSoundActive();
    private static boolean isMusicActive = SoundConfig.getInstance().isMusicActive();

    //    --------------------------------------------------------
    //                      INSTANCE FIELD
    //    --------------------------------------------------------

    private Clip clip;
    private int audioType;

    //    --------------------------------------------------------
    //                       CONSTRUCTOR
    //    --------------------------------------------------------

    public SoundManager(String s,int audioType) {
        this.audioType = audioType;
        File audio = new File(s);
        AudioFileFormat aff = null;
        try {
            aff = AudioSystem.getAudioFileFormat(audio);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        assert aff != null;
        AudioFormat af = aff.getFormat();
        AudioInputStream ais = null;
        try {
            ais = AudioSystem.getAudioInputStream(audio);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        assert ais != null;
        int bufferSize = (int)ais.getFrameLength() * af.getFrameSize();
        DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, ais.getFormat(), bufferSize);

        try {
            this.clip = (Clip)AudioSystem.getLine(dataLineInfo);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            try {
                this.clip.open(ais);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    --------------------------------------------------------
    //                      INSTANCE METHODS
    //    --------------------------------------------------------

    public void playOnce() {
        if(audioType == SOUND && isSoundActive || audioType == MUSIC && isMusicActive) {
            this.clip.stop();
            this.clip.setMicrosecondPosition(0);
            this.clip.start();
        }
    }

    public void startLoop() {
        if(audioType == SOUND && isSoundActive || audioType == MUSIC && isMusicActive){
            this.clip.setMicrosecondPosition(0);
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public  void stopLoop(){
        this.clip.stop();
    }

    //    --------------------------------------------------------
    //                      STATIC METHODS
    //    --------------------------------------------------------

    public static boolean isSoundActive() {
        return isSoundActive;
    }

    public static void switchSoundConfig() {
        isSoundActive = !isSoundActive;
        SoundConfig.getInstance().setSoundActive(isSoundActive);
    }

    public static boolean isMusicActive() {
        return isMusicActive;
    }

    public static void switchMusicConfig() {
        isMusicActive = !isMusicActive;
        SoundConfig.getInstance().setMusicActive(isMusicActive);
    }

}
