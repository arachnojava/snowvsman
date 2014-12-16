package com.mhframework.ai.path;

import com.mhframework.gameplay.tilemap.MHMapCellAddress;
import com.mhframework.gameplay.tilemap.MHTileMapDirection;
import com.mhframework.gameplay.tilemap.view.MHRectangularMapView;
import com.mhframework.gameplay.tilemap.view.MHTileMapView;

public class MHNode extends MHMapCellAddress
{
    private static final long serialVersionUID = 1L;
    private double   costFromStart;
    private double   costToGoal;

    private MHNode parent;

    public MHNode(MHMapCellAddress p)
    {
        this.row = p.row;
        this.column = p.column;
        parent = null;
        costFromStart = 0.0;
    }


    public void setCostFromStart(double v)
    {
        costFromStart = v;
    }


    public double getCostFromStart()
    {
        return costFromStart;
    }


    public void costToGoal(MHMapCellAddress goal)
    {
        costToGoal = manhattanDistance(goal);
    }


    public double getScore()
    {
        return costFromStart + costToGoal;
    }


    public void setParent(MHNode p)
    {
        parent = p;
    }


    public MHNode getParent()
    {
        return parent;
    }

    /*
     * Return the neighboring tile node in the quad direction, except when that
     * location is invalid.
     */
    public MHNode makeNeighbor(MHTileMapDirection direction, MHTileMapView map)
    {
        MHNode newNode = new MHNode(map.tileWalk(row, column, direction));
        
        if (map.getMapData().isCollidable(newNode.row, newNode.column, null))
            return null;
        
        newNode.setCostFromStart(getCostFromStart() + 1.0);
        newNode.setParent(this);

        return newNode;
    } // end of makeNeighbor()


    public MHNodePath buildPath()
    /*
     * Build a path (a list of Points) from the next tile after the start up to
     * the this tile.
     */
    {
        MHNodePath path = new MHNodePath();
        path.add(this);
        MHNode temp = parent;
        while (temp != null)
        {
            path.add(0, temp);
            temp = temp.getParent();
        }
        path.remove(0);
        return path;
    }
}
