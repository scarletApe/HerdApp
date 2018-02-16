package com.marmar.farmapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.HoldingAreaDAO;
import com.marmar.farmapp.hibernate.LivestockHoldingDAO;
import com.marmar.farmapp.model.HoldingArea;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.model.LivestockHolding;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PenManagerController implements Initializable, Internationable {

	@FXML
	private Label lbNumber;

	@FXML
	private JFXButton btnRefresh;

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
	private TableView<LivestockHolding> tvTable;

	@FXML
	private Label lbWeight;

	@FXML
	private JFXButton btnAddRemove;

	@FXML
	private Label lbCapacity;

	@FXML
	private ImageView ivImage;

	private javafx.scene.image.Image gato;
	private HoldingAreaDAO haCRUD;
	private LivestockHoldingDAO lhCRUD;

	private Localizer local;
	private DecimalFormat df;
	private HashMap<String, Image> map;
	private ArrayList<HoldingArea> holds;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		df = new DecimalFormat("##.##");

		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		haCRUD = ctx.getBean(HoldingAreaDAO.class);
		lhCRUD = ctx.getBean(LivestockHoldingDAO.class);

		local = ResourceManager.localizer;
		gato = ResourceManager.gato;
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_pen.png"));

		// set the style to the list view
		lvList.getStylesheets().add(ResourceManager.listCSS);

		// fill list
		fillList();

		// set the labels and therefore the table
		setLabels();

		// hide the refresh button
		btnRefresh.setVisible(false);
		
		//disable other buttons
		btnPDF.setDisable(true);
		btnExcel.setDisable(true);
	}

	public void setLabels() {
		ResourceBundle msg = local.getMessages();

		// set the labels
		lbLabel.setText(msg.getString("label.registered.pens") + ":");

		// set the button names
		btnRefresh.setText(msg.getString("button.refresh"));
		btnExplore.setText(msg.getString("button.explore.selection"));
		btnAddRemove.setText(msg.getString("button.add_remove"));

		// set the table columns
		ObservableList<LivestockHolding> data = tvTable.getItems();
		tvTable.getColumns().clear();
		createColumn(tvTable, msg.getString("label.id"), "id_lh", 50, gato);
		createColumn(tvTable, msg.getString("label.hold"), "hold", 100, gato);
		createColumn(tvTable, msg.getString("item.livestock"), "livestock", 400, gato);
		createColumn(tvTable, msg.getString("label.date.added"), "date_added", 150, gato);
		createColumn(tvTable, msg.getString("label.duration"), "duration", 100, gato);
		tvTable.setItems(data);
	}

	@FXML
	void handleExplore(ActionEvent event) throws IOException {
		LivestockHolding o = tvTable.getSelectionModel().getSelectedItem();
		if (o != null) {
			Livestock u = o.getLivestock();
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
	void handleAddRemove(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/PenAddRemove.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Holding Area Transfer Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		PenAddRemoveController controller = loader.<PenAddRemoveController>getController();
		stage.show();
		controller.initData(holds.get(lvList.getSelectionModel().getSelectedIndex()));
	}

	@FXML
	void handleRefresh(ActionEvent event) {

	}

	@FXML
	void handlePDF(ActionEvent event) {

	}

	@FXML
	void handleExcel(ActionEvent event) {

	}

	public void fillList() {

		// get all the data
		holds = haCRUD.getAll();

		// fill the list strings
		ObservableList<String> data = FXCollections.observableArrayList();
		for (int i = 0; i < holds.size(); i++) {
			data.add(holds.get(i).getName());
		}

		// create the map of the images
		map = new HashMap<>(holds.size());
		HoldingArea e;
		for (int i = 0; i < holds.size(); i++) {
			e = holds.get(i);
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
		// fill the table with only the selected filter
		int index = lvList.getSelectionModel().getSelectedIndex();
		HoldingArea hold = holds.get(index);
		ArrayList<LivestockHolding> allData = lhCRUD.getLivestockInHold(hold);

		ObservableList<LivestockHolding> data = FXCollections.observableArrayList();
		allData.stream().forEach((obj) -> {
			data.add(obj);
		});
		tvTable.setItems(data);

		ResourceBundle msg = local.getMessages();
		lbCapacity.setText(local.getMessages().getString("label.capacity") + ": " + hold.getAu_capacity());

		lbNumber.setText(msg.getString("label.num_ani_pen") + ": " + data.size());

		double total = getTotalWeight((List<LivestockHolding>) data);
		lbWeight.setText(msg.getString("label.au_load") + ": " + df.format(total));
	}

	private double getTotalWeight(List<LivestockHolding> data) {
		double total = 0;
		for (int i = 0; i < data.size(); i++) {
			total += data.get(i).getLivestock().getAnimalUnit();
		}
		return total;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
