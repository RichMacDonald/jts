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
package org.locationtech.jtstest.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class DoubleKeyMap 
{
	private Map<Object, Map<Object, Object>> topMap = new TreeMap<Object, Map<Object, Object>>();
	
	public void put(Object key1, Object key2, Object value)
	{
		Map<Object, Object> keyMap = (Map<Object, Object>) topMap.get(key1);
		if (keyMap == null)
			keyMap = createKeyMap(key1);
		keyMap.put(key2, value);
	}
	
	private Map<Object, Object> createKeyMap(Object key1)
	{
		Map<Object, Object> map = new TreeMap<Object, Object>();
		topMap.put(key1, map);
		return map;
	}
	
	public Object get(Object key1, Object key2)
	{
		Map<?, ?> keyMap = (Map<?, ?>) topMap.get(key1);
		if (keyMap == null) return null;
		return keyMap.get(key2);
	}
	
	public Set<Object> keySet()
	{
		return topMap.keySet();
	}
	public Set<?> keySet(Object key)
	{
		Map<?, ?> keyMap = (Map<?, ?>) topMap.get(key);
		if (keyMap == null) return new TreeSet<Object>();
		return keyMap.keySet();
	}
	
	public Collection<?> values(Object key1)
	{
		Map<?, ?> keyMap = (Map<?, ?>) topMap.get(key1);
		if (keyMap == null) return new ArrayList<Object>();
		return keyMap.values();
	}
}
