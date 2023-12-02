package com.raginggoose.roguetrails.hud;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.inventory.Inventory;

/**
 * The heads-up display which overlays the game
 */
public class HUD {
    private final HorizontalGroup healthGroup;
    private final HorizontalGroup inventoryGroup;
    private final Skin skin;
    private final Stage stage;

    /**
     * Creates a new HUD that will be rendered over the game
     * @param inventory the players inventory
     * @param skin the skin used for the scene2D objects
     * @param hp the player's health
     * @param stage the stage that the HUD acts/renders on
     */
    public HUD(Inventory inventory, Skin skin, float hp, Stage stage) {
        this.skin = skin;
        this.stage = stage;
        inventoryGroup = new HorizontalGroup();
        inventoryGroup.space(10f);

        Label title = new Label("Inventory", skin);
        inventoryGroup.addActor(title);
        inventoryGroup.space(10f);

        for (int i = 0; i < 10; i++) {
            Entity itemEntity = inventory.getInventory().get(i);
            if (itemEntity != null) {
                String itemName = Mapper.ITEM_MAPPER.get(itemEntity).toString();
                Label itemLabel = new Label("Item " + (i + 1) + ": " + itemName, skin);
                itemLabel.setColor(Color.WHITE);
                inventoryGroup.addActor(itemLabel);
            } else {
                inventoryGroup.addActor(new Label("", skin));
            }
        }

        healthGroup = new HorizontalGroup();
        healthGroup.space(10f);
        Label health = new Label("Health", skin);
        healthGroup.addActor(health);

        for (int i = 0; i < hp; i++) {
            Label hpLabel = new Label("<3", skin);
            hpLabel.setColor(Color.RED);
            healthGroup.addActor(hpLabel);
        }

        stage.addActor(inventoryGroup);
        inventoryGroup.setPosition(10f, 10f);

        stage.addActor(healthGroup);
        healthGroup.setPosition(10f, stage.getHeight() - healthGroup.getHeight() - 10f);
    }

    public void setStage(Stage s) {

    }

    public void updateInventory(Inventory inventory) {
        for (int i = 0; i < 10; i++) {
            Entity itemEntity = inventory.getInventory().get(i);
            Label itemLabel = (Label) inventoryGroup.getChildren().get(i + 1); // first child is title label
            if (itemEntity != null) {
                String itemName = Mapper.ITEM_MAPPER.get(itemEntity).toString();
                itemLabel.setText("Item " + (i + 1) + ": " + itemName);
            } else {
                itemLabel.setText("");
            }
        }
    }

    public void updateHealth(float hp) {
        healthGroup.clearChildren();

        Label health = new Label("Health", skin);
        healthGroup.addActor(health);

        for (int i = 0; i < hp; i++) {
            Label hpLabel = new Label("<3", skin);
            hpLabel.setColor(Color.RED);
            healthGroup.addActor(hpLabel);
        }
    }

    public void resize(int width, int height) {
        inventoryGroup.setPosition(10f, 10f);
        healthGroup.setPosition(10f, height - healthGroup.getHeight() - 10f);
    }
}
