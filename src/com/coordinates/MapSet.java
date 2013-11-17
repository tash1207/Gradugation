package com.coordinates;

import java.util.HashSet;

public class MapSet extends HashSet<String> {

	private static final long serialVersionUID = 1L;
	
	public MapSet() {
		super();
	}

	public boolean contains(MapCoordinate coord) {
		return super.contains(coord.toString());
	}
	
	public boolean add(MapCoordinate coord) {
		return super.add(coord.toString());
	}
}
