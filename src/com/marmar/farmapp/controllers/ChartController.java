package com.marmar.farmapp.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.util.ChartManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class ChartController implements Initializable {

	@FXML
	private Label lbFilter;

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private JFXButton btnHTML;

	@FXML
	private BorderPane bpBorder;

	@FXML
	private Label lbLabel;

	@FXML
	private JFXButton btnPDF;

	@FXML
	private ListView<String> lvList;

	@FXML
	private ComboBox<Animal> cbAnimal;

	@FXML
	private ImageView ivImage;

	private ChartManager charter;
	private Animal a;
	private AnimalDAO crud;
	private Node chart;
	private String name = "none";
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_chart.png"));
		
		charter = new ChartManager();
		
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		crud = ctx.getBean(AnimalDAO.class);
		local = ResourceManager.localizer;

		ObservableList<Animal> animalData = FXCollections.observableArrayList();
		ArrayList<Animal> allAni = crud.getAll();
		allAni.stream().forEach((alraza1) -> {
			animalData.add(alraza1);
		});
		cbAnimal.setItems(animalData);
		cbAnimal.setValue(animalData.get(0));

		ResourceBundle msg = local.getMessages();

		lbFilter.setText(msg.getString("label.filter.by.animal") + ":");
		lbLabel.setText(msg.getString("label.view.charts") + ":");
		btnPDF.setText(msg.getString("button.pdf"));
		btnRefresh.setText(msg.getString("button.refresh"));

		// llenar la lista con imagenes y las opciones
		ObservableList<String> opciones = FXCollections.observableArrayList(
				// "Livestock Pie Chart",
				"1. " + msg.getString("label.chart.livestock.pie"),
				// "Livestock Bar Chart",
				"2. " + msg.getString("label.chart.livestock.bar"),
				// "Breeds Population",
				"3. " + msg.getString("label.chart.breeds"),
				// "Births Per Year",
				"4. " + msg.getString("label.chart.births.year"),
				// "Deaths Per Year",
				"5. " + msg.getString("label.chart.deaths.year"));
		lvList.setItems(opciones);
		lvList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					handleListMenu(nv);
				});
		// set the style to the list view
		lvList.getStylesheets().add(ResourceManager.listCSS);
		lvList.getSelectionModel().select(0);

		// hide the pdf button
		// btnPDF.setVisible(false);
		btnPDF.setDisable(true);
	}

	private void handleListMenu(String option) {

		a = cbAnimal.getSelectionModel().getSelectedItem();

		switch (option.charAt(0)) {
		// case "Livestock Pie Chart":
		case '1':
			chart = charter.fillChartPiePopLivestock(a);
			name = a.getName() + "PieChart";
			bpBorder.setCenter(chart);
			break;
		// case "Livestock Bar Chart":
		case '2':
			chart = charter.fillChartBarPopLivestock(a);
			name = a.getName() + "BarChart";
			bpBorder.setCenter(chart);
			break;
		// case "Births Per Year":
		case '4':
			chart = charter.fillChartNacimientos(a);
			name = a.getName() + "BirthsYear";
			bpBorder.setCenter(chart);
			break;
		// case "Deaths Per Year":
		case '5':
			chart = charter.fillChartMuertes(a);
			name = a.getName() + "DeathsYear";
			bpBorder.setCenter(chart);
			break;
		// case "Breeds Population":

		case '3':
			chart = charter.fillChartRazas(a);
			name = a.getName() + "BreedsPop";
			bpBorder.setCenter(chart);
			break;
		}
	}

	@FXML
	void handleRefresh(ActionEvent event) {
		ObservableList<Animal> animalData = FXCollections.observableArrayList();
		ArrayList<Animal> allAni = crud.getAll();
		allAni.stream().forEach((alraza1) -> {
			animalData.add(alraza1);
		});
		cbAnimal.setItems(animalData);
		cbAnimal.setValue(animalData.get(0));
	}

	@FXML
	void handlePDF(ActionEvent event) {

	}

	@FXML
	void handleHTML(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to Image File");
		fileChooser.setInitialFileName(name + ".png");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				WritableImage image = chart.snapshot(new SnapshotParameters(), null);
				ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
			} catch (Exception ex) {
				System.out.println("Exception=" + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}