/*
 * Copyright (c) 2016 Vivid Solutions.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v20.html
 * and the Eclipse Distribution License is available at
 *
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.locationtech.jts.operation.distance;

import org.locationtech.jts.geom.Coordinate;

/**
 * Only used to pass multiple disparate objects in a method return.
 * Declared final so the runtime will optimize it away and eliminate gc.
 * 
 * @author rjm
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
