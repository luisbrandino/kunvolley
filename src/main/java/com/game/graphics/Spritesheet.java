package com.game.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.game.Main;

public final class Spritesheet {
    private BufferedImage spritesheet;
    private int tileWidth;
    private int tileHeight;

    public Spritesheet(String path, int tileWidth, int tileHeight) {
        try {
            this.spritesheet = ImageIO.read(Main.class.getResourceAsStream(path));
        } catch (Exception e)
        {
            throw new Error(e.getMessage());
        }

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public BufferedImage getTile(int x, int y) {
        return spritesheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }
}
