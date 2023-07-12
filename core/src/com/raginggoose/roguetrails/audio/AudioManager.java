package com.raginggoose.roguetrails.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class AudioManager {
    private static final String TAG = AudioManager.class.getSimpleName();
    private final AssetLoader assetLoader;
    private final RogueTrails game;
    public boolean isPaused;
    private AudioType currentMusicType;
    private Music currentMusic;

    public AudioManager(RogueTrails game) {
        assetLoader = game.getAssetManager();
        this.game = game;
    }

    public void playAudio(AudioType type) {
        try {
            // Check if the audio type is music
            if (type.isMusic()) {
                if (currentMusicType == type) {
                    // Audio type is the current type, so it is already playing
                    return;
                } else if (currentMusic != null) {
                    currentMusic.stop();
                }

                playMusic(type);

            } else {
                assetLoader.manager.get(type.getFilePath(), Sound.class).play(game.getPreferences().getSoundVolume());
            }

            isPaused = false;
        } catch (Exception e) {
            // Handle the exception appropriately, such as logging an error message or providing fallback options
            Gdx.app.error(TAG, "Error playing audio: " + e.getMessage());
        }
    }

    private void playMusic(AudioType type) {
        // Set current type to the new type and play the new music
        currentMusicType = type;
        currentMusic = assetLoader.manager.get(type.getFilePath(), Music.class);
        currentMusic.setLooping(true);
        currentMusic.setVolume(game.getPreferences().getMusicVolume());
        currentMusic.play();
    }

    public void pauseMusic() {
        if (currentMusic != null) {
            currentMusic.pause();
            isPaused = true;
        } else {
            // Handle the situation when currentMusic is null, such as logging an error or skipping the operation
            Gdx.app.error(TAG, "No music is currently playing.");
        }
    }

    public void resumeMusic() {
        if (currentMusic != null) {
            currentMusic.play();
            isPaused = false;
        } else {
            // Handle the situation when currentMusic is null, such as logging an error or skipping the operation
            Gdx.app.error(TAG, "No music is currently playing.");
        }
    }

    public void changeVolume() {
        if (currentMusic != null)
            currentMusic.setVolume(game.getPreferences().getMusicVolume());
    }
}
