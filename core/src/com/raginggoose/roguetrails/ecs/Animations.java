package com.raginggoose.roguetrails.ecs;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class Animations {
    public static TextureAtlas playerAtlas = null;
    public static Animation<TextureRegion> upAnim = null;
    public static Animation<TextureRegion> stop = null;
    public static float frameRate = 0.1f;
    public Animations(AssetLoader assetLoader) {
        playerAtlas = assetLoader.manager.get(AssetLoader.PLAYER_ATLAS);
        upAnim = new Animation<>(frameRate, playerAtlas.findRegions("rightwalk"));
        stop = new Animation<>(frameRate, playerAtlas.findRegions("idleright"));
    }

}
