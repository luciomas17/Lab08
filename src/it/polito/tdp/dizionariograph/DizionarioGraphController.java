package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	Model model = new Model();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtLetterNumber;

    @FXML
    private TextField txtWordToSearch;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreateGraph(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtWordToSearch.clear();
    	
    	try {
			model.createGraph(Integer.parseInt(this.txtLetterNumber.getText()));
			this.txtResult.appendText("Grafo creato!");
			
		} catch (NumberFormatException e) {
			this.txtResult.appendText("Errore: numero di lettere non valido!");;
			e.printStackTrace();
		}
    }

    @FXML
    void doFindMaxDegree(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(model.getWordsList().size() == 0)
    		this.txtResult.appendText("Errore: devi prima creare un grafo!");
    	else
    		this.txtResult.appendText(String.format("Grado massimo: %d", model.findMaxDegree()));
    }

    @FXML
    void doFindNeighbors(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(model.getWordsList().size() == 0)
    		this.txtResult.appendText("Errore: devi prima creare un grafo!");
    	else {
	    	String wordToSearch = this.txtWordToSearch.getText().toLowerCase().trim();
	    	if(wordToSearch.length() != model.getWordsList().get(0).length()) {
	    		this.txtResult.appendText("Errore: la parola da cercare deve avere un numero di lettere "
	    				+ "pari al numero di lettere inserito per creare il grafo!");
	    		return;
	    	}
	    	
	    	List<String> neighbors = model.displayNeighbours(wordToSearch);
	    	if(neighbors == null)
	    		this.txtResult.appendText("Errore: parola non trovata!");
	    	else {
	    		for(String neighbor : neighbors)
	    			this.txtResult.appendText(neighbor + "\n");
	    	}
    	}
    }

    @FXML
    void doReset(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtLetterNumber.clear();
    	this.txtWordToSearch.clear();
    }

    @FXML
    void initialize() {
        assert txtLetterNumber != null : "fx:id=\"txtLetterNumber\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtWordToSearch != null : "fx:id=\"txtWordToSearch\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
	}
}
