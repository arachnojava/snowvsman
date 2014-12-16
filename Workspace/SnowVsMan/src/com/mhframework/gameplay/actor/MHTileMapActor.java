package com.mhframework.gameplay.actor;

import com.mhframework.core.math.MHVector;
import com.mhframework.gameplay.tilemap.MHITileMapContent;
import com.mhframework.gameplay.tilemap.view.MHCamera2D;
import com.mhframework.gameplay.tilemap.view.MHIsoMouseMap;
import com.mhframework.platform.graphics.MHBitmapImage;
import com.mhframework.platform.graphics.MHGraphicsCanvas;

public class MHTileMapActor extends MHActor implements MHITileMapContent
{
    private int tileID;
    
    @Override
    public int getTileID()
    {
        return tileID;
    }

    @Override
    public MHBitmapImage getImage()
    {
        return super.getImage();
    }

    public void setTileID(int id)
    {
       tileID = id;
    }

	@Override
	public void render(MHGraphicsCanvas g, MHCamera2D camera) 
	{
		MHVector v = camera.worldToScreen(getPosition().clone());
		v = camera.screenToLocal(v);
		int rx = (int)(v.x-(getWidth()/2));
		int ry = (int)(v.y-(getHeight()-MHIsoMouseMap.getInstance().getHeight()/2));
		g.drawImage(getImage(), rx, ry);
	}

	
//	public MHVector getRenderAnchor()
//	{
//		MHVector p = getPosition().clone();
//		p.x += getWidth()/2;
//		p.y += getHeight() - 16; // TODO: get this from the tile map system.
//		return p;
//	}

}
