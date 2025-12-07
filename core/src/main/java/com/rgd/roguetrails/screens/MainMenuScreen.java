package com.rgd.roguetrails.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.rgd.roguetrails.RogueTrails;

/**
 * @author Michael (ExplorerDonutz) Quick
 * @version 1.0, 2025/12/07
 * A screen which extends the BaseScreen to display the main menu
 */
public class MainMenuScreen extends BaseScreen {
    private final Stage stage;
    private final Table root;
    private final Skin skin;

    /**
     * The constructor for the MainMenuScreen which initializes all the Scene2D actors and stage
     *
     * @param game The Game object which runs the entire game
     */
    public MainMenuScreen(RogueTrails game) {
        // Call the BaseScreen constructor and pass the game object
        super(game);

        // Initialize the stage and its skin
        stage = new Stage(this.viewport);
        Gdx.input.setInputProcessor(stage);
        // TODO move to asset manager
        skin = new Skin(Gdx.files.internal("skin.json"));

        // Create the root table which will hold the actors
        root = new Table();
        root.setFillParent(true);

        // Title label
        Label label = new Label("Rogue Trails", skin);
        label.setAlignment(Align.center);
        root.add(label);

        root.row();

        // Play button
        TextButton textButton = new TextButton("Play", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(Actions.fadeOut(1));
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        textButton.padLeft(16.0f);
        textButton.padRight(16.0f);
        root.add(textButton).fillX();

        root.row();

        // Settings button
        // TODO Make settings screen and add a ChangeListener which changes to that screen on button press
        textButton = new TextButton("Settings", skin);
        textButton.padLeft(16.0f);
        textButton.padRight(16.0f);
        root.add(textButton).fillX();

        root.row();

        // Quit button
        textButton = new TextButton("Quit", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addAction(Actions.fadeOut(1));
                Gdx.app.exit();
            }
        });

        textButton.padLeft(16.0f);
        textButton.padRight(16.0f);
        root.add(textButton).fillX();
        stage.addActor(root);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
