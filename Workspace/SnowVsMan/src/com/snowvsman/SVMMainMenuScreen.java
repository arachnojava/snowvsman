package com.snowvsman;

import com.mhframework.MHGame;
import com.mhframework.MHScreen;
import com.mhframework.MHScreenManager;
import com.mhframework.core.math.MHVector;
import com.mhframework.platform.event.MHMouseTouchEvent;
import com.mhframework.platform.graphics.MHColor;
import com.mhframework.platform.graphics.MHFont;
import com.mhframework.platform.graphics.MHGraphicsCanvas;
import com.mhframework.ui.MHButton;
import com.mhframework.ui.event.MHButtonListener;

public class SVMMainMenuScreen extends MHScreen implements MHButtonListener
{
	public static String IMAGES_DIR;
	private MHButton btnPlay, btnExit;
	private static MHFont titleFont, buttonFont;

		
	@Override
	public void load() 
	{
		if (!isLoaded)
		{
			isLoaded = true;
			
			super.load();

			// Set up fonts.
			titleFont = getTitleFont();
			
			buttonFont = MHFont.getDefaultFont();
			buttonFont.setHeight(30);

			// Set up buttons.
			btnPlay = MHButton.create("Play");
			btnPlay.setSize(250, 50);
			btnPlay.setPosition(MHScreenManager.getDisplayWidth()/2 - btnPlay.getWidth()/2, 250);
			btnPlay.addButtonListener(this);
			btnPlay.setFont(buttonFont);
			add(btnPlay);

			btnExit = MHButton.create("Exit");
			btnExit.setSize(btnPlay.getWidth(), btnPlay.getHeight());
			btnExit.setPosition(btnPlay.getX(), btnPlay.getY() + 100);
			btnExit.addButtonListener(this);
			btnExit.setFont(buttonFont);
			add(btnExit);
		}
	}
	
	
	private MHFont getTitleFont()
	{
		if (titleFont == null)
		{
			titleFont = MHFont.getDefaultFont(); // TODO: Find a good font for this game.
			titleFont.setHeight(60);
		}
		
		return titleFont;
	}
	

	@Override
	public void update(long elapsedTime) 
	{
		// TODO Auto-generated method stub
		super.update(elapsedTime);
	}

	
	@Override
	public void render(MHGraphicsCanvas g) 
	{
		g.fill(MHColor.WHITE);
		g.setColor(MHColor.BLUE);
		g.drawRect(10, 10, MHScreenManager.getDisplayWidth()-20, MHScreenManager.getDisplayHeight()-20);
		
		g.setFont(getTitleFont());
		MHVector v = this.calculateCenterAnchor(g, "SNOW vs. MAN", titleFont);
		g.drawString("SNOW vs. MAN", (int)v.x, 100);
		
		super.render(g);
	}


	@Override
	public void onButtonPressed(MHButton button, MHMouseTouchEvent e) 
	{
		if (button == this.btnExit)
			MHGame.setProgramOver(true);
		else if (button == btnPlay)
		{
			SVMGameScreen.getInstance().loadMapFile("Level00.lime");
			MHScreenManager.getInstance().changeScreen(SVMGameScreen.getInstance());
		}
	}
	
	

}
