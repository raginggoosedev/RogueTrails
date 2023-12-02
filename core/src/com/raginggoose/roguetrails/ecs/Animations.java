package com.raginggoose.roguetrails.ecs;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raginggoose.roguetrails.loader.AssetLoader;

/**
 * Contains all the animations used in the game
 */
public class Animations {
    public static TextureAtlas playerAtlas = null;
    public static Animation<TextureRegion> upAnim = null;
    public static Animation<TextureRegion> stop = null;
    public static float frameRate = 0.1f;

    /**
     * Loads all the animations
     * @param assetLoader the asset loader used to access the animations
     */
    public Animations(AssetLoader assetLoader) {
        playerAtlas = assetLoader.manager.get(AssetLoader.PLAYER_ATLAS);
        upAnim = new Animation<>(frameRate, playerAtlas.findRegions("rightwalk"));
        stop = new Animation<>(frameRate, playerAtlas.findRegions("idleright"));
    }

}
