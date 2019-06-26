package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {
	private NewUfoSightingsDAO dao;
	
	private List<String>statiUsId;
	private List<State>statiUs;
	
	private Graph<String, DefaultWeightedEdge>grafo;
	
	
	public Model() {
		dao= new NewUfoSightingsDAO();
		statiUsId= new ArrayList<String>(dao.getAllStateUsId());
		statiUs= new ArrayList<State>(dao.loadAllStates());
	}
	
	public List<String>getFormeByYear(Integer year){
		return dao.getAllFormeByYear(year);
	}
	
	public void creaGrafo(Integer anno, String forma) {
		this.grafo= new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.statiUsId);
		
		for(String s1: this.grafo.vertexSet()) {
			List<StatiAvvistamenti>vicini=new ArrayList<StatiAvvistamenti>(dao.getConnectedWeight(s1, anno, forma));
			if(!vicini.isEmpty()) {
			for(StatiAvvistamenti s2: vicini) {
				if(!s1.equals(s2.getStatoVicino())) {
				//if(this.grafo.getEdge(s1, s2.getStatoVicino())==null) {
					Graphs.addEdge(this.grafo, s1, s2.getStatoVicino(), s2.getNumeroAvvistamenti());
				//}else {
					this.grafo.setEdgeWeight(this.grafo.getEdge(s1, s2.getStatoVicino()), s2.getNumeroAvvistamenti());
				}
			}
		  }
		}
		
		System.out.println("Grafo creato con "+this.grafo.vertexSet().size()+" vertici e "+this.grafo.edgeSet().size()+" archi.");
	}
	
	public Graph<String, DefaultWeightedEdge>getGrafo(){
		return this.grafo;
	}
	
	public Integer getPesoTotale(String s){
		if(grafo==null) {
			return null;
		}
		Integer peso=null;
		
		for(String s2: Graphs.neighborListOf(this.grafo, s)) {
			peso=+(int)this.grafo.getEdgeWeight(this.grafo.getEdge(s, s2));
		}
		return peso;
	}
	
	

	

}
