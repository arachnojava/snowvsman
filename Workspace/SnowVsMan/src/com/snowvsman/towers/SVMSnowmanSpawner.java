package com.snowvsman.towers;

import com.mhframework.MHGame;
import com.mhframework.gameplay.actor.MHTileMapActor;
import com.mhframework.gameplay.tilemap.MHMapCellAddress;
import com.mhframework.gameplay.tilemap.view.MHTileMapView;
import com.mhframework.platform.MHPlatform;
import com.snowvsman.SVMGameScreen;
import com.snowvsman.characters.SVMSnowman;

public class SVMSnowmanSpawner extends MHTileMapActor 
{
	private long lastSpawnTime = MHGame.getGameTimerValue();
	public SVMSnowmanSpawner()
	{
		setImage(MHPlatform.createImage(MHPlatform.getAssetsDirectory() + "images/SnowBrickBlock.png"));
	}

	
	boolean s = false;
	@Override
	public void update(long elapsedTime) 
	{
		//if (!s)
		if (MHGame.getGameTimerValue() - lastSpawnTime > 10000)
		{
			s = true;
			MHTileMapView map = SVMGameScreen.getInstance().getMap();
			MHMapCellAddress gridSpace = map.calculateGridLocation(this);

			// Spawn a snowman.
			SVMSnowman snowman = new SVMSnowman();
			map.putActor(snowman, gridSpace.row+1, gridSpace.column+1);
			SVMGameScreen.getInstance().addActor(snowman);
			
			lastSpawnTime = MHGame.getGameTimerValue();
		}
		
		super.update(elapsedTime);
	}

	
	
	
}
