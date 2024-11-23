package com.game.settings;

public class PlayerSettings {
    public final int MOVE_FORWARD;
    public final int MOVE_BACKWARD;    
    public final int MOVE_RIGHT;
    public final int MOVE_LEFT;
    public final int SERVE;
    public final int SERVE_DIRECTION;

    public PlayerSettings(
        int moveForward,
        int moveBackward,
        int moveRight,
        int moveLeft,
        int serve,
        int serveDirection
    )
    {
        MOVE_FORWARD = moveForward;
        MOVE_BACKWARD = moveBackward;
        MOVE_RIGHT = moveRight;
        MOVE_LEFT = moveLeft;
        SERVE = serve;
        SERVE_DIRECTION = serveDirection;
    }
}