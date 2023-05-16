package com.raginggoose.roguetrails.hud;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.inventory.Inventory;

public class HUD {
    private final HorizontalGroup group;
    private final Skin skin;

    public HUD(Inventory inventory, Skin skin) {
        this.skin = skin;
        group = new HorizontalGroup();
        group.space(10f);

        Label title = new Label("Inventory", skin);
        group.addActor(title);
        group.space(10f);

        for (int i = 0; i < 10; i++) {
            Entity itemEntity = inventory.getInventory().get(i);
            if (itemEntity != null) {
                String itemName = Mapper.ITEM_MAPPER.get(itemEntity).toString();
                Label itemLabel = new Label("Item " + (i + 1) + ": " + itemName, skin);
                itemLabel.setColor(Color.WHITE);
                group.addActor(itemLabel);
            } else {
                group.addActor(new Label("", skin));
            }
        }
    }

    public void setStage(Stage s) {
        s.addActor(group);
        group.setPosition(10f, 10f);
    }

    public void updateInventory(Inventory inventory) {
        for (int i = 0; i < 10; i++) {
            Entity itemEntity = inventory.getInventory().get(i);
            Label itemLabel = (Label) group.getChildren().get(i + 1); // first child is title label
            if (itemEntity != null) {
                String itemName = Mapper.ITEM_MAPPER.get(itemEntity).toString();
                itemLabel.setText("Item " + (i + 1) + ": " + itemName);
            } else {
                itemLabel.setText("");
            }
        }
    }
}
