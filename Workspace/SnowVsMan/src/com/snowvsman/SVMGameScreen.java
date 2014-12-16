package com.snowvsman;

import com.mhframework.MHScreen;
import com.mhframework.MHScreenManager;
import com.mhframework.core.io.MHLogFile;
import com.mhframework.core.math.MHVector;
import com.mhframework.core.math.geom.MHRectangle;
import com.mhframework.gameplay.MHGameWorldData;
import com.mhframework.gameplay.actor.MHActorList;
import com.mhframework.gameplay.actor.MHTileMapActor;
import com.mhframework.gameplay.tilemap.MHLayersEnum;
import com.mhframework.gameplay.tilemap.MHMapCellAddress;
import com.mhframework.gameplay.tilemap.MHTileMapDirection;
import com.mhframework.gameplay.tilemap.io.MHWorldIO;
import com.mhframework.gameplay.tilemap.view.MHIsometricMapView;
import com.mhframework.gameplay.tilemap.view.MHTileMapView;
import com.mhframework.gameplay.tilemap.view.MHTileMapView.Type;
import com.mhframework.platform.MHPlatform;
import com.mhframework.platform.event.MHKeyEvent;
import com.mhframework.platform.event.MHMouseTouchEvent;
import com.mhframework.platform.graphics.MHGraphicsCanvas;
import com.snowvsman.characters.SVMSnowman;
import com.snowvsman.towers.SVMCampFire;
import com.snowvsman.towers.SVMSnowmanSpawner;
import com.snowvsman.towers.SVMTower;


public class SVMGameScreen extends MHScreen 
{
	private static MHLogFile logFile;
	private static SVMGameScreen instance;

	// DEBUG
	private final double SCROLL_SPEED = 0.25;
	private MHVector scroll;
	private String debugString = "";
	
	private MHTileMapView map;
	private MHGameWorldData mapData;

	private MHActorList actors = new MHActorList();
	SVMSnowmanSpawner snowmanSpawner;
	
	private SVMGameScreen()
	{
		
	}
	
	
	public static SVMGameScreen getInstance()
	{
		if (instance == null)
			instance = new SVMGameScreen();
		
		return instance;
	}
	
	
	@Override
	public void load() 
	{
		super.load();
		
		if (logFile == null)
		{
			logFile = new MHLogFile("SVMLogFile.txt");
			logFile.append(" ========  LOG FILE CREATED/OPENED  ========");
		}

		snowmanSpawner = new SVMSnowmanSpawner();
		int r = map.getMapData().getWorldHeight() / 2 + 4;
		int c = map.getMapData().getWorldWidth() / 10 - 1;
		map.putActor(snowmanSpawner, r, c);
		actors.add(snowmanSpawner);

		SVMCampFire fire = SVMCampFire.getInstance();
		map.putActor(fire, 6, 11);
		actors.add(fire);
		
		// DEBUG
		scroll = new MHVector();
	}
	
	
	public MHTileMapView getMap()
	{
		return map;
	}

	
	public void addActor(MHTileMapActor actor)
	{
		actors.add(actor);
	}
	
	
	public void loadMapFile(String mapFileName)
	{
		mapData = MHWorldIO.loadGameWorld(mapFileName);
		
		if (map == null)
		{
			map = MHIsometricMapView.create(Type.STAGGERED, mapData);
			
			// Eliminate "jaggies" from the staggered map.
			// TODO: Find a reasonable place to do this in the engine.
			MHRectangle screen = MHScreenManager.getViewRect();
			screen.x -= mapData.getTileWidth()/2;
			screen.y -= mapData.getTileHeight()/2;
			screen.width += mapData.getTileWidth();
			screen.height += mapData.getTileHeight();
			
			map.setScreenSpace(screen);
			
			// DEBUG
			//map.setGridOn(true);
			//map.setCursorOn(true);
		}
		else
			map.setMapData(mapData);
	}
	

	@Override
	public void update(long elapsedTime) 
	{
		super.update(elapsedTime);
		//map.update(elapsedTime);
		actors.update(elapsedTime);
		if (map != null && scroll != null)
			map.scrollMap(scroll.x*elapsedTime, scroll.y*elapsedTime);		
	}

	
	@Override
	public void render(MHGraphicsCanvas g) 
	{ 
		map.render(g); 
		
		super.render(g);
		
		// DEBUG:
//		g.setColor(MHColor.BLACK);
//		g.drawString("Cursor Address: " + map.getCursorAddress().toString(), 10, 30);
//		g.drawString(debugString, 10, 60);
	}

	
	@Override
	public void onMouseUp(MHMouseTouchEvent e) 
	{
		MHMapCellAddress cell = map.mapMouse(e.getPoint());
		//MHMapCellAddress cell = map.getCursorAddress();
		SVMTower tower = new SVMTower();
		
		// Can't build a tower in an occupied space.
		if (map.getMapData().isCollidable(cell.row, cell.column, tower))
			return;
		
		// Can't build a tower that blocks the path.
		MHMapCellAddress start = map.calculateGridLocation(snowmanSpawner);
		start = map.tileWalk(start.row, start.column, MHTileMapDirection.SOUTHEAST);
		MHMapCellAddress goal = map.calculateGridLocation(SVMCampFire.getInstance());
		goal = map.tileWalk(goal.row, goal.column, MHTileMapDirection.SOUTHWEST);
		
		if (!SVMSnowman.isValidPath(start, goal))
			return;
		
		map.putActor(tower, cell.row, cell.column);
		//map.putTile(tower, MHLayersEnum.WALLS, cell.row, cell.column);
		
		// DEBUG:
		//debugString = "Placed tower at " + cell.toString();
	}
	
	
	@Override
	public void onMouseMoved(MHMouseTouchEvent e) 
	{
		super.onMouseMoved(e);
		map.onMouseMoved(e);
	}


	// FOR TESTING.  DO NOT USE IN THIS GAME!
	@Override
	public void onKeyDown(MHKeyEvent e) 
	{
		if (e.getKeyCode() == MHPlatform.getKeyCodes().keyUpArrow())
			scroll.y = -SCROLL_SPEED;
		else if (e.getKeyCode() == MHPlatform.getKeyCodes().keyDownArrow())
			scroll.y = SCROLL_SPEED;

		if (e.getKeyCode() == MHPlatform.getKeyCodes().keyLeftArrow())
			scroll.x = -SCROLL_SPEED;
		else if (e.getKeyCode() == MHPlatform.getKeyCodes().keyRightArrow())
			scroll.x = SCROLL_SPEED;

		super.onKeyDown(e);
	}


	// FOR TESTING.  DO NOT USE IN THIS GAME!
	@Override
	public void onKeyUp(MHKeyEvent e) 
	{
		if (e.getKeyCode() == MHPlatform.getKeyCodes().keyUpArrow() ||
			e.getKeyCode() == MHPlatform.getKeyCodes().keyDownArrow())
			scroll.y = 0;

		if (e.getKeyCode() == MHPlatform.getKeyCodes().keyLeftArrow() ||
			e.getKeyCode() == MHPlatform.getKeyCodes().keyRightArrow())
			scroll.x = 0;
		
		super.onKeyUp(e);
	}	
}
