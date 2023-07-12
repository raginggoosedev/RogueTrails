package com.raginggoose.roguetrails.audio;

import com.raginggoose.roguetrails.loader.AssetLoader;

public enum AudioType {
    BACKGROUND(AssetLoader.BACKGROUND_MUSIC, true, 0.5f);

    private final String filePath;
    private final boolean isMusic;

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
