package com.rgd.roguetrails.utils;


import com.badlogic.gdx.Gdx;

public class Constants {

    // Assets
    public static final String SKIN = String.valueOf(Gdx.files.internal("skin.json"));
    public static final String SKIN_ATLAS = String.valueOf(Gdx.files.internal("skin.atlas"));

    // Box2D Scaling
    // Pixels Per Meter
    public static final float PPM = 32;

    // Initial player position
    public static final float PLAYER_ORIGIN_X = 32;
    public static final float PLAYER_ORIGIN_Y = 64;
    public static final float PLAYER_WIDTH = 32;
    public static final float PLAYER_HEIGHT = 32;

    // Hallway constants
    public static final float HALLWAY_WIDTH = 128;
    public static final float HALLWAY_LENGTH = 512;
    public static final int VERTICAL_HALL = 0;
    public static final int HORIZONTAL_HALL = 1;
}
