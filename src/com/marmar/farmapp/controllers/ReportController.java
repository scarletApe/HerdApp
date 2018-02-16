package com.marmar.farmapp.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.EventDAO;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.model.Event;
import com.marmar.farmapp.model.Livestock;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

public class ReportController implements Initializable, Internationable {

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

	@FXML
	private ComboBox<Livestock> cbLivestock;

	@FXML
	private Label lbEvent;

	@FXML
	private Label lbLivestock;

	@FXML
	private ComboBox<Event> cbEvent;

	private HTMLContentMaker maker;
	private String currentReport;
	private String reportTitle;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		maker = new HTMLContentMaker();
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_report.png"));

		// llenar la lista con imagenes y las opciones
		ObservableList<String> opciones = FXCollections.observableArrayList("1. Reporte General",
				"2. Reporte de Muertes",  "3. Reporte de Individuo",
				"4. Reporte de un Evento","5. Reporte Completo Año");// , "6. Misceleano");
		lvList.setItems(opciones);
		lvList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					handleListMenu(nv);
				});
		// set the style to the list view
		lvList.getStylesheets().add(ResourceManager.listCSS);
		lvList.getSelectionModel().select(0);

		fillCombos();
		
		//hide the refresh button
		btnRefresh.setVisible(false);
	}

	private void fillCombos() {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		EventDAO edao = ctx.getBean(EventDAO.class);
		LivestockDAO ldao = ctx.getBean(LivestockDAO.class);

		// llenar el combobox de livestock
		ObservableList<Livestock> ganado = FXCollections.observableArrayList();
		ArrayList<Livestock> data2 = ldao.getAll();// .getLivestockFiltred(0, "Active Only", "All", null, "All Ages",
													// 0);
		data2.stream().forEach((obj) -> {
			ganado.add(obj);
		});
		cbLivestock.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Livestock> o, Livestock ov, Livestock nv) -> {
					currentReport = maker.getReportIndividual(nv);
					reportTitle = "ReporteIndividuo";
					System.out.println(currentReport);
					final WebEngine webEngine = webView.getEngine();
					webEngine.loadContent(currentReport);
				});
		cbLivestock.setItems(ganado);

		// llenar el combobox de eventos
		ObservableList<Event> events = FXCollections.observableArrayList();
		ArrayList<Event> data = edao.getAll();
		data.stream().forEach((obj) -> {
			events.add(obj);
		});
		cbEvent.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Event> o, Event ov, Event nv) -> {
					currentReport = maker.getReportEvent(nv);
					reportTitle = "ReporteEvento";
					System.out.println(currentReport);
					final WebEngine webEngine = webView.getEngine();
					webEngine.loadContent(currentReport);
				});
		cbEvent.setItems(events);
	}

	private void handleListMenu(String option) {

		switch (option.charAt(0)) {
		// case "General Report":
		case '1': {
			currentReport = maker.getGeneralReport();
			reportTitle = "ReporteGeneral";
			System.out.println(currentReport);
			final WebEngine webEngine = webView.getEngine();
			webEngine.loadContent(currentReport);

			cbLivestock.setDisable(true);
			cbEvent.setDisable(true);
			lbLivestock.setDisable(true);
			lbEvent.setDisable(true);
		}
			break;
		// case "Report of Deaths":
		case '2':
			currentReport = maker.getReportDeaths();
			reportTitle = "ReporteMuertes";
			System.out.println(currentReport);
			final WebEngine webEngine = webView.getEngine();
			webEngine.loadContent(currentReport);

			cbLivestock.setDisable(true);
			cbEvent.setDisable(true);
			lbLivestock.setDisable(true);
			lbEvent.setDisable(true);
			break;
		// case "Report of Births":
		case '3':
			cbLivestock.setDisable(false);
			cbEvent.setDisable(true);
			lbLivestock.setDisable(false);
			lbEvent.setDisable(true);

			cbLivestock.setValue(cbLivestock.getItems().get(0));
			break;
		// case "Event Report":
		case '4':
			cbLivestock.setDisable(true);
			cbEvent.setDisable(false);
			lbLivestock.setDisable(true);
			lbEvent.setDisable(false);

			cbEvent.setValue(cbEvent.getItems().get(0));
			break;
		// case "Reporte Completo":
		case '5':
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
		fileChooser.setInitialFileName(reportTitle + ".pdf");
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
		fileChooser.setInitialFileName(reportTitle + ".html");
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

	@Override
	public void setLabels() {
		// TODO Auto-generated method stub

	}

}
