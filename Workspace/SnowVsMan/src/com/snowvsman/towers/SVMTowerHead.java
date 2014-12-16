package com.snowvsman.towers;

import com.mhframework.platform.MHPlatform;
import com.mhframework.platform.graphics.MHBitmapImage;

public class SVMTowerHead 
{
	private double attackRate;
	private double damage;
	private MHBitmapImage image;
	private int level;
	private double experience;
	
	
	public SVMTowerHead()
	{
		image = MHPlatform.createImage(MHPlatform.getAssetsDirectory() + "images/LaserTurret.png");
	}


	public double getAttackRate() {
		return attackRate;
	}


	public void setAttackRate(double attackRate) {
		this.attackRate = attackRate;
	}


	public double getDamage() {
		return damage;
	}


	public void setDamage(double damage) {
		this.damage = damage;
	}


	public MHBitmapImage getImage() 
	{
		return image;
	}


	public void setImage(MHBitmapImage image) {
		this.image = image;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public double getExperience() {
		return experience;
	}


	public void setExperience(double experience) {
		this.experience = experience;
	}
}
