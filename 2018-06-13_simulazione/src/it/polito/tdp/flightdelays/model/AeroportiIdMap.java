package it.polito.tdp.flightdelays.model;

import java.util.HashMap;
import java.util.Map;

public class AeroportiIdMap {
	
	private Map<String, Airport> map;
	
	public AeroportiIdMap() {
		map = new HashMap<>();
	}
	
	public Airport get(String airportId) {
		return map.get(airportId);
	}
	
	public Airport get(Airport airport) {
		Airport old = map.get(airport.getId());
		if (old == null) {
			map.put(airport.getId(), airport);
			return airport;
		}
		return old;
	}
	
	public void put(Airport airport, String airportId) {
		map.put(airportId, airport);
	}

}
