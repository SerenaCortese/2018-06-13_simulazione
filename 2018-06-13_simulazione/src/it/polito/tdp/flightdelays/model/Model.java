package it.polito.tdp.flightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class Model {
	
	private FlightDelaysDAO dao ;
	
	private List<Airline> airlines;
	private List<Airport> airports;
	private Map<Flight, Double> mappaArchi;
	private List<Arco> archi;
	
	private AeroportiIdMap aeroportiIdMap;
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new FlightDelaysDAO();
		
		this.aeroportiIdMap = new AeroportiIdMap();
		
		this.airlines = dao.loadAllAirlines();
		this.airports = dao.loadAllAirports(aeroportiIdMap);
		
	}
	
	public List<Airline> getAirlines() {
		return this.airlines;
	}


	public void creaGrafo(Airline airline) {
		
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		archi = new ArrayList<>();
		
		Graphs.addAllVertices(this.grafo, airports);
		
		System.out.println("Vertici: "+ grafo.vertexSet().size());
		
		mappaArchi = dao.getArchi(airline);
		for(Flight f : mappaArchi.keySet()) {
			Airport sourceAirport = aeroportiIdMap.get(f.getOriginAirportId());
			Airport destinationAirport = aeroportiIdMap.get(f.getDestinationAirportId());
			
			if(sourceAirport != null && destinationAirport != null) {
				double mediaRitardi = mappaArchi.get(f);
				double distanza = LatLngTool.distance(new LatLng(sourceAirport.getLatitude(), 
						sourceAirport.getLongitude()), new LatLng(destinationAirport.getLatitude(), 
								destinationAirport.getLongitude()), LengthUnit.KILOMETER); ;
				double peso = mediaRitardi/distanza;
				mappaArchi.put(f, peso);
				Graphs.addEdge(this.grafo, sourceAirport, destinationAirport, peso);
				archi.add(new Arco(sourceAirport, destinationAirport, peso));
			}
			
		}
		
		System.out.println("Numero Archi: "+grafo.edgeSet().size());
	}
	
	public List<Arco> getPeggiori(){
		Collections.sort(archi);
		List<Arco> peggiori = new ArrayList<>();
		if(archi.size()>10) {
			for (int i = 0; i<10; i++) {
				peggiori.add(archi.get(i));
			}
			return peggiori;
			
		}
		return archi;
		
	}

	public int simula(int k, int v) {
		for(Airport a : this.airports) {
			a.setVoliPartenza(dao.getVoliPartenzaDaAeroporto(a));
		}
		Simulazione sim = new Simulazione(k,v,this);
		sim.init();
		sim.run();
		return sim.getTotRitardo();
		
	}

	public List<Airport> getAirports() {
		return airports;
	}

	public AeroportiIdMap getAeroportiIdMap() {
		return aeroportiIdMap;
	}
	
	
	
	

}
