package com.raginggoose.roguetrails.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetLoader {
    public final AssetManager manager = new AssetManager();

    // Textures
    //public static final String PLAYER_MOVE = "playerSprites/playerMove.png";
    public static final String PLAYER_ATLAS = "playerSprites/player.atlas";
    public static final String PLAYER_TEXTURE = "playerSprites/player.png";

    public void queueAssets() {
        manager.load(PLAYER_ATLAS, TextureAtlas.class);
        manager.load(PLAYER_TEXTURE, Texture.class);
    }
}
