package com.marmar.farmapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.util.ImageTableCell;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;
import com.marmar.farmapp.util.writers.ReportManager;
import com.marmar.farmapp.util.writers.Writable;
import com.marmar.farmapp.util.writers.XLSWriter;

import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

@SuppressWarnings("unchecked")
public class AnimalController implements Initializable {

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnExport;

	@FXML
	private TableView<Animal> tvTable;

	@FXML
	private ComboBox<?> cbCombo;

	@FXML
	private ImageView ivImage;

	private javafx.scene.image.Image gato;
	private AnimalDAO ca;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		ca = ctx.getBean(AnimalDAO.class);
		local = ResourceManager.localizer;

		gato = new javafx.scene.image.Image("/com/marmar/farmapp/images/gato.png");
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_cowhead.png"));

		setLabels();
		fillTable(false);
	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbLabel.setText(msg.getString("label.registered.animal.types") + ":");

		btnExcel.setText(msg.getString("button.excel"));
		btnExplore.setText(msg.getString("button.explore.selection"));
		btnNew.setText(msg.getString("button.create.new"));
		btnPDF.setText(msg.getString("button.pdf"));
		btnRefresh.setText(msg.getString("button.refresh"));
	}

	@FXML
	void handleExport(ActionEvent event) {

	}

	@FXML
	void handleExplore(ActionEvent event) throws IOException {
		Animal u = tvTable.getSelectionModel().getSelectedItem();
		if (u != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/AnimalEdit.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("User Window");
			Parent root = (Parent) loader.load();
			root.getStylesheets().add(ResourceManager.metroCSS);
			stage.setScene(new Scene(root));
			AnimalEditController controller = loader.<AnimalEditController> getController();
			stage.show();
			controller.initData(u);
		}
	}

	@FXML
	void handleNew(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/AnimalEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Event Type Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.metroCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleRefresh(ActionEvent event) {
		fillTable(true);
	}

	private void fillTable(boolean refresh) {

		if (refresh) {
			// actualizar la tabla
			ArrayList<Animal> data = ca.getAll();
			tvTable.getItems().clear();
			tvTable.getItems().addAll(data);
			return;
		}

		ObservableList<Animal> animalData = FXCollections.observableArrayList();
		ArrayList<Animal> alraza = ca.getAll();
		alraza.stream().forEach((alraza1) -> {
			animalData.add(alraza1);
		});

		tvTable.getColumns().clear();

		ResourceBundle msg = local.getMessages();

		createActionColumn();
		createColumn(tvTable, msg.getString("label.id"), "id_animal", 50, gato);
		createImageColum();
		createColumn(tvTable, msg.getString("label.name"), "name", 100, gato);
		createColumn(tvTable, msg.getString("label.description"), "description", 200, gato);

		createColumn(tvTable, msg.getString("label.au_infant"), "au_infant", 150, gato);
		createColumn(tvTable, msg.getString("label.au_juvenile"), "au_juvenile", 150, gato);
		createColumn(tvTable, msg.getString("label.au_adult_fem"), "au_adult_female", 150, gato);
		createColumn(tvTable, msg.getString("label.au_adult_male"), "au_adult_male", 150, gato);
		createColumn(tvTable, msg.getString("label.months_to_juvenile"), "to_juvenile", 150, gato);
		createColumn(tvTable, msg.getString("label.months_to_adult"), "to_adult", 150, gato);

		tvTable.setItems(animalData);
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

	@SuppressWarnings("rawtypes")
	private void createActionColumn() {
		ResourceBundle msg = local.getMessages();
		TableColumn colAction = new TableColumn<>(msg.getString("item.action"));
		colAction.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
						return new SimpleBooleanProperty(p.getValue() != null);
					}
				});
		colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
			@Override
			public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
				return new ButtonCell(tvTable);
			}
		});
		ImageView iv = new ImageView(gato);
		iv.setFitHeight(30);
		iv.setFitWidth(30);
		colAction.setGraphic(iv);
		tvTable.getColumns().add(colAction);
	}

	private void createImageColum() {
		ResourceBundle msg = local.getMessages();
		TableColumn<Animal, Image> imageColumn = new TableColumn<>(msg.getString("label.picture"));
		imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
		imageColumn.setCellFactory(param -> new ImageTableCell<>());
		ImageView iv = new ImageView(gato);
		iv.setFitHeight(30);
		iv.setFitWidth(30);
		imageColumn.setGraphic(iv);
		tvTable.getColumns().add(imageColumn);
	}

	@SuppressWarnings("rawtypes")
	private class ButtonCell extends TableCell<Object, Boolean> {
		ResourceBundle msg = local.getMessages();
		final Hyperlink cellButtonDelete = new Hyperlink(msg.getString("item.delete"));
		final HBox hb = new HBox(cellButtonDelete);

		ButtonCell(final TableView tblView) {
			hb.setSpacing(4);
			cellButtonDelete.setOnAction((ActionEvent t) -> {
				// status = 1;
				int row = getTableRow().getIndex();
				tvTable.getSelectionModel().select(row);
				Animal a = tvTable.getSelectionModel().getSelectedItem();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure, Delete " + a.getName() + "  ?");
				alert.initStyle(StageStyle.UTILITY);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					// delete
					ca.delete(a);
					handleRefresh(null);
				} else {
					// dont delete
				}
			});
		}

		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
			if (!empty) {
				setGraphic(hb);
			} else {
				setGraphic(null);
			}
		}
	}

	@FXML
	private JFXButton btnExcel;

	@FXML
	private JFXButton btnExplore;

	@FXML
	private Label lbLabel;

	@FXML
	private JFXButton btnPDF;

	@FXML
	void handlePDF(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to PDF File");
		fileChooser.setInitialFileName("Animals.pdf");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {

				ArrayList<Animal> list = ca.getAll();
				ArrayList<Writable> to_write_list = new ArrayList<>(list.size());
				for (int i = 0; i < list.size(); i++) {
					to_write_list.add(list.get(i));
				}
				new ReportManager().writeReportFromWriteList(to_write_list, "Animals", file, false);

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
		fileChooser.setInitialFileName("AnimalTypes.xls");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				ArrayList<Animal> list = ca.getAll();
				ArrayList<Writable> to_write_list = new ArrayList<>(list.size());
				for (int i = 0; i < list.size(); i++) {
					to_write_list.add(list.get(i));
				}
				XLSWriter xw = new XLSWriter();
				xw.writeXLS(to_write_list, file.getAbsolutePath(), file.getName());// "Users"

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

}
