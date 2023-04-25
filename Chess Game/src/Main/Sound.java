package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.net.URL;
import javax.sound.sampled.Clip;

public class Sound
{
    Clip clip;
    URL[] soundURL;
    
    public Sound() {
        (this.soundURL = new URL[30])[0] = this.getClass().getResource("/sfx/move.wav");
        this.soundURL[1] = this.getClass().getResource("/sfx/check.wav");
        this.soundURL[2] = this.getClass().getResource("/sfx/checkMate.wav");
        this.soundURL[3] = this.getClass().getResource("/sfx/rattle.wav");
        this.soundURL[4] = this.getClass().getResource("/sfx/move.wav");
    }
    
    public void setFile(final int i) {
        try {
            final AudioInputStream ais = AudioSystem.getAudioInputStream(this.soundURL[i]);
            (this.clip = AudioSystem.getClip()).open(ais);
        }
        catch (Exception ex) {}
    }
    
    public void play() {
        this.clip.start();
    }
    
    public void loop() {
        this.clip.loop(-1);
    }
    
    public void stop() {
        this.clip.stop();
    }
}