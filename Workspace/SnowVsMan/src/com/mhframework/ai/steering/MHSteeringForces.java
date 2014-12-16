package com.mhframework.ai.steering;

import com.mhframework.core.math.MHVector;
import com.mhframework.gameplay.actor.MHActor;
import com.mhframework.gameplay.tilemap.MHMapCellAddress;
import com.mhframework.gameplay.tilemap.view.MHTileMapView;


public abstract class MHSteeringForces 
{


    public static MHVector seek(MHActor entity, MHVector target)
    {
        // Position of target – our position = headingDirection
        MHVector headingDirection = target.subtract(entity.getPosition());
          
        // Desired velocity = Norm(headingDirection) * max velocity
        MHVector desiredVelocity = headingDirection.normalize().multiply(entity.getMaxSpeed());
  
        // Steering force = desired velocity – current velocity
        MHVector steeringForce = desiredVelocity.subtract(entity.getVelocity());
  
        return steeringForce;
    }
      
      
    public static MHVector flee(MHActor entity, MHVector target)
    {
        // Position of target – our position = headingDirection
        MHVector headingDirection = entity.getPosition().subtract(target);
          
  
        // Desired velocity = Norm(headingDirection) * max velocity
        MHVector desiredVelocity = headingDirection.normalize().multiply(entity.getMaxSpeed());
  
        // Steering force = desired velocity – current velocity
        MHVector steeringForce = desiredVelocity.subtract(entity.getVelocity());
  
        return steeringForce;
    } 
    

    public static MHVector arrive(MHActor entity, MHVector target, double slowRange)
    {
        // Position of target – our position = headingDirection
        MHVector headingDirection = target.subtract(entity.getPosition());
          
        // Desired velocity = Norm(headingDirection) * max velocity
        double pct = entity.getPosition().distance(target) / slowRange;
        pct = Math.min(pct, 1.0);
        
        MHVector desiredVelocity = headingDirection.normalize().multiply(entity.getMaxSpeed() * pct);
  
        // Steering force = desired velocity – current velocity
        MHVector steeringForce = desiredVelocity.subtract(entity.getVelocity());
  
        return steeringForce;
    }
      

    public static MHVector avoid(MHActor entity, MHVector target, double range)
    {
        // Position of target – our position = headingDirection
        MHVector headingDirection = entity.getPosition().subtract(target);
          
        // Desired velocity = Norm(headingDirection) * max velocity
        double pct = entity.getPosition().distance(target) / range;
        pct = Math.min(pct, 1.0);
        
        MHVector desiredVelocity = headingDirection.normalize().multiply(entity.getMaxSpeed() * pct);
  
        // Steering force = desired velocity – current velocity
        MHVector steeringForce = desiredVelocity.subtract(entity.getVelocity());
  
        return steeringForce;
    }


	public static MHVector avoidWalls(MHActor actor, MHTileMapView map) 
	{
		MHVector steeringForce = new MHVector();
		MHVector vel = actor.getVelocity();
		MHVector rightWhisker = vel.rotate(-1);
		MHVector leftWhisker = vel.rotate(1);

		MHMapCellAddress cell = map.calculateGridLocation(rightWhisker.add(actor.getPosition()));
		if (map.getMapData().isCollidable(cell.row, cell.column, actor))
			steeringForce = leftWhisker.subtract(rightWhisker);
		
		cell = map.calculateGridLocation(leftWhisker.add(actor.getPosition()));
		if (map.getMapData().isCollidable(cell.row, cell.column, actor))
			steeringForce = rightWhisker.subtract(leftWhisker);

		
		steeringForce = steeringForce.subtract(actor.getVelocity());
		
		return steeringForce;
	}
    
    
    
    public static MHVector wander(MHActor entity, double circleSize, double wanderDistance, double jitter)
    {
		// Find center of wander circle.
		MHVector velocity = entity.getVelocity();
		MHVector center = velocity.normalize().multiply(wanderDistance);
		center = center.add(entity.getPosition());
		
		// Create a vector to the circle's perimeter.
		MHVector perimeter = velocity.normalize().multiply(circleSize);
		
		// Rotate the perimeter vector to get the new wander target for this frame.
		double rot = -jitter + Math.random() * (jitter*2);
		//wanderer.setTargetAngle(wanderer.getTargetAngle() + rot);
		perimeter = perimeter.rotate(rot);
				
		// Add the perimeter vector to the circle center to get the exact wander point.
		MHVector wanderTarget = center.add(perimeter);

		// Seek to the point.
        MHVector seekForce = seek(entity, wanderTarget);
        
        return seekForce;

    }
}

