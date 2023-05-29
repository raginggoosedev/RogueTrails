package com.raginggoose.roguetrails.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetLoader {
    public final AssetManager manager = new AssetManager();

    // Textures
    //public static final String PLAYER_MOVE = "playerSprites/playerMove.png";
    public static final String PLAYER_ATLAS = "playerSprites/player.atlas";
    public static final String PLAYER_TEXTURE = "playerSprites/player.png";

    // Game Skin
    public static final String GAME_SKIN = "skin.json";
    public static final String SKIN_ATLAS = "skin.atlas";

    public void queueAssets() {
        manager.load(PLAYER_ATLAS, TextureAtlas.class);
        manager.load(PLAYER_TEXTURE, Texture.class);
        manager.load(SKIN_ATLAS, TextureAtlas.class);
        manager.load(GAME_SKIN, Skin.class, new SkinLoader.SkinParameter(SKIN_ATLAS));
    }
}
