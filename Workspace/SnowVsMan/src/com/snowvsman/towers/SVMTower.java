package com.snowvsman.towers;

import com.mhframework.gameplay.actor.MHTileMapActor;
import com.mhframework.gameplay.tilemap.view.MHCamera2D;
import com.mhframework.platform.MHPlatform;
import com.mhframework.platform.graphics.MHBitmapImage;
import com.mhframework.platform.graphics.MHGraphicsCanvas;

public class SVMTower extends MHTileMapActor 
{
	private SVMTowerBase base;
	private SVMTowerHead head;
	private MHBitmapImage image;
	
	public SVMTower() 
	{
		base = new SVMTowerBase();
		head = new SVMTowerHead();
	}

	
	@Override
	public int getWidth() 
	{
		return Math.max(base.getImage().getWidth(), head.getImage().getWidth());
	}


	@Override
	public int getHeight() 
	{
		return Math.max(base.getImage().getHeight(), head.getImage().getHeight());
	}





	@Override
	public MHBitmapImage getImage() 
	{
		if (image == null)
		{
			image = MHPlatform.createImage(base.getImage().getWidth(), base.getImage().getHeight());
			image.getGraphicsCanvas().drawImage(base.getShadowImage(), 0, base.getImage().getHeight()-32);
			image.getGraphicsCanvas().drawImage(base.getImage(), 0, 0);
			image.getGraphicsCanvas().drawImage(head.getImage(), 0, 0);
		}
		
		return image;
	}


	@Override
	public void render(MHGraphicsCanvas g, MHCamera2D camera) 
	{
		image = getImage();
		
		// TODO Auto-generated method stub
		super.render(g, camera);
	}

	
	
	
	
}
