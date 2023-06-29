package com.raginggoose.roguetrails.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class AudioManager {
    private final AssetLoader assetLoader;
    public boolean isPaused;
    private AudioType currentMusicType;
    private Music currentMusic;
    private final RogueTrails game;

    public AudioManager(RogueTrails game) {
        assetLoader = game.getAssetManager();
        this.game = game;
    }

    public void playAudio(AudioType type) {
        // Check if the audio type is music
        if (type.isMusic()) {
            if(currentMusicType == type) {
                // Audio type is the current type, so it is already playing
                return;
            } else if (currentMusic != null) {
                currentMusic.stop();
            }

            // Set current type to the new type and play the new music
            currentMusicType = type;
            currentMusic = assetLoader.manager.get(type.getFilePath(), Music.class);
            currentMusic.setLooping(true);
            currentMusic.setVolume(game.getPreferences().getMusicVolume());
            currentMusic.play();
        } else {
            assetLoader.manager.get(type.getFilePath(), Sound.class).play(game.getPreferences().getSoundVolume());
        }

        isPaused = false;
    }

    public void pauseMusic() {
        currentMusic.pause();
        isPaused = true;
    }

    public void resumeMusic() {
        currentMusic.play();
        isPaused = false;
    }
}
