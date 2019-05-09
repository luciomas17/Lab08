package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private class EdgeTraversedGraphListener implements TraversalListener<String, DefaultEdge> {
		@Override
		public void connectedComponentFinished(ConnectedComponentTraversalEvent arg0) {
		}

		@Override
		public void connectedComponentStarted(ConnectedComponentTraversalEvent arg0) {
		}

		@Override
		public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
			String sourceVertex = graph.getEdgeSource(e.getEdge());
			String targetVertex = graph.getEdgeTarget(e.getEdge());
			
			if(!backVisit.containsKey(targetVertex) && backVisit.containsKey(sourceVertex))
				backVisit.put(targetVertex, sourceVertex);
		}

		@Override
		public void vertexFinished(VertexTraversalEvent<String> arg0) {
		}

		@Override
		public void vertexTraversed(VertexTraversalEvent<String> arg0) {
		}
	}
	
	private WordDAO dao;
	private Graph<String, DefaultEdge> graph;
	private List<String> words;
	private Map<String, String> backVisit;

	public Model() {
		this.dao = new WordDAO();
		this.graph = new SimpleGraph<>(DefaultEdge.class);
	}
	
	public void createGraph(int numeroLettere) {
		this.words = dao.getAllWordsFixedLength(numeroLettere);
		
		Graphs.addAllVertices(this.graph, this.words);
		
		for(String word : this.graph.vertexSet()) {
			List<String> wordsToCompare = new ArrayList<>(words);
			for(String wordToCompare : wordsToCompare) {
				if(!word.equals(wordToCompare) && areConnected(word, wordToCompare))
					this.graph.addEdge(word, wordToCompare);
			}
		}
	}

	private boolean areConnected(String word, String wordToCompare) {
		int count = 0;
		for(int i=0; i<word.length(); i++) {
			if(!word.substring(i, i+1).equals(wordToCompare.substring(i, i+1)))
				count++;
		}
		if(count == 1)
			return true;
		else
			return false;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		List<String> neighbours = Graphs.neighborListOf(this.graph, parolaInserita);

		return neighbours;
	}

	public int findMaxDegree() {
		
		return -1;
	}
	
	public int getVertexSetSize() {
		return this.graph.vertexSet().size();
	}
	
	public int getEdgeSetSize() {
		return this.graph.edgeSet().size();
	}
}
