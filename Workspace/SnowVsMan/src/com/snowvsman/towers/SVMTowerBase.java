package com.snowvsman.towers;

import com.mhframework.platform.MHPlatform;
import com.mhframework.platform.graphics.MHBitmapImage;
import com.mhframework.resources.MHResourceManager;

public class SVMTowerBase 
{
	private static MHBitmapImage images[] = null;
	private int towerClass;
	private double damageModifier;
	private double attackRateModifier;
	
	public SVMTowerBase()
	{
		damageModifier = 1.0;
		attackRateModifier = 1.0;
		towerClass = 1;
		
		if (images == null)
		{
			images = new MHBitmapImage[4];
			images[0] = MHResourceManager.getInstance().getImage(MHPlatform.getAssetsDirectory() + "images/Shadow.png");//MHPlatform.createImage(MHPlatform.getAssetsDirectory() + "images/Shadow.png");
			images[1] = MHResourceManager.getInstance().getImage(MHPlatform.getAssetsDirectory() + "images/Base1.png");//MHPlatform.createImage(MHPlatform.getAssetsDirectory() + "images/Base1.png");
		}
		
	}

	
	public void upgrade()
	{
		if (towerClass < 3)
			towerClass++;
		else
			towerClass = 3;
		
		switch(towerClass)
		{
		case 3:
			damageModifier = 2.0;
			attackRateModifier = 0.5;
			break;
		case 2:
			damageModifier = 1.5;
			attackRateModifier = 0.75;
			break;
		default:
			break;
		}
	}

	
	public int getTowerClass() {
		return towerClass;
	}


	public double getDamageModifier() {
		return damageModifier;
	}


	public double getAttackRateModifier() {
		return attackRateModifier;
	}

	
	public MHBitmapImage getShadowImage() 
	{
		return images[0];
	}


	public MHBitmapImage getImage() 
	{
		return images[towerClass];
	}
}
