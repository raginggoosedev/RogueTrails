package com.raginggoose.roguetrails.inventory;

import com.badlogic.ashley.core.Entity;
import com.raginggoose.roguetrails.ecs.Mapper;
import com.raginggoose.roguetrails.ecs.components.ItemComponent;

import java.util.HashMap;

public class Inventory {
    private final HashMap<Integer, Entity> items;

    public Inventory() {
        items = new HashMap<>();
    }

    public void addItem(Entity item) {
        items.put(getInventory().size(), item);
    }

    public int getInventorySize() {
        return items.size();
    }

    public HashMap<Integer, Entity> getInventory() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("-------- Player Inventory --------\n");
        for(int i = 0; i < items.size(); i++) {
            Entity e = items.get(i);
            ItemComponent itemComponent = Mapper.ITEM_MAPPER.get(e);
            stringBuilder.append("[ ").append(i).append(" ] : ").append(itemComponent).append("\n");
        }

        return stringBuilder.toString();
    }
}
