package com.marmar.farmapp.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.util.ResourceManager;
import com.marmar.farmapp.util.writers.HTMLContentMaker;
import com.marmar.farmapp.util.writers.HTMLWriter;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class ReportController implements Initializable {

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private JFXButton btnHTML;

	@FXML
	private WebView webView;

	@FXML
	private Label lbLabel;

	@FXML
	private JFXButton btnPDF;

	@FXML
	private ListView<String> lvList;

	@FXML
	private ImageView ivImage;

	private HTMLContentMaker maker;
	private String currentReport;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		maker = new HTMLContentMaker();
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_report.png"));

		// llenar la lista con imagenes y las opciones
		ObservableList<String> opciones = FXCollections.observableArrayList("General Report", "Report of Deaths",
				"Report of Births", "Individual Report", "Event Report", "Other Report");
		lvList.setItems(opciones);
		lvList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					handleListMenu(nv);
				});
		// set the style to the list view
		lvList.getStylesheets().add(ResourceManager.listCSS);
		lvList.getSelectionModel().select(0);

	}

	private void handleListMenu(String option) {

		switch (option) {
		case "General Report": {
			currentReport = maker.getGeneralReport();
			System.out.println(currentReport);
			final WebEngine webEngine = webView.getEngine();
			webEngine.loadContent(currentReport);
		}
			break;
		case "Report of Deaths":
			break;
		case "Report of Births":
			break;
		case "Individual Report":
			break;
		case "Event Report":
			break;
		case "Other Report":
			break;
		}
	}

	@FXML
	void handleRefresh(ActionEvent event) {

	}

	@FXML
	void handlePDF(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to PDF File");
		fileChooser.setInitialFileName("Livestock.pdf");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				HTMLWriter writer = new HTMLWriter();
				writer.writePDF(currentReport, file, false);
			} catch (Exception ex) {
				System.out.println("Exception=" + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	@FXML
	void handleHTML(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to HTML File");
		fileChooser.setInitialFileName("Livestock.html");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				HTMLWriter writer = new HTMLWriter();
				writer.writeHTML(currentReport, file);
			} catch (Exception ex) {
				System.out.println("Exception=" + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
