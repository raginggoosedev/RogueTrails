package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class SettingsScreen implements Screen {
    private final RogueTrails game;
    private final Stage stage;

    public SettingsScreen(RogueTrails game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());


        // Set up the stage when the screen is constructed
        Table table = new Table();
        table.setFillParent(true);

        Skin skin = game.getAssetManager().manager.get(AssetLoader.GAME_SKIN);

        // music volume
        Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(game.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener(event -> {
            game.getPreferences().setMusicVolume(volumeMusicSlider.getValue());

            // Update current music volume
            game.getAudioManager().changeVolume();
            return false;
        });

        // sound volume
        Slider soundMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        soundMusicSlider.setValue(game.getPreferences().getSoundVolume());
        soundMusicSlider.addListener(event -> {
            game.getPreferences().setSoundVolume(soundMusicSlider.getValue());
            return false;
        });

        // music on/off
        CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked(game.getPreferences().isMusicEnabled());
        musicCheckbox.addListener(event -> {
            boolean enabled = musicCheckbox.isChecked();
            game.getPreferences().setMusicEnabled(enabled);

            if (!enabled)
                game.getAudioManager().pauseMusic();
            else
                game.getAudioManager().resumeMusic();
            return false;
        });

        // sound on/off
        CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked(game.getPreferences().isSoundEffectsEnabled());
        soundEffectsCheckbox.addListener(event -> {
            boolean enabled = soundEffectsCheckbox.isChecked();
            game.getPreferences().setSoundEffectsEnabled(enabled);
            return false;
        });

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getPrevScreen());
                stage.addAction(Actions.fadeOut(1));
            }
        });

        Label titleLabel = new Label("Settings", skin);
        Label volumeMusicLabel = new Label("Music Volume", skin);
        Label volumeSoundLabel = new Label("Sound Volume", skin);
        Label musicOnOffLabel = new Label("Music", skin);
        Label soundOnOffLabel = new Label("Sound Effect", skin);

        table.add(titleLabel).colspan(2).expand().fill().pad(10, 10, 10, 10);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeMusicLabel).left().expand().fill().pad(10, 10, 10, 10);
        table.add(volumeMusicSlider).expand().fill().pad(10, 10, 10, 10);
        table.row().pad(10, 0, 0, 10);
        table.add(musicOnOffLabel).left().expand().fill().pad(10, 10, 10, 10);
        table.add(musicCheckbox).expand().fill().pad(10, 10, 10, 10);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeSoundLabel).left().expand().fill().pad(10, 10, 10, 10);
        table.add(soundMusicSlider).expand().fill().pad(10, 10, 10, 10);
        table.row().pad(10, 0, 0, 10);
        table.add(soundOnOffLabel).left().expand().fill().pad(10, 10, 10, 10);
        table.add(soundEffectsCheckbox).expand().fill().pad(10, 10, 10, 10);
        table.row().pad(10, 0, 0, 10);
        table.add(backButton).colspan(2).expand().fill().pad(10, 10, 10, 10);


        stage.addActor(table);
    }


    @Override
    public void show() {
        // Do these every time the screen is shown
        Gdx.input.setInputProcessor(stage);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.75f, 0.5f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
