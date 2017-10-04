package com.marmar.farmapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.EventDAO;
import com.marmar.farmapp.hibernate.EventTypeDAO;
import com.marmar.farmapp.model.Event;
import com.marmar.farmapp.model.EventType;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("unchecked")
public class EventController implements Initializable {

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
	private ListView<String> lvList;

	@FXML
	private TableView<Event> tvTable;

	@FXML
	private ImageView ivImage;

	private javafx.scene.image.Image gato;
	private EventTypeDAO cet;
	private EventDAO ce;

	private HashMap<String, Image> map;
	ArrayList<EventType> eventTypeData;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		cet = ctx.getBean(EventTypeDAO.class);
		ce = ctx.getBean(EventDAO.class);
		local = ResourceManager.localizer;

		// set the labels
		setLabels();
		
		gato = new javafx.scene.image.Image("/com/marmar/farmapp/images/gato.png");
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_event.png"));

		// set the style to the list view
		lvList.getStylesheets().add(ResourceManager.listCSS);

		fillList();
		
		//hide the refresh button
//		btnRefresh.setDisable(true);
		btnRefresh.setVisible(false);
	}
	
	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbLabel.setText(msg.getString("label.registered.events") + ":");

		btnRefresh.setText(msg.getString("button.refresh"));
		btnExplore.setText(msg.getString("button.explore.selection"));
		btnNew.setText(msg.getString("button.create.new"));
	}

	@FXML
	void handleExplore(ActionEvent event) throws IOException {
		Event u = tvTable.getSelectionModel().getSelectedItem();
		if (u != null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/EventEdit.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("Event Window");
			Parent root = (Parent) loader.load();
			root.getStylesheets().add(ResourceManager.metroCSS);
			stage.setScene(new Scene(root));
			EventEditController controller = loader.<EventEditController> getController();
			stage.show();
			controller.initData(u);
		}
	}

	@FXML
	void handleNew(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/EventEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Event Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.metroCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleRefresh(ActionEvent event) {
		// fillList();
	}

	@FXML
	void handlePDF(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Export to PDF File");
		fileChooser.setInitialFileName("Events.pdf");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {

				ArrayList<Event> list = ce.getAll();
				ArrayList<Writable> to_write_list = new ArrayList<>(list.size());
				for (int i = 0; i < list.size(); i++) {
					to_write_list.add(list.get(i));
				}
				new ReportManager().writeReportFromWriteList(to_write_list, "Registered Events", file,false);

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
		fileChooser.setInitialFileName("Events.xls");
		File file = fileChooser.showSaveDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				ArrayList<Event> list = ce.getAll();
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

	public void fillList() {

		// get all the data
		eventTypeData = cet.getAll();

		// fill the list strings
		ObservableList<String> data = FXCollections.observableArrayList();
		for (int i = 0; i < eventTypeData.size(); i++) {
			data.add(eventTypeData.get(i).getName());
		}

		// create the map of the images
		map = new HashMap<>(eventTypeData.size());
		EventType e;
		for (int i = 0; i < eventTypeData.size(); i++) {
			e = eventTypeData.get(i);
			map.put(e.getName(), e.getImage());
		}

		lvList.setItems(data);
		lvList.setCellFactory(listview -> new StringImageCell());
		lvList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					fillTable(false);
				});
		lvList.getSelectionModel().select(0);
	}

	private void fillTable(boolean all) {
		ArrayList<Event> allData;

		if (all) {
			// fill the table with all the data
			// get all the data
			allData = ce.getAll();
		} else {
			// fill the table with only the selected filter
			int index = lvList.getSelectionModel().getSelectedIndex();
			EventType et = eventTypeData.get(index);
			allData = ce.getEventType(et);
		}

		ObservableList<Event> data = FXCollections.observableArrayList();
		allData.stream().forEach((obj) -> {
			data.add(obj);
		});

		tvTable.getColumns().clear();

		ResourceBundle msg = local.getMessages();

//		createActionColumn();
		createColumn(tvTable, msg.getString("label.id"), "id_event", 50, gato);
		createColumn(tvTable, msg.getString("label.event.type"), "eventType", 150, gato);
		createColumn(tvTable, msg.getString("label.date.event"), "date_event", 150, gato);
		createColumn(tvTable, msg.getString("label.total"), "total_amount", 100, gato);
		createColumn(tvTable, msg.getString("label.stakeholder"), "stakeholder", 150, gato);
		createColumn(tvTable, msg.getString("label.stakeholder.contact"), "stakeholder_contact", 200, gato);
		createColumn(tvTable, msg.getString("label.notes"), "comments", 200, gato);
		createColumn(tvTable, msg.getString("item.farm"), "ranch", 150, gato);

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

//	@SuppressWarnings("rawtypes")
//	private void createActionColumn() {
//		TableColumn colAction = new TableColumn<>("Action");
//		colAction.setCellValueFactory(
//				new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
//					@Override
//					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
//						return new SimpleBooleanProperty(p.getValue() != null);
//					}
//				});
//		colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
//			@Override
//			public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
//				return new ButtonCell(tvTable);
//			}
//		});
//		ImageView iv = new ImageView(gato);
//		iv.setFitHeight(30);
//		iv.setFitWidth(30);
//		colAction.setGraphic(iv);
//		tvTable.getColumns().add(colAction);
//	}

//	@SuppressWarnings("rawtypes")
//	private class ButtonCell extends TableCell<Object, Boolean> {
//		final Hyperlink cellButtonDelete = new Hyperlink("Delete");
//		final HBox hb = new HBox(cellButtonDelete);
//
//		ButtonCell(final TableView tblView) {
//			hb.setSpacing(4);
//			cellButtonDelete.setOnAction((ActionEvent t) -> {
//				// status = 1;
//				int row = getTableRow().getIndex();
//				tvTable.getSelectionModel().select(row);
//				// aksiKlikTableData(null);
//				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure Delete Data  ?");
//				alert.initStyle(StageStyle.UTILITY);
//				Optional<ButtonType> result = alert.showAndWait();
//				if (result.get() == ButtonType.OK) {
//					// delete
//				} else {
//					// dont delete
//				}
//			});
//		}

//		@Override
//		protected void updateItem(Boolean t, boolean empty) {
//			super.updateItem(t, empty);
//			if (!empty) {
//				setGraphic(hb);
//			} else {
//				setGraphic(null);
//			}
//		}
//	}

	// A Custom ListCell that displays an image and string
	private class StringImageCell extends ListCell<String> {

		Label label;

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (item == null || empty) {
				setItem(null);
				setGraphic(null);
			} else {
				setText(item);
				// setTextFill(Color.WHITE);
				ImageView image = getImageView(item);
				label = new Label("", image);
				setGraphic(label);
			}
		}

		private ImageView getImageView(String imageName) {
			Image image = map.get(imageName);

			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(40);
			imageView.setFitHeight(40);

			return imageView;
		}

	}
}