package com.raginggoose.roguetrails.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.loader.AssetLoader;

/**
 * Handles audio for the program
 */
public class AudioManager {
    private static final String TAG = AudioManager.class.getSimpleName();
    private final AssetLoader assetLoader;
    private final RogueTrails game;
    public boolean isPaused;
    private AudioType currentMusicType;
    private Music currentMusic;

    /**
     * Creates a new audio manager for the game
     * @param game the game class for the game
     */
    public AudioManager(RogueTrails game) {
        assetLoader = game.getAssetManager();
        this.game = game;
    }

    /**
     * Plays an audio file
     * @param type the type of audio (sound or music)
     */
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
            // Log an error if the audio file cannot be played
            Gdx.app.error(TAG, "Error playing audio: " + e.getMessage());
        }
    }

    /**
     * Plays music audio
     * @param type the type of audio (sound or music)
     */
    private void playMusic(AudioType type) {
        // Set current type to the new type and play the new music
        currentMusicType = type;
        currentMusic = assetLoader.manager.get(type.getFilePath(), Music.class);
        currentMusic.setLooping(true);
        currentMusic.setVolume(game.getPreferences().getMusicVolume());
        currentMusic.play();
    }

    /**
     * Pauses the audio that is currently being played by the manager
     */
    public void pauseMusic() {
        if (currentMusic != null) {
            // If there is audio playing, then pause it
            currentMusic.pause();
            isPaused = true;
        } else {
            // Handle the situation when currentMusic is null, such as logging an error or skipping the operation
            Gdx.app.error(TAG, "No music is currently playing.");
        }
    }

    /**
     * Resume audio that has been paused by the manager
     */
    public void resumeMusic() {
        if (currentMusic != null) {
            currentMusic.play();
            isPaused = false;
        } else {
            // Handle the situation when currentMusic is null, such as logging an error or skipping the operation
            Gdx.app.error(TAG, "No music is currently playing.");
        }
    }

    /**
     * Changes the volume that the manager plays audio at
     */
    public void changeVolume() {
        if (currentMusic != null)
            // Adjust the volume based off of the game preferences volume setting
            currentMusic.setVolume(game.getPreferences().getMusicVolume());
    }
}
