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
		this.words = new ArrayList<>();
	}
	
	public void createGraph(int numeroLettere) {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
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
		if(!this.words.contains(parolaInserita))
			return null;
		
		List<String> neighbours = Graphs.neighborListOf(this.graph, parolaInserita);

		return neighbours;
	}

	public int findMaxDegree() {
		int maxDegree = 0;

		for(String vertex : this.graph.vertexSet()) {
			if(graph.degreeOf(vertex) > maxDegree)
				maxDegree = graph.degreeOf(vertex);
		}
		
		return maxDegree;
	}
	
	public int getVertexSetSize() {
		return this.graph.vertexSet().size();
	}
	
	public int getEdgeSetSize() {
		return this.graph.edgeSet().size();
	}
	
	public List<String> getWordsList() {
		return this.words;
	}
}
