package Utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private static boolean isAudioActive = Config.getInstance().isAudioActive();
    private static int audioVolume = Config.getInstance().getAudioVolume();

    private Clip clip;

    public SoundManager(String s) {
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

    public void playOnce() {
        if(isAudioActive)
        this.clip.stop();
        this.clip.setMicrosecondPosition(0);
        this.clip.start();
    }

    public  void startLoop(){
        if(isAudioActive)
        this.clip.setMicrosecondPosition(0);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public  void stopLoop(){
        this.clip.stop();
    }

    public static void setIsAudioActive(boolean isAudioActive) {
        SoundManager.isAudioActive = isAudioActive;
    }

    public static void setAudioVolume(int audioVolume) {
        SoundManager.audioVolume = audioVolume;
    }
}

