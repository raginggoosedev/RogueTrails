package com.raginggoose.roguetrails.inventory;

import com.badlogic.ashley.core.Entity;

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
}
