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

package org.locationtech.jtstest.testbuilder.geom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

/**
 * Locates the paths to facets (vertices and segments) of 
 * a {@link Geometry} which are within a given tolerance
 * of a query point.
 * 
 *  
 * @author Martin Davis
 *
 */
public class FacetLocater 
{
	/**
	 * Creates a list containing all the vertex {@link GeometryLocation}s
	 * in the input collection.
	 * 
	 * @param locations the source collection
	 * @return a list of the vertex locations, if any
	 */
	public static List<GeometryLocation> filterVertexLocations(Collection<GeometryLocation> locations)
	{
		ArrayList<GeometryLocation> vertexLocs = new ArrayList<GeometryLocation>();
		for (GeometryLocation loc : locations) {
			if (loc.isVertex()) vertexLocs.add(loc);
		}
		return vertexLocs;
	}
	
  private Geometry parentGeom;
  private List<GeometryLocation> locations = new ArrayList<GeometryLocation>();
  private Coordinate queryPt;
  private double tolerance = 0.0; 
  
  public FacetLocater(Geometry parentGeom) {
    this.parentGeom = parentGeom;
  }
  
  public List<GeometryLocation> getLocations(Coordinate queryPt, double tolerance)
  {
  	this.queryPt = queryPt;
  	this.tolerance = tolerance;
    findLocations(parentGeom, locations);
    return locations;
  }
  
  private void findLocations(Geometry geom, List<GeometryLocation> locations)
  {
    findLocations(new Stack<Integer>(), parentGeom, locations);
  }
    
  private void findLocations(Stack<Integer> path, Geometry geom, List<GeometryLocation> locations)
  {
  	if (geom instanceof GeometryCollection) {
  		for (int i = 0; i < geom.getNumGeometries(); i++ ) {
  			Geometry subGeom = geom.getGeometryN(i);
  			path.push(i);
  			findLocations(path, subGeom, locations);
  			path.pop();
  		}
  	}
  	else if (geom instanceof Polygon) { 
  			findLocations(path, (Polygon) geom, locations);

  	}
  	else {
  		CoordinateSequence seq;
  	
  		if (geom instanceof LineString) {
   		 seq = ((LineString) geom).getCoordinateSequence();
  		}
  		else if (geom instanceof Point) {
  		 seq = ((Point) geom).getCoordinateSequence();
  		}
  		else {
  			throw new IllegalStateException("Unknown geometry type: " + geom.getClass().getName());
  		}
  		findLocations(path, geom, seq, locations);
  	}
  }
  
  private void findLocations(Stack<Integer> path, Polygon poly, List<GeometryLocation> locations)
  {
		path.push(0);
		findLocations(path, 
				poly.getExteriorRing(),
				poly.getExteriorRing().getCoordinateSequence(), locations);
		path.pop();
		
		for (int i = 0; i < poly.getNumInteriorRing(); i++ ) {
			path.push(i + 1);
			findLocations(path, 
					poly.getInteriorRingN(i), 
					poly.getInteriorRingN(i).getCoordinateSequence(), locations);
			path.pop();
		}
  }

  private void findLocations(Stack<Integer> path, Geometry compGeom, CoordinateSequence seq, List<GeometryLocation> locations)
  {
  	findVertexLocations(path, compGeom, seq, locations);
  	findSegmentLocations(path, compGeom, seq, locations);
  }

  private void findVertexLocations(Stack<Integer> path, Geometry compGeom, CoordinateSequence seq, List<GeometryLocation> locations)
  {
  	for (int i = 0; i < seq.size(); i++) {
      Coordinate p = seq.getCoordinate(i);
      double dist = p.distance(queryPt);
      if (dist <= tolerance) 
      	locations.add(new GeometryLocation(parentGeom, compGeom, toIntArray(path), i, true, p));
  	}
  }

  private void findSegmentLocations(Stack<Integer> path, Geometry compGeom, CoordinateSequence seq, List<GeometryLocation> locations)
  {
  	LineSegment seg = new LineSegment();
  	for (int i = 0; i < seq.size() - 1; i++) {
      seg.p0 = seq.getCoordinate(i);
      seg.p1 = seq.getCoordinate(i+1);
      double dist = seg.distance(queryPt);
      if (dist <= tolerance) 
      	locations.add(new GeometryLocation(parentGeom, compGeom, toIntArray(path), i, false, seg.p0));
  	}
  }

	public static int[] toIntArray(Vector<Integer> path)
	{
		int[] index = new int[path.size()];
		int i = 0;
		for (Object element : path) {
			Integer pathIndex = (Integer) element;
			index[i++] = pathIndex.intValue();
		}
		return index;
	}

}
