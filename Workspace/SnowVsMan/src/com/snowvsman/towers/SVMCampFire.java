package com.snowvsman.towers;

import com.mhframework.core.math.MHVector;
import com.mhframework.gameplay.actor.MHAnimationSequence;
import com.mhframework.gameplay.actor.MHAnimator;
import com.mhframework.gameplay.actor.MHTileMapActor;
import com.mhframework.gameplay.tilemap.MHMapCellAddress;
import com.mhframework.gameplay.tilemap.view.MHCamera2D;
import com.mhframework.gameplay.tilemap.view.MHTileMapView;
import com.mhframework.gameplay.tilemap.view.MHTilePlotter;
import com.mhframework.gameplay.tilemap.view.MHTileWalker;
import com.mhframework.platform.MHPlatform;
import com.mhframework.platform.graphics.MHGraphicsCanvas;
import com.mhframework.resources.MHResourceManager;
import com.snowvsman.SVMGameScreen;

public class SVMCampFire extends MHTileMapActor 
{
	private static final long FRAME_DURATION = 1000/8L;
	
	private static SVMCampFire instance;
	private MHAnimationSequence sequence;
	private MHAnimator animator;
	
	private static final double MAX_HEALTH = 100.0;
	private double health = MAX_HEALTH;
	
	private SVMCampFire() 
	{
		sequence = new MHAnimationSequence();
		
		for (int i = 0; i < 5; i++)
			sequence.addFrame(MHPlatform.getAssetsDirectory() + "images/CampFire0"+i+".png", FRAME_DURATION);
		
		animator = new MHAnimator(sequence);
		setImage(MHResourceManager.getInstance().getImage(animator.getImageID()));
	}

	
	public static SVMCampFire getInstance()
	{
		if (instance == null)
			instance = new SVMCampFire();
		
		return instance;
	}
	
	
	public void subtractHealth(double amount)
	{
		if (amount < health)
			health -= amount;
		else
			health = 0;
	}

	
	@Override
	public void render(MHGraphicsCanvas g, MHCamera2D camera) 
	{
		if (health > 0)
			super.render(g, camera);

		// Show fire "health".
		int hp = (int)((health/MAX_HEALTH) * 100);
		MHVector v = this.position.subtract(-10, -32);//SVMGameScreen.getInstance().getMap().calculateBasePoint(this);
		v = camera.worldToScreen(v);
		g.drawString(hp+"%", (int)v.x, (int)v.y);
	}


	@Override
	public void update(long elapsedTime) 
	{
		animator.update(elapsedTime, null);
		setImage(MHResourceManager.getInstance().getImage(animator.getImageID()));
	}

	
	public MHMapCellAddress getGridLocation()
	{
		MHTileMapView map = SVMGameScreen.getInstance().getMap();
		return map.calculateGridLocation(this);
	}
	
}
