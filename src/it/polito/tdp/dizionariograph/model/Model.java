package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private WordDAO dao;
	private Graph<String, DefaultEdge> graph;
	private List<String> words;

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
				if(areConnected(word, wordToCompare))
					this.graph.addEdge(word, wordToCompare);
			}
		}
	}

	private boolean areConnected(String word, String wordToCompare) {
		if(!word.equals(wordToCompare)) {
			int count = 0;
			for(int i=0; i<word.length(); i++) {
				if(!word.substring(i, i+1).equals(wordToCompare.substring(i, i+1)))
					count++;
			}
			if(count == 1)
				return true;
		}
		return false;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		return new ArrayList<String>();
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
