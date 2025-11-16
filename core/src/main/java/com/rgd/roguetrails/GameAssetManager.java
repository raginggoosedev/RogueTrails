package com.rgd.roguetrails;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public class GameAssetManager implements Disposable {
    private final AssetManager manager;

    public GameAssetManager() {
        manager = new AssetManager();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
