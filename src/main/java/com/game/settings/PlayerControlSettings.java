package com.game.settings;

public class PlayerControlSettings {
    public final int MOVE_FORWARD;
    public final int MOVE_BACKWARD;    
    public final int MOVE_RIGHT;
    public final int MOVE_LEFT;
    public final int SERVE;

    public PlayerControlSettings(
        int moveForward,
        int moveBackward,
        int moveRight,
        int moveLeft,
        int serve
    )
    {
        MOVE_FORWARD = moveForward;
        MOVE_BACKWARD = moveBackward;
        MOVE_RIGHT = moveRight;
        MOVE_LEFT = moveLeft;
        SERVE = serve;
    }
}