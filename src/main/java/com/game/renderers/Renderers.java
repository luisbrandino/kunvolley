package com.game.renderers;

import java.util.HashMap;

import com.game.contracts.IRenderer;

public class Renderers {
    private static HashMap<String, IRenderer> _renderers = new HashMap<String, IRenderer>();

    public static void addRenderer(String rendererName, IRenderer renderer)
    {
        if (rendererName == null || renderer == null) {
            throw new IllegalArgumentException("Renderers.addRenderer: Renderer name and renderer cannot be null");
        }

        _renderers.put(rendererName, renderer);
    }

    public static IRenderer getRenderer(String rendererName) {
        IRenderer renderer = _renderers.get(rendererName);

        if (renderer == null)
            throw new Error("Renderers.getRenderer : Renderer not found");

        return renderer;
    }
}