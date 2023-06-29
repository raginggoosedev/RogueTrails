package com.raginggoose.roguetrails;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.raginggoose.roguetrails.audio.AudioManager;
import com.raginggoose.roguetrails.loader.AssetLoader;
import com.raginggoose.roguetrails.screens.ScreenType;

import java.util.EnumMap;

/**
 * The main game class which calls upon methods from the LibGDX Game class to create and render screens
 *
 * @author Michael Quick, Josh Muszka, Nicholas Woo, Eunsung Kim
 * @version 1.0, 2023/03/26
 */
public class RogueTrails extends Game {
    public static final boolean DEBUG = true;
    private final String TAG = this.getClass().getSimpleName();
    private SpriteBatch batch;
    private AssetLoader assetManager;
    private AudioManager audioManager;
    private EnumMap<ScreenType, Screen> screenCache;
    private GamePreferences preferences;
    private Screen prevScreen;

    @Override
    public void create() {
        if (DEBUG) {
            Gdx.app.log(TAG, "------ Game is in DEBUG! ------");
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
        }

        batch = new SpriteBatch();
        assetManager = new AssetLoader();
        preferences = new GamePreferences();

        // Use an enum map to cache screens by their screen types
        screenCache = new EnumMap<>(ScreenType.class);

        // TODO make loading screen
        assetManager.queueAssets();
        assetManager.manager.finishLoading();

        audioManager = new AudioManager(this);

        // Set the first screen to type MENU
        setScreen(ScreenType.MENU);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assetManager.manager.dispose();
    }

    /**
     * Sets the next screen for the game
     * @param screenType the type of screen to be set
     */
    public void setScreen(ScreenType screenType) {
        prevScreen = screen;

        screen = getScreen();

        Screen screen = screenCache.get(screenType);

        if (screen == null) {
            // Screen doesn't exist yet, create a new screen
            Gdx.app.log(TAG, "Creating " + screenType + " screen");
            try {
                // Try to create the new screen and add it to the cache
                Screen newScreen = (Screen) ClassReflection.getConstructor(screenType.getScreenClass(), RogueTrails.class).newInstance(this);
                screenCache.put(screenType, newScreen);

                setScreen(newScreen);
            } catch (ReflectionException e) {
                throw new GdxRuntimeException("Screen " + screenType + " could not be created!", e);
            }
        } else {
            // Screen exists
            setScreen(screen);
        }


    }

    // Public getters

    public SpriteBatch getBatch() {
        return batch;
    }

    public AssetLoader getAssetManager() {
        return assetManager;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public GamePreferences getPreferences() {
        return preferences;
    }

    public Screen getPrevScreen() {
        return prevScreen;
    }
}
