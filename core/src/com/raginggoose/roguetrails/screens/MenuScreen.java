package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class MenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final RogueTrails game;

    public MenuScreen(RogueTrails game) {
        stage = new Stage(new ScreenViewport());
        skin = game.getAssetManager().manager.get(AssetLoader.GAME_SKIN);
        this.game = game;

        Table table = new Table();
        table.setFillParent(true);

        Image image = new Image(skin, "title");
        table.add(image).padTop(20.0f).padLeft(20.0f).padRight(20.0f).expandX().expandY().fill();

        table.row().expandX().expandY();
        TextButton textButton = new TextButton("Play", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fade out then change to game screen
                stage.addAction(Actions.sequence(Actions.fadeOut(0.75f), Actions.run(() -> game.setScreen(ScreenType.GAME))));
            }
        });
        table.add(textButton).padTop(40.0f).padBottom(10.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();

        table.row().expandX().expandY();
        textButton = new TextButton("Settings", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fade out then change to game screen
                stage.addAction(Actions.sequence(Actions.fadeOut(0.75f), Actions.run(() -> game.setScreen(ScreenType.SETTINGS))));
            }
        });
        table.add(textButton).padTop(10.0f).padBottom(10.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();

        table.row().expandX().expandY();
        textButton = new TextButton("Quit", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fade out then exit application
                stage.addAction(Actions.sequence(Actions.fadeOut(0.75f), Actions.run(() -> Gdx.app.exit())));
            }
        });
        table.add(textButton).padTop(10.0f).padBottom(20.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1)));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
