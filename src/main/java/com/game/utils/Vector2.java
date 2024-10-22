package com.game.utils;

public final class Vector2 {
    public double x = 0;
    public double y = 0;

    public Vector2() {
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(int x, int y) {
        this.x = (double) x;
        this.y = (double) y;
    }
}
