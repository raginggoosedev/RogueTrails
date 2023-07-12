package com.raginggoose.roguetrails.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.raginggoose.roguetrails.RogueTrails;
import com.raginggoose.roguetrails.screens.ScreenType;

/**
 * In-game menu that pauses gameplay and displays options
 */
public class Menu {
    private final Table table;
    private boolean isOpening;
    private boolean isClosing;

    /**
     * Creates a new menu
     *
     * @param stage the stage for the menu to be drawn on
     * @param skin  the skin used for the actors
     * @param game  the game being played
     */
    public Menu(Stage stage, Skin skin, RogueTrails game) {

        table = new Table();
        table.setFillParent(true);

        // Create a Pixmap for the translucent black color
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.5f)); // 0.5f represents 50% transparency
        pixmap.fill();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));

        // Dispose the pixmap
        pixmap.dispose();


        table.row().expandX().expandY();
        TextButton textButton = new TextButton("Resume", skin);
        table.add(textButton).padTop(80.0f).padBottom(20.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getScreen().resume();
            }
        });

        table.row().expandX().expandY();
        textButton = new TextButton("Settings", skin);
        table.add(textButton).padTop(20.0f).padBottom(20.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fade out then go to settings screen
                stage.addAction(Actions.sequence(Actions.fadeOut(0.75f), Actions.run(() -> game.setScreen(ScreenType.SETTINGS))));
            }
        });

        table.row().expandX().expandY();
        textButton = new TextButton("Quit to Menu", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Fade out then exit application
                stage.addAction(Actions.sequence(Actions.fadeOut(0.75f), Actions.run(() -> game.setScreen(ScreenType.MENU))));
            }
        });
        table.add(textButton).padTop(20.0f).padBottom(80.0f).padLeft(140.0f).padRight(140.0f).expandX().expandY().fill();

        table.setVisible(false);
        stage.addActor(table);

        isOpening = false;
        isClosing = false;
    }

    public void show() {
        table.setVisible(true);
        table.addAction(Actions.sequence(Actions.run(() -> setOpening(true)), Actions.fadeIn(0.25f), Actions.run(() -> setOpening(false))));
    }

    public void hide() {
        table.addAction(Actions.sequence(Actions.run(() -> setClosing(true)), Actions.fadeOut(0.25f), Actions.run(() -> setClosing(false))));
        table.setVisible(false);
    }

    private void setClosing(boolean isClosing) {
        this.isClosing = isClosing;
    }

    public boolean isOpening() {
        return isOpening;
    }

    public void setOpening(boolean isOpening) {
        this.isOpening = isOpening;
    }

    public boolean isClosing() {
        return isClosing;
    }
}
