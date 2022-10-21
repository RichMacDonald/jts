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
package org.locationtech.jtstest.testrunner;

import org.locationtech.jts.geom.Geometry;

/**
 * A {@link ResultMatcher} which always passes.
 * This is useful if the expected result of an operation is not known.
 * 
 * @author mbdavis
 *
 */
public class NullResultMatcher
 implements ResultMatcher
{
	/**
	 * Always reports a match.
	 * 
	 * @return true always
	 */
	@Override
	public boolean isMatch(Geometry geom, String opName, Object[] args, 
			Result actualResult, Result expectedResult,
			double tolerance)
	{
		return true;
	}

	
}
