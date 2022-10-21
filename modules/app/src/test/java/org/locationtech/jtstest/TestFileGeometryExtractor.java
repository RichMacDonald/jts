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
package org.locationtech.jtstest;

import java.io.File;
import java.util.ArrayList;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jtstest.testrunner.TestCase;
import org.locationtech.jtstest.testrunner.TestReader;
import org.locationtech.jtstest.testrunner.TestRun;
import org.locationtech.jtstest.util.FileUtil;
import org.locationtech.jtstest.util.StringUtil;



/**
 * @version 1.7
 */
public class TestFileGeometryExtractor {

  public TestFileGeometryExtractor() {
  }
  public static void main(String[] args) throws Exception {
    TestReader testReader = new TestReader();
    TestRun testRun = testReader.createTestRun(new File("c:\\blah\\isvalid.xml"), 0);
    ArrayList geometries = new ArrayList();
    for (Object element : testRun.getTestCases()) {
      TestCase testCase = (TestCase) element;
      add(testCase.getGeometryA(), geometries);
      add(testCase.getGeometryB(), geometries);
    }
    String run = "";
    int j = 0;
    for (Object element : geometries) {
      Geometry geometry = (Geometry) element;
      j++;
      run += "<case>" + StringUtil.newLine;
      run += "  <desc>Test " + j + "</desc>" + StringUtil.newLine;
      run += "  <a>" + StringUtil.newLine;
      run += "    " + geometry + StringUtil.newLine;
      run += "  </a>" + StringUtil.newLine;
      run += "  <test> <op name=\"isValid\" arg1=\"A\"> true </op> </test>" + StringUtil.newLine;
      run += "</case>" + StringUtil.newLine;
    }
    FileUtil.setContents("c:\\blah\\isvalid2.xml", run);
  }

  private static void add(Geometry geometry, ArrayList geometries) {
    if (geometry == null) { return; }
    for (Object element : geometries) {
      Geometry existingGeometry = (Geometry) element;
      if (geometry.equalsExact(existingGeometry)) { return; }
    }
    geometries.add(geometry);
  }
}
