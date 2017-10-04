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
import com.marmar.farmapp.hibernate.HoldingAreaDAO;
import com.marmar.farmapp.model.HoldingArea;
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
public class PensController implements Initializable {

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private JFXButton btnNew;

	@FXML
	private JFXButton btnExcel;

	@FXML
	private JFXButton btnExplore;

	@FXML
	private Label lbLabel;

	@FXML
	private JFXButton btnPDF;

	@FXML
	private TableView<HoldingArea> tvTable;

	@FXML
	private ImageView ivImage;

	private javafx.scene.image.Image gato;
	private HoldingAreaDAO crud;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		crud = ctx.getBean(HoldingAreaDAO.class);
		local = ResourceManager.localizer;

		gato = new javafx.scene.image.Image("/com/marmar/farmapp/images/gato.png");
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_pen.png"));

		setLabels();
		fillTable(false);
	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbLabel.setText(msg.getString("label.holding_area") + ":");

		btnExcel.setText(msg.getString("button.excel"));
		btnExplore.setText(msg.getString("button.explore.selection"));
		btnNew.setText(msg.getString("button.create.new"));
		btnPDF.setText(msg.getString("button.pdf"));
		btnRefresh.setText(msg.getString("button.refresh"));
	}

	private void fillTable(boolean refresh) {
		if (refresh) {
			// actualizar la tabla
			ArrayList<HoldingArea> data = crud.getAll();
			tvTable.getItems().clear();
			tvTable.getItems().addAll(data);
			return;
		}

		ObservableList<HoldingArea> holdingData = FXCollections.observableArrayList();
		ArrayList<HoldingArea> array = crud.getAll();
		array.stream().forEach((alraza1) -> {
			holdingData.add(alraza1);
		});

		tvTable.getColumns().clear();

		ResourceBundle msg = local.getMessages();

		createActionColumn();
		createColumn(tvTable, msg.getString("label.id"), "id_hold", 50, gato);
		createColumn(tvTable, msg.getString("label.name"), "name", 100, gato);
		createColumn(tvTable, msg.getString("label.description"), "description", 266, gato);
		createColumn(tvTable, msg.getString("item.farm"), "ranch", 150, gato);
		createColumn(tvTable, msg.getString("label.au_capacity"), "au_capacity", 150, gato);
		createColumn(tvTable, msg.getString("label.area"), "area", 150, gato);

		tvTable.setItems(holdingData);
	}

	@FXML
	void handleExplore(ActionEvent event) throws IOException {
		HoldingArea u = tvTable.getSelectionModel().getSelectedItem();
		if (u != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/PenEdit.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Holding Area Window");
			Parent root = (Parent) loader.load();
			root.getStylesheets().add(ResourceManager.metroCSS);
			stage.setScene(new Scene(root));
			PenEditController controller = loader.<PenEditController> getController();
			stage.show();
			controller.initData(u);
		}
	}

	@FXML
	void handleNew(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/PenEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Holding Area Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.metroCSS);
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
		fileChooser.setInitialFileName("HoldingAreas.pdf");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {

				ArrayList<HoldingArea> list = crud.getAll();
				ArrayList<Writable> to_write_list = new ArrayList<>(list.size());
				for (int i = 0; i < list.size(); i++) {
					to_write_list.add(list.get(i));
				}
				new ReportManager().writeReportFromWriteList(to_write_list, "HoldingAreas", file, false);

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
				ArrayList<HoldingArea> list = crud.getAll();
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
				HoldingArea a = tvTable.getSelectionModel().getSelectedItem();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure, Delete " + a.getName() + "  ?");
				alert.initStyle(StageStyle.UTILITY);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					// delete
					crud.delete(a);
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
}
