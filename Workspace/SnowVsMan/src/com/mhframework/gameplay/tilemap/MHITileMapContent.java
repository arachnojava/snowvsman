package com.mhframework.gameplay.tilemap;

import com.mhframework.gameplay.tilemap.view.MHCamera2D;
import com.mhframework.platform.graphics.MHBitmapImage;
import com.mhframework.platform.graphics.MHGraphicsCanvas;

public interface MHITileMapContent
{
    public int getTileID();
    public MHBitmapImage getImage();
    public void render(MHGraphicsCanvas g, int screenX, int screenY);
    public void render(MHGraphicsCanvas g, MHCamera2D camera);
}
