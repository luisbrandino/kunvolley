package com.game.graphics;

import java.awt.image.BufferedImage;

public class PlayerAnimation extends Animation {
    
    public PlayerAnimation(long frameDelay)
    {
        super(frameDelay);

        Spritesheet firstSpritesheet = new Spritesheet("images/character-sprite.png", 460, 590); 
        Spritesheet secondSpritesheet = new Spritesheet("images/character-sprite.png", 460, 600);

        addAnimation("idle_front", new BufferedImage[] {
            firstSpritesheet.getTile(1, 0)
        });

        addAnimation("walk_front", new BufferedImage[] {
            firstSpritesheet.getTile(0, 0), firstSpritesheet.getTile(1, 0), firstSpritesheet.getTile(2, 0), firstSpritesheet.getTile(3, 0)
        });
    
        addAnimation("walk_right", new BufferedImage[] {
            firstSpritesheet.getTile(0, 1), firstSpritesheet.getTile(1, 1), firstSpritesheet.getTile(2, 1), firstSpritesheet.getTile(3, 1)
        });

        addAnimation("idle_right", new BufferedImage[] {
            firstSpritesheet.getTile(1, 1)
        });
    
        addAnimation("walk_left", new BufferedImage[] {
            secondSpritesheet.getTile(0, 2), secondSpritesheet.getTile(1, 2), secondSpritesheet.getTile(2, 2), secondSpritesheet.getTile(3, 2)
        });

        addAnimation("idle_left", new BufferedImage[] {
            firstSpritesheet.getTile(0, 2)
        });
    
        addAnimation("idle_back", new BufferedImage[] {
            secondSpritesheet.getTile(1, 3)
        });

        addAnimation("walk_back", new BufferedImage[] {
            secondSpritesheet.getTile(0, 3), secondSpritesheet.getTile(1, 3), secondSpritesheet.getTile(2, 3), secondSpritesheet.getTile(3, 3)
        });
    }

}
