package it.polito.tdp.dizionariograph.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.createGraph(4);
		System.out.println("Grafo creato: " + model.getVertexSetSize() + " vertici e " + model.getEdgeSetSize() + " archi\n");
		
		List<String> vicini = model.displayNeighbours("casa");
		System.out.println("Neighbours di casa (dim: " + vicini.size() + "): " + vicini + "\n");
		
		System.out.println("Cerco il vertice con grado massimo...");
		System.out.println(model.findMaxDegree());
	}

}
