package com.rgd.roguetrails.dungeon;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.rgd.roguetrails.b2d.BodyFactory;
import com.rgd.roguetrails.utils.Constants;

public class Hallway {
    private final boolean first;
    private final float x;
    private final float y;
    private final float width;
    private final float length;
    private final BodyFactory bodyFactory;
    private final int direction;

    public Hallway(float x, float y, float width, float length, World world, int direction, boolean first) {
        this.first = first;
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
        this.direction = direction;

        bodyFactory = BodyFactory.getInstance(world);

        createHallwayBody();
    }

    public void createHallwayBody() {
        float[] top_points;
        float[] bottom_points;
        // Initial hallway; Make it a dead end on the left
        if (first) {
            // x1, y1, x2, y2, ...
            top_points = new float[]{x, y, x, y + width, x + length, y + width, x + length, y + width - (width / 4f)};
            bottom_points = new float[]{x, y, x + length, y, x + length, y + width / 4f};


        } else if (direction == Constants.HORIZONTAL_HALL) {
            top_points = new float[]{x, y + (width / 4f * 3f), x, y + width, x + length, y + width, x + length, y + (width / 4f * 3f)};
            bottom_points = new float[]{x, y + width / 4f, x, y, x + length, y, x + length, y + width / 4f};

        } else {
            // Top is West...
            top_points = new float[]{x + width / 4f, y + length, x, y + length, x, y, x + width / 4f, y};
            bottom_points = new float[]{x + (width /4f * 3f), y + length, x + width, y + length, x + width, y, x + (width /4f * 3f), y};
        }

        bodyFactory.makePolyline(x, y, top_points, BodyDef.BodyType.StaticBody);
        bodyFactory.makePolyline(x, y, bottom_points, BodyDef.BodyType.StaticBody);
    }

}
