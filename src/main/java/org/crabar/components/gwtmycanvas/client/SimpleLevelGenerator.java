package org.crabar.components.gwtmycanvas.client;

import org.crabar.components.gwtmycanvas.client.gameObjects.Brick;

import java.util.ArrayList;

/**
 * Created by ypoliakov on 20.11.2014.
 */
public class SimpleLevelGenerator {
    public SimpleLevelGenerator() {
    }

    public ArrayList<Brick> generateLevel(int levelX, int levelY, int levelWidth, int levelHeight) {
        final int brickWidth = 70;
        final int brickHeight = 30;
        final int gap = 1;

        ArrayList<Brick> bricks = new ArrayList<Brick>();

        int horBrickCount = Math.round(levelWidth / (brickWidth + gap));
        int vertBrickCount = Math.round(levelHeight / (brickHeight + gap));

        int xOffset = Math.round((levelWidth - ((horBrickCount + gap) * brickWidth - gap)) / 2);
        int yOffset = Math.round((levelHeight - ((vertBrickCount + gap) * brickHeight - gap)) / 2);

        Brick brick;

        for (int i = 0; i < horBrickCount; i++) {
            for (int j = 0; j < vertBrickCount; j++) {
                brick = new Brick(levelX + xOffset + (brickWidth + gap) * i, levelY + yOffset + (brickHeight + gap) * j);
                bricks.add(brick);
            }
        }

        return bricks;
    }
}
