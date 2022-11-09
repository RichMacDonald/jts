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

import org.locationtech.jts.algorithm.Distance;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.Polygon;

/**
 * Delegate for all distance calculations between lines, points, and Envelopes.
 * DEFAULT is unchanged from original code. Delegation allows substitution of
 * projections where geometry is not Cartesian.
 * 
 * @author rjm
 */
public interface DistanceSupport {
	double distance(Coordinate A, Coordinate B);
//	double distance(Coordinate A, Coordinate B, Coordinate C, Coordinate D);
//	Coordinate[] closestPoints(LineSegment seg0, LineSegment seg1);
//	Coordinate closestPoint(LineSegment seg, Coordinate coord);
	int locate(Coordinate pt, Polygon poly);
	double distance(Envelope env0, Envelope env1);
	double orthogonal(Coordinate p, Coordinate A, Coordinate B);

	ClosestPointAndDistance distance(Coordinate A, Coordinate B, Coordinate C, Coordinate D, double minDistance);
	ClosestPointAndDistance orthogonal(Coordinate p, Coordinate A, Coordinate B, double minDistance);
	
	

	static DistanceSupport DEFAULT = new DistanceSupport() {
		private final PointLocator ptLocator = new PointLocator();

		public final double distance(Coordinate A, Coordinate B, Coordinate C, Coordinate D) {
			return Distance.segmentToSegment(A, B, C, D);
		}

		public final Coordinate[] closestPoints(LineSegment seg0, LineSegment seg1) {
			return seg0.closestPoints(seg1);
		}

		public final Coordinate closestPoint(LineSegment seg, Coordinate coord) {
			return seg.orthogonalPoint(coord);
		}

		@Override
		public final int locate(Coordinate pt, Polygon poly) {
			return ptLocator.locate(pt, poly);
		}

		@Override
		public final double distance(Envelope env0, Envelope env1) {
			return env0.distance(env1);
		}

		@Override
		public final double orthogonal(Coordinate p, Coordinate A, Coordinate B) {
			return Distance.pointToSegmentOrthogonal(p, A, B);
		}

		@Override
		public final double distance(Coordinate A, Coordinate B) {
			return A.distance(B);
		}

		@Override
		public final ClosestPointAndDistance orthogonal(Coordinate p, Coordinate A, Coordinate B, double minDistance) {
	        double dist = orthogonal( p, A, B );
            if (dist < minDistance) {
              LineSegment seg = new LineSegment(A, B);
              Coordinate segClosestPoint = closestPoint(seg, p);
              return new ClosestPointAndDistance(dist, segClosestPoint);
            }
            return null;
		}

		@Override
		public final ClosestPointAndDistance distance(Coordinate A, Coordinate B, Coordinate C, Coordinate D, double minDistance) {
	        double dist = distance(A, B, C, D);
	        if (dist < minDistance) {
	          minDistance = dist;
	          LineSegment seg0 = new LineSegment(A, B);
	          LineSegment seg1 = new LineSegment(C, D);
	          Coordinate[] closestPt = closestPoints(seg0, seg1);
              return new ClosestPointAndDistance(dist, closestPt[0], closestPt[1]);
	        }
	        return null;
		};
	};

}
