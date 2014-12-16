package com.mhframework.gameplay;

import com.mhframework.MHGlobalConstants;
import com.mhframework.gameplay.actor.MHActor;
import com.mhframework.gameplay.actor.MHTileMapActor;
import com.mhframework.gameplay.tilemap.MHITileMapContent;
import com.mhframework.gameplay.tilemap.MHLayersEnum;
import com.mhframework.gameplay.tilemap.MHTileGrid;
import com.mhframework.gameplay.tilemap.MHTileSet;
import com.mhframework.gameplay.tilemap.io.MHMapFileInfo;
import com.mhframework.resources.MHResourceManager;


public class MHGameWorldData 
{
	private String mapName;

	protected String[] layerFileNames;
	protected MHTileGrid[] layers;

	private String tileSetID;


	public MHGameWorldData() 
	{
		layers = new MHTileGrid[getNumLayers()];
        layerFileNames = new String[getNumLayers()];
	}


	public boolean isCollidable(int row, int column, MHActor actor) 
	{
		// Do a domain check to see if values are in range.
		if (row < 0 || column < 0 || row >= getWorldHeight() || column >= getWorldWidth())
			return true;
		
		// Check collidable layers for obstructions.
		if (layers[MHLayersEnum.WALLS.getID()].getTile(row, column).getTileID() != MHTileSet.NULL_TILE_ID)
			return true;
		
		// FIXME:  Actors should compare against each other to determine collisions, so this check is inadequate by itself.
		MHITileMapContent mapActor = layers[MHLayersEnum.ACTORS.getID()].getTile(row, column);
		if (mapActor != null && mapActor.getTileID() != MHTileSet.NULL_TILE_ID && actor != mapActor)
			return true;

		return false;
	}
	
	
	public void putActor(MHTileMapActor actor, int row, int col)
	{
		if (row >= 0 && row < this.getWorldHeight() && col >= 0 && col < this.getWorldWidth())
			layers[MHLayersEnum.ACTORS.getID()].getDataGrid()[row][col] = actor;
	}

	
	// FIXME:  Should this return a tile map actor?  I'm really not sure.
	public MHITileMapContent getActor(int row, int col)
	{
		if (row >= 0 && row < this.getWorldHeight() &&
			col >= 0 && col < this.getWorldWidth())
			return layers[MHLayersEnum.ACTORS.getID()].getDataGrid()[row][col];
		
		return null;
	}

	
	public void putTile(MHITileMapContent tile, MHLayersEnum layer, int row, int col)
	{
		layers[layer.getID()].getDataGrid()[row][col] = tile;
	}
	
	
	public MHITileMapContent getTile(int layer, int row, int col)
	{
		return layers[layer].getDataGrid()[row][col];
	}

	
	public int getWorldHeight() 
	{
		return layers[0].getNumRows();
	}

	
	public int getWorldWidth() 
	{
		return layers[0].getNumColumns();
	}
	
	
	public int getNumLayers()
	{
		return MHLayersEnum.values().length;
	}
	

	private MHTileGrid getLayer(int layerID) 
	{
		// Validate parameter.
		if (layerID >= 0 && layerID < getNumLayers())
			return layers[layerID];
		
		return null;
	}
	
	
	public String getLayerFileName(MHLayersEnum layer) 
	{
		return getLayerFileName(layer.getID());
	}

	
	public String getLayerFileName(int layerID) 
	{
	    // Validate parameter.
	    if (layerID >= 0 && layerID < getNumLayers())
	    {
//	        if (layerFileNames[layerID] == null)
//	        {
//	            layerFileNames[layerID] = mapFileName+MHLayersEnum.values()[layerID]+MHMapFileInfo.TILE_MAP_FILE_EXTENSION;
//	        }
	        return layerFileNames[layerID];
	    }
	    
	    return null;
	}


	public MHTileGrid getLayer(MHLayersEnum layer) 
	{
	    return getLayer(layer.getID());
	}

	
	public void setTileGrid(int layerID, MHTileGrid tilemap, String name) 
	{
		layers[layerID] = tilemap;
		layerFileNames[layerID] = name;
	}

	
	public void setTileGrid(MHLayersEnum layer, MHTileGrid tileMap, String name) 
	{
		setTileGrid(layer.getID(), tileMap, name);
	}


	public void setTileSetID(String tileSetId) 
	{
		this.tileSetID = tileSetId;
	}
	
	
	public String getTileSetID()
	{
		return tileSetID;
	}


	public int getTileWidth() 
	{
	    return MHResourceManager.getInstance().getTileSet(tileSetID).getBaseTileWidth();
	}


	public int getTileHeight() 
	{
        return MHResourceManager.getInstance().getTileSet(tileSetID).getBaseTileHeight();
	}


	public void setMapName(String name) 
	{
		mapName = name;
	}


	public String getMapFileName() 
	{
	    String filename = mapName;
	    filename = validateFilePath(filename);
	    filename = validateMapFileExtension(filename);
		return filename;
	}

	
	private String validateFilePath(String filename)
	{
	    if (!filename.startsWith(MHGlobalConstants.DIR_DATA+"/"))
	        filename = MHGlobalConstants.DIR_DATA + "/" + filename;

	    return filename;
	}

	
    private String validateMapFileExtension(String filename)
    {
        if (!filename.endsWith(MHMapFileInfo.WORLD_FILE_EXTENSION))
            filename += MHMapFileInfo.WORLD_FILE_EXTENSION;
        
        return filename;
    }


	/**
	 * <ol>
     *   <li>Map name : String</li>
     *   <li>Map rows : int</li>
     *   <li>Map columns : int</li>
     *   <li>Tile width : int</li>
     *   <li>Tile height : int</li>
     *   <li>Tile set ID : String</li>
     *   <li>Floor map file name : String</li>
     *   <li>Floor details file name : String</li>
     *   <li>Wall map file name : String</li>
     *   <li>Wall details file name : String</li>
     *   <li>Actor list file name : String</li>
     * </ol>
     */
    @Override   
    public String toString()
    {
        StringBuffer data = new StringBuffer();
        data.append(mapName + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getWorldHeight() + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getWorldWidth() + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getTileWidth() + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getTileHeight() + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getTileSetID() + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getLayerFileName(MHLayersEnum.FLOOR) + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getLayerFileName(MHLayersEnum.FLOOR_DECALS) + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getLayerFileName(MHLayersEnum.WALLS) + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getLayerFileName(MHLayersEnum.WALL_DECALS) + MHMapFileInfo.MAP_FILE_DELIMITER);
        data.append(getLayerFileName(MHLayersEnum.ACTORS) + "\n");
        
        return data.toString();
    }


    public void setLayerFileName(MHLayersEnum layer, String filename)
    {
        layerFileNames[layer.getID()] = filename;
    }


    public String getMapName()
    {
        return mapName;
    }


}
