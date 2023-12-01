package com.raginggoose.roguetrails.audio;

import com.raginggoose.roguetrails.loader.AssetLoader;

/**
 * Used to specify the details of audio that exists on an audio file
 */
public enum AudioType {
    BACKGROUND(AssetLoader.BACKGROUND_MUSIC, true, 0.5f);

    private final String filePath;
    private final boolean isMusic;

    /**
     * Creates a new audio type with its filepath, whether it is music or a sound, and the volume it should be set to
     * @param filePath the filepath of the audio file
     * @param isMusic whether the audio is music or a sound
     * @param volume the volume that the audio should be played at
     */
    AudioType(String filePath, boolean isMusic, float volume) {
        this.filePath = filePath;
        this.isMusic = isMusic;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isMusic() {
        return isMusic;
    }
}
