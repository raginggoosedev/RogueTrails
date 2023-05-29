package com.raginggoose.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.loader.AssetLoader;

public class MenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;

    public MenuScreen(RogueTrails game) {
        stage = new Stage(new ScreenViewport());
        skin = game.getAssetManager().manager.get(AssetLoader.GAME_SKIN);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        Image image = new Image(skin, "title");
        table.add(image).padTop(20.0f).padLeft(20.0f).padRight(20.0f).expandX().expandY().fill();

        table.row().expandX().expandY();
        TextButton textButton = new TextButton("Play", skin);
        textButton.addListener(new ClickListener() {
           @Override
           public void clicked(InputEvent event, float x, float y) {
               game.setScreen(ScreenType.GAME);
           }
        });
        table.add(textButton).padTop(40.0f).padBottom(10.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();

        table.row().expandX().expandY();
        textButton = new TextButton("Settings", skin);
        table.add(textButton).padTop(10.0f).padBottom(10.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();

        table.row().expandX().expandY();
        textButton = new TextButton("Quit", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(textButton).padTop(10.0f).padBottom(20.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();
        stage.addActor(table);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
