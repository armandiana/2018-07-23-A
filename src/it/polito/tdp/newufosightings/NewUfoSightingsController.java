/**
 * Sample Skeleton for 'NewUfoSightings.fxml' Controller Class
 */

package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewUfoSightingsController {

	private Model model;
	
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML // fx:id="txtAnno"
	private TextField txtAnno; // Value injected by FXMLLoader

	@FXML // fx:id="btnSelezionaAnno"
	private Button btnSelezionaAnno; // Value injected by FXMLLoader

	@FXML // fx:id="cmbBoxForma"
	private ComboBox<String> cmbBoxForma; // Value injected by FXMLLoader

	@FXML // fx:id="btnCreaGrafo"
	private Button btnCreaGrafo; // Value injected by FXMLLoader

	@FXML // fx:id="txtT1"
	private TextField txtT1; // Value injected by FXMLLoader

	@FXML // fx:id="txtAlfa"
	private TextField txtAlfa; // Value injected by FXMLLoader

	@FXML // fx:id="btnSimula"
	private Button btnSimula; // Value injected by FXMLLoader

	@FXML
	void doCreaGrafo(ActionEvent event) {
		Integer anno=null;
		String shape=null;
		try {
			anno= Integer.parseInt(this.txtAnno.getText());
		}catch(IllegalArgumentException e) {
			this.txtResult.appendText("Inserire un anno dal 1910 al 2014 nel formato corretto.\n");
			return;
		}
		try {
			shape= this.cmbBoxForma.getValue();
		}catch(NullPointerException e) {
			this.txtResult.appendText("Selezionare una forma dal menù a tendina.\n");
			return;
		}
		if(anno>2014||anno<1910) {
			this.txtResult.appendText("Inserire un anno dal 1910 al 2014.\n");
		}
		
		this.model.creaGrafo(anno, shape);
		this.txtResult.appendText("Creato grafo con "+this.model.getGrafo().vertexSet().size()+" vertici e "+this.model.getGrafo().edgeSet().size()+" archi.\n");


		for(String s: this.model.getGrafo().vertexSet()) {
			this.txtResult.appendText("Stato: "+s+" con peso: "+this.model.getPesoTotale(s)+".\n");
		}
	}

	@FXML
	void doSelezionaAnno(ActionEvent event) {
		Integer anno=null;
		
		try {
			anno= Integer.parseInt(this.txtAnno.getText());
			
		}catch(IllegalArgumentException e) {
			this.txtResult.appendText("Inserire un anno dal 1910 al 2014 nel formato corretto.\n");
			return;
		}
		
		if(anno>2014 || anno<1910) {
			this.txtResult.appendText("Inserire un anno dal 1910 al 2014.\n");
			return;
		}
		this.cmbBoxForma.getItems().clear();
		this.cmbBoxForma.getItems().addAll(this.model.getFormeByYear(anno));
	}

	@FXML
	void doSimula(ActionEvent event) {

	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
		assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
		this.cmbBoxForma.getItems().clear();
	}
}
