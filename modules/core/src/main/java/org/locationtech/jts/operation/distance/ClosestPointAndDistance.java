package org.locationtech.jts.operation.distance;

import org.locationtech.jts.geom.Coordinate;

/**
 * Only used to pass multiple disparate objects in a method return.
 * Declared final so the runtime will optimize it away and eliminate gc.
 * 
 * @author rjm
 *
 */
public final class ClosestPointAndDistance {
	public final double dist;
	public final Coordinate closestPt0;
	public final Coordinate closestPt1;
	
	public ClosestPointAndDistance(double dist, Coordinate closestPt0) {
		this.dist = dist;
		this.closestPt0 = closestPt0;
		this.closestPt1 = null;
	}
	public ClosestPointAndDistance(double dist, Coordinate closestPt0, Coordinate closestPt1) {
		this.dist = dist;
		this.closestPt0 = closestPt0;
		this.closestPt1 = closestPt1;
	}
}
