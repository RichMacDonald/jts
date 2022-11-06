package org.locationtech.jts.operation.distance;

import org.locationtech.jts.algorithm.Distance;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.Polygon;

/**
 * Delegate for all distance calculations between lines, points, and Envelopes.
 * DEFAULT is unchanged from original code.
 * Delegation allows substitution of projections where geometry is not Cartesian.
 * 
 * @author rjm
 *
 */
interface DistanceSupport {
	double distance(Coordinate A, Coordinate B,  Coordinate C, Coordinate D);
	Coordinate[] closestPoints(LineSegment seg0, LineSegment seg1);
	Coordinate closestPoint(LineSegment seg, Coordinate coord);
	int locate(Coordinate pt, Polygon poly);
	double distance(Envelope env0, Envelope env1);
	double pointToSegment(Coordinate p, Coordinate A, Coordinate B);
	double distance(Coordinate A, Coordinate B);
	
	 
	 static DistanceSupport DEFAULT = new DistanceSupport() {
		private final PointLocator ptLocator = new PointLocator();

		@Override
		public final double distance(Coordinate A, Coordinate B, Coordinate C, Coordinate D) {
			return Distance.segmentToSegment(A, B, C, D);
		}

		@Override
		public final Coordinate[] closestPoints(LineSegment seg0, LineSegment seg1) {
			return seg0.closestPoints(seg1);
		}

		@Override
		public final Coordinate closestPoint(LineSegment seg, Coordinate coord) {
			return seg.closestPoint(coord);
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
		public double pointToSegment(Coordinate p, Coordinate A, Coordinate B) {
			return Distance.pointToSegment(p, A, B);
		}

		@Override
		public double distance(Coordinate A, Coordinate B) {
			return A.distance(B);
		};
	 };

	 
}
