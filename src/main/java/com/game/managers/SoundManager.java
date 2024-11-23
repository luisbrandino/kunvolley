package com.game.managers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.game.Main;
import com.game.utils.RandomGenerator;

public final class SoundManager {
    private List<Clip> _activeClips;
    private List<String> _soundFiles;

    public SoundManager(List<String> soundFiles) {
        _activeClips = new ArrayList<>();
        _soundFiles = soundFiles;
    }

    private Clip loadSound(String soundFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        URL soundURL = Main.class.getResource(soundFile);
        
        if (soundURL == null)
            throw new IOException("Sound not found: " + soundFile);

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }

    public void playSound(int index) {
        if (index < 0 || index >= _soundFiles.size())
            return;

        try {
            Clip clip = loadSound(_soundFiles.get(index));
            clip.setFramePosition(0); 
            clip.start();  
            _activeClips.add(clip); 
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void playRandom() {
        if (_soundFiles.isEmpty())
            return;

        int randomIndex = RandomGenerator.nextInt(0, _soundFiles.size() - 1);
        playSound(randomIndex);
    }
}