package com.marmar.farmapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;
import com.marmar.farmapp.util.writers.ReportManager;
import com.marmar.farmapp.util.writers.Writable;
import com.marmar.farmapp.util.writers.XLSWriter;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("unchecked")
public class LivestockController implements Initializable, Internationable {

	@FXML
	private ComboBox<String> cbAge;

	@FXML
	private Label lbFilter;

	@FXML
	private ComboBox<String> cbGender;

	@FXML
	private Label lbTotal;

	@FXML
	private ComboBox<String> cbEarTag;

	@FXML
	private JFXButton btnExcel;

	@FXML
	private JFXButton btnExplore;

	@FXML
	private Label lbLabel;

	@FXML
	private JFXButton btnPDF;

	@FXML
	private Label lbActive;

	@FXML
	private ComboBox<String> cbActive;

	@FXML
	private ImageView ivImage;

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private JFXButton btnNew;

	@FXML
	private Label lbEarTag;

	@FXML
	private Label lbAge;

	@FXML
	private TableView<Livestock> tvTable;

	@FXML
	private ComboBox<Animal> cbAnimal;

	@FXML
	private Label lbGender;

	@FXML
	private TextField tfMonths;

	@FXML
	private Label lbAnimal;

	@FXML
	private Label lbMonths;

	private javafx.scene.image.Image gato;
	private AnimalDAO ca;
	private LivestockDAO cl;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		ca = ctx.getBean(AnimalDAO.class);
		cl = ctx.getBean(LivestockDAO.class);

		local = ResourceManager.localizer;
		gato = new javafx.scene.image.Image("/com/marmar/farmapp/images/gato.png");
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_cowhead.png"));

		setLabels();
		// fillCombos();
		fillTable(true);
	}

	public void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbActive.setText(msg.getString("label.active") + ":");
		lbAge.setText(msg.getString("label.age") + ":");
		lbAnimal.setText(msg.getString("label.animal.type") + ":");
		lbEarTag.setText(msg.getString("label.ear.tag") + ":");
		lbFilter.setText(msg.getString("label.apply.filters") + ":");
		lbGender.setText(msg.getString("label.gender") + ":");
		lbLabel.setText(msg.getString("item.livestock_inventory") + ":");
		lbMonths.setText(msg.getString("label.months") + ":");
		lbTotal.setText(msg.getString("label.number.livestock.table") + ":");

		btnExcel.setText(msg.getString("button.excel"));
		btnExplore.setText(msg.getString("button.explore.selection"));
		btnNew.setText(msg.getString("button.create.new"));
		btnPDF.setText(msg.getString("button.pdf"));
		btnRefresh.setText(msg.getString("button.refresh"));

		ObservableList<Livestock> data = tvTable.getItems();
		tvTable.getColumns().clear();
		createColumn(tvTable, msg.getString("label.id"), "id_livestock", 50, gato);
		createColumn(tvTable, msg.getString("label.ear.tag"), "ear_ring", 130, gato);
		createColumn(tvTable, msg.getString("label.gender"), "sex", 100, gato);
		createColumn(tvTable, msg.getString("label.name"), "name", 100, gato);
		createColumn(tvTable, msg.getString("label.color"), "color", 100, gato);
		createColumn(tvTable, msg.getString("label.status"), "status", 100, gato);
		createColumn(tvTable, msg.getString("label.birth.date"), "date_birth", 130, gato);
		createColumn(tvTable, msg.getString("label.age"), "age", 50, gato);
		createColumn(tvTable, msg.getString("label.death_date"), "date_death", 110, gato);
		createColumn(tvTable, msg.getString("label.breed"), "id_race_1", 150, gato);
		createColumn(tvTable, msg.getString("label.breed") + " 2", "id_race_2", 150, gato);
		createColumn(tvTable, msg.getString("item.farm"), "ranch", 150, gato);
		createColumn(tvTable, msg.getString("label.active"), "active", 100, gato);
		createColumn(tvTable, msg.getString("label.branded"), "branded", 130, gato);
		tvTable.setItems(data);
		lbTotal.setText(msg.getString("label.number.livestock.table") + ": " + data.size());

		// fill the combo boxes
		fillCombos();
	}

	@FXML
	void handleExplore(ActionEvent event) throws IOException {
		Livestock u = tvTable.getSelectionModel().getSelectedItem();
		if (u != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/LivestockEdit.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Livestock Window");
			Parent root = (Parent) loader.load();
			root.getStylesheets().add(ResourceManager.currentCSS);
			stage.setScene(new Scene(root));
			LivestockEditController controller = loader.<LivestockEditController>getController();
			stage.show();
			controller.initData(u);
		}
	}

	@FXML
	void handleNew(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/LivestockEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Livestock Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleRefresh(ActionEvent event) {
		fillTable(true);
	}

	@FXML
	void handlePDF(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to PDF File");
		fileChooser.setInitialFileName("Livestock.pdf");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {

				Animal animal = cbAnimal.getSelectionModel().getSelectedItem();
				int sexo = cbGender.getSelectionModel().getSelectedIndex();
				int activo = cbActive.getSelectionModel().getSelectedIndex();
				int arete = cbEarTag.getSelectionModel().getSelectedIndex();
				int edad = cbAge.getSelectionModel().getSelectedIndex();
				int meses = 0;
				try {
					meses = Integer.parseInt(tfMonths.getText());
				} catch (Exception e) {
					System.out.println("es no es un entero!!! >:(");
				}

				ArrayList<Livestock> bovinos = cl.getLivestockFiltred(sexo, activo, arete, animal, edad, meses, false);
				ArrayList<Writable> to_write_list = new ArrayList<>(bovinos.size());
				for (int i = 0; i < bovinos.size(); i++) {
					to_write_list.add(bovinos.get(i));
				}
				new ReportManager().writeReportFromWriteList(to_write_list, "Livestock", file, true);

			} catch (Exception ex) {
				System.out.println("Exception=" + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	@FXML
	void handleExcel(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to Excel File");
		fileChooser.setInitialFileName("Livestock.xls");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				ArrayList<Livestock> list = cl.getAll();
				ArrayList<Writable> to_write_list = new ArrayList<>(list.size());
				for (int i = 0; i < list.size(); i++) {
					to_write_list.add(list.get(i));
				}
				XLSWriter xw = new XLSWriter();
				xw.writeXLS(to_write_list, file.getAbsolutePath(), file.getName());// "Users"

			} catch (Exception ex) {
				System.out.println("Exception=" + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	public void fillCombos() {

		ResourceBundle msg = local.getMessages();

		// llenar el combobox de animales
		ObservableList<Animal> animalData = FXCollections.observableArrayList();
		ArrayList<Animal> allAni = ca.getAll();
		allAni.stream().forEach((alraza1) -> {
			animalData.add(alraza1);
		});
		cbAnimal.setItems(animalData);
		cbAnimal.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Animal> o, Animal ov, Animal nv) -> {
					fillTable(true);
				});

		// set the tf to zero
		tfMonths.setText("0");

		// llenar el combobox de sexos
		ObservableList<String> sexos = FXCollections.observableArrayList(msg.getString("filter.both"),
				msg.getString("filter.male"), msg.getString("filter.female"));
		cbGender.setItems(sexos);
		cbGender.setValue(sexos.get(0));
		cbGender.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					fillTable(true);
				});

		// llenar el combobox de actividad
		ObservableList<String> actividad = FXCollections.observableArrayList(msg.getString("filter.active.all"),
				msg.getString("filter.active.only"), msg.getString("filter.archived.only"));
		cbActive.setItems(actividad);
		cbActive.setValue(actividad.get(1));
		cbActive.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					fillTable(true);
				});

		// llenar el combobox de aretados
		ObservableList<String> aretados = FXCollections.observableArrayList(msg.getString("filter.both"),
				msg.getString("filter.registered.only"), msg.getString("filter.notregistered.only"));
		cbEarTag.setItems(aretados);
		cbEarTag.setValue(aretados.get(0));
		cbEarTag.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					fillTable(true);
				});

		// llenar el combobox de edades
		ObservableList<String> edades = FXCollections.observableArrayList(msg.getString("filter.allages"),
				msg.getString("filter.greater"), msg.getString("filter.lesser"));
		cbAge.setItems(edades);
		cbAge.setValue(edades.get(0));
		cbAge.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					fillTable(true);
				});
	}

	private void fillTable(boolean refresh) {
		if (refresh) {
			// actualizar la tabla de livestock
			Animal animal = cbAnimal.getSelectionModel().getSelectedItem();
			int sexo = cbGender.getSelectionModel().getSelectedIndex();
			int activo = cbActive.getSelectionModel().getSelectedIndex();
			int arete = cbEarTag.getSelectionModel().getSelectedIndex();
			int edad = cbAge.getSelectionModel().getSelectedIndex();
			int meses = 0;
			try {
				meses = Integer.parseInt(tfMonths.getText());
			} catch (Exception e) {
				System.out.println("es no es un entero!!! >:(");
			}

			ArrayList<Livestock> bovinos = cl.getLivestockFiltred(sexo, activo, arete, animal, edad, meses, false);
			tvTable.getItems().clear();
			tvTable.getItems().addAll(bovinos);

			lbTotal.setText(local.getMessages().getString("label.number.livestock.table") + ": " + bovinos.size());
			return;
		}

		// get all the data
		ArrayList<Livestock> livestock = cl.getAll();
		ObservableList<Livestock> data = FXCollections.observableArrayList();
		livestock.stream().forEach((obj) -> {
			data.add(obj);
		});
		tvTable.setItems(data);
	}

	@SuppressWarnings("rawtypes")
	private void createColumn(TableView tv, String name, String property, int width, Image img) {
		TableColumn<Object, Object> col = new TableColumn(name);
		col.setMinWidth(width);
		col.setCellValueFactory(new PropertyValueFactory<>(property));
		ImageView iv = new ImageView(img);
		iv.setFitHeight(30);
		iv.setFitWidth(30);
		col.setGraphic(iv);

		tv.getColumns().add(col);
	}

	// @SuppressWarnings("rawtypes")
	// private void createActionColumn() {
	// TableColumn colAction = new TableColumn<>("Action");
	// colAction.setCellValueFactory(
	// new Callback<TableColumn.CellDataFeatures<Object, Boolean>,
	// ObservableValue<Boolean>>() {
	// @Override
	// public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object,
	// Boolean> p) {
	// return new SimpleBooleanProperty(p.getValue() != null);
	// }
	// });
	// colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>,
	// TableCell<Object, Boolean>>() {
	// @Override
	// public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
	// return new ButtonCell(tvTable);
	// }
	// });
	// ImageView iv = new ImageView(gato);
	// iv.setFitHeight(30);
	// iv.setFitWidth(30);
	// colAction.setGraphic(iv);
	// tvTable.getColumns().add(colAction);
	// }
	//
	// @SuppressWarnings("rawtypes")
	// private class ButtonCell extends TableCell<Object, Boolean> {
	// final Hyperlink cellButtonDelete = new Hyperlink("Delete");
	// final HBox hb = new HBox(cellButtonDelete);
	//
	// ButtonCell(final TableView tblView) {
	// hb.setSpacing(4);
	// cellButtonDelete.setOnAction((ActionEvent t) -> {
	// // status = 1;
	// int row = getTableRow().getIndex();
	// tvTable.getSelectionModel().select(row);
	// // aksiKlikTableData(null);
	// Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure
	// Delete Data ?");
	// alert.initStyle(StageStyle.UTILITY);
	// Optional<ButtonType> result = alert.showAndWait();
	// if (result.get() == ButtonType.OK) {
	// // delete
	// } else {
	// // dont delete
	// }
	// });
	// }
	//
	// @Override
	// protected void updateItem(Boolean t, boolean empty) {
	// super.updateItem(t, empty);
	// if (!empty) {
	// setGraphic(hb);
	// } else {
	// setGraphic(null);
	// }
	// }
	// }
}
