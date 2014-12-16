package com.snowvsman.characters;

import java.util.ArrayList;

import com.mhframework.ai.path.MHNodePath;
import com.mhframework.ai.path.MHPathFinder;
import com.mhframework.ai.steering.MHSteeringForces;
import com.mhframework.core.math.MHRandom;
import com.mhframework.core.math.MHVector;
import com.mhframework.gameplay.actor.MHTileMapActor;
import com.mhframework.gameplay.tilemap.MHMapCellAddress;
import com.mhframework.gameplay.tilemap.MHTileMapDirection;
import com.mhframework.gameplay.tilemap.view.MHCamera2D;
import com.mhframework.gameplay.tilemap.view.MHTileMapView;
import com.mhframework.gameplay.tilemap.view.MHTilePlotter;
import com.mhframework.platform.MHPlatform;
import com.mhframework.platform.graphics.MHBitmapImage;
import com.mhframework.platform.graphics.MHGraphicsCanvas;
import com.snowvsman.SVMGameScreen;
import com.snowvsman.towers.SVMCampFire;

public class SVMSnowman extends MHTileMapActor 
{
	private static final MHTileMapDirection[] directions = 
	{
		MHTileMapDirection.NORTHEAST,
		MHTileMapDirection.SOUTHEAST,
		MHTileMapDirection.SOUTHWEST,
		MHTileMapDirection.NORTHWEST
	};
	private MHNodePath path;
	private int currentNode;
	
	// DEBUG: Let's try smoothing the steering forces by averaging them.
	ArrayList<MHVector> forces = new ArrayList<MHVector>();
	
	public SVMSnowman()
	{
		MHBitmapImage img = MHPlatform.createImage(MHPlatform.getAssetsDirectory() + "images/Snowman.png");
		setImage(img);
		this.setMaxSpeed(0.075);
	}

	
	@Override
	public void update(long elapsedTime) 
	{
		MHTileMapView map = SVMGameScreen.getInstance().getMap();

		map.removeActor(this);
		
		if (path == null || currentNode >= path.size())
		{
			path = getPath();
			currentNode = 0;
		}
		else
		{			
			MHMapCellAddress node = path.get(currentNode);
			
			MHVector target = MHTilePlotter.getInstance().plotTile(node.row, node.column);
			target.x += MHTilePlotter.getInstance().getTileWidth()/2;
			target.y += MHTilePlotter.getInstance().getTileHeight()/2;
			
			MHVector force = MHSteeringForces.seek(this, target);
			force = force.add(MHSteeringForces.avoidWalls(this, map));
			
			setVelocity(getVelocity().add(force));
			
			if (this.getPosition().distance(target) < map.getTileHeight()/2)
			{
				currentNode++;
				node = path.get(currentNode);
				if (node == null)
				{
					// TODO: Change to dying state.
					this.destroy();
					
					SVMCampFire.getInstance().subtractHealth(10.0);
				}
				else if (map.getMapData().isCollidable(node.row, node.column, this))
				{
					path = MHPathFinder.aStarSearch(path.get(currentNode-1), path.get(path.size()-1), map, directions);
					currentNode = 0;
				}
			}
			else
			{
				MHVector oldPos = this.getPosition().clone();
				MHVector newPos = this.getPosition().add(getVelocity().multiply(elapsedTime));
				setPosition(newPos);
				
				MHMapCellAddress a = map.calculateGridLocation(this);
				if (map.getMapData().isCollidable(a.row, a.column, this))
					setPosition(oldPos);
			}
		}
		
		if (!isDestroyed())
			map.reinsertActor(this);
	}

	
	
	private MHNodePath getPath()
	{
		MHTileMapView map = SVMGameScreen.getInstance().getMap();
		MHMapCellAddress startLoc = map.calculateGridLocation(this);
		MHMapCellAddress fireLoc = SVMCampFire.getInstance().getGridLocation();
		MHMapCellAddress goalLoc;
		MHNodePath path = null;
		
		
		do
		{
			switch (MHRandom.rollD4())
			{
			case 1:  goalLoc = map.tileWalk(fireLoc.row, fireLoc.column, MHTileMapDirection.NORTHEAST);
			break;
			case 2:  goalLoc = map.tileWalk(fireLoc.row, fireLoc.column, MHTileMapDirection.SOUTHEAST);
			break;
			case 3:  goalLoc = map.tileWalk(fireLoc.row, fireLoc.column, MHTileMapDirection.NORTHWEST);
			break;
			default: goalLoc = map.tileWalk(fireLoc.row, fireLoc.column, MHTileMapDirection.SOUTHWEST);
			break;			        
			}
		
			path = MHPathFinder.aStarSearch(startLoc, goalLoc, map, directions);
		} while (path == null);
		
		return path;
	}
	
	
	public static boolean isValidPath(MHMapCellAddress start, MHMapCellAddress goal)
	{
		MHTileMapView map = SVMGameScreen.getInstance().getMap();
		return (null != MHPathFinder.aStarSearch(start, goal, map, directions));
	}
	

	@Override
	public void render(MHGraphicsCanvas g, MHCamera2D camera) 
	{
		// DEBUG:  Draw the path.
//		if (path != null)
//		{
//			for (int i = 0; i < path.size(); i++)
//			{
//				MHVector v = MHTilePlotter.getInstance().plotTile(path.get(i));
//				v = camera.worldToScreen(v);
//				v = camera.screenToLocal(v);
//				g.setColor(MHColor.MAGENTA);
//				g.drawRect((int)v.x, (int)v.y, 64, 32);
//			}
//		}
		
		// DEBUG: Render visual indicator of what space the snowman is in.
//		MHVector v = MHTilePlotter.getInstance().plotTile(SVMGameScreen.getInstance().getMap().calculateGridLocation(this));
//		v = camera.worldToScreen(v);
//		v = camera.screenToLocal(v);
//		g.setColor(MHColor.RED);
//		g.drawRect((int)v.x, (int)v.y, 64, 32);

		super.render(g, camera);
	}
}
