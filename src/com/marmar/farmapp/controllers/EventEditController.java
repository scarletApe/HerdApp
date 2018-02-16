package com.marmar.farmapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.EventDAO;
import com.marmar.farmapp.hibernate.EventTypeDAO;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.hibernate.LivestockEventDAO;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Event;
import com.marmar.farmapp.model.EventType;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.model.LivestockEvent;
import com.marmar.farmapp.model.Ranch;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

@SuppressWarnings("unchecked")
public class EventEditController implements Initializable {

	@FXML
	private TextField tfStake;

	@FXML
	private Label lbTotal;

	@FXML
	private Label lbStake;

	@FXML
	private TextField tfTotal;

	@FXML
	private TextField tfPrice;

	@FXML
	private Label lbContact;

	@FXML
	private Label lbDate;

	@FXML
	private Label lbAdd;

	@FXML
	private TextField tfComment;

	@FXML
	private Label lbImage;

	@FXML
	private Label lbET;

	@FXML
	private TableView<LivestockEvent> tvTable;

	@FXML
	private Label lbFarm;

	@FXML
	private ComboBox<EventType> cbET;

	@FXML
	private Label lbLabel;

	@FXML
	private TextField tfLComment;

	@FXML
	private TextField tfContact;

	@FXML
	private JFXButton btnImage;

	@FXML
	private ImageView ivImage;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnSave;

	@FXML
	private DatePicker dpDate;

	@FXML
	private ComboBox<Livestock> cbLivestock;

	@FXML
	private JFXButton btnAdd;

	@FXML
	private TextField tfFarm;

	@FXML
	private Label lbComment;

	private Ranch ranch;
	private byte[] image;
	private Event event;
	private boolean update = false;

	private RanchDAO cr;
	private EventTypeDAO cet;
	private LivestockDAO cl;
	private EventDAO ce;
	private LivestockEventDAO cle;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		cet = ctx.getBean(EventTypeDAO.class);
		cr = ctx.getBean(RanchDAO.class);
		cl = ctx.getBean(LivestockDAO.class);
		ce = ctx.getBean(EventDAO.class);
		cle = ctx.getBean(LivestockEventDAO.class);

		local = ResourceManager.localizer;
		setLabels();

		ranch = cr.getAll().get(0);
		tfFarm.setText(ranch.getName());
		tfFarm.setDisable(true);

		// llenar el combobox de event types
		ObservableList<EventType> list = FXCollections.observableArrayList();
		ArrayList<EventType> data = cet.getAll();
		data.stream().forEach((obj) -> {
			list.add(obj);
		});
		cbET.setItems(list);
		cbET.setValue(list.get(0));

		// llenar el combobox de livestock
		ObservableList<Livestock> list2 = FXCollections.observableArrayList();
		ArrayList<Livestock> data2 = cl.getLivestockFiltred(0, 1, 0, null, 0, 0,true);
		data2.stream().forEach((obj) -> {
			list2.add(obj);
		});
		cbLivestock.setItems(list2);
		cbLivestock.setValue(list2.get(0));

		// init the table view
		ResourceBundle msg = local.getMessages();
		tvTable.getColumns().clear();
		Image gato = ResourceManager.gato;
		createColumn(tvTable, msg.getString("label.id"), "id_le", 50, gato);
		// createColumn(tvTable, "Event", "event", 100, gato);
		createColumn(tvTable, msg.getString("item.livestock"), "livestock", 400, gato);
		createColumn(tvTable, msg.getString("label.price"), "price", 50, gato);
		createColumn(tvTable, msg.getString("label.comment"), "comment", 200, gato);
	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbLabel.setText(msg.getString("label.create.event") + ":");
		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));

		// Event Details Panel
		tabDetails.setText(msg.getString("label.event.details"));
		lbFarm.setText(msg.getString("item.farm") + ":");
		lbET.setText(msg.getString("label.event.type") + ":");
		lbDate.setText(msg.getString("label.date.event") + ":");
		lbComment.setText(msg.getString("label.comment") + ":");
		lbTotal.setText(msg.getString("label.total") + ":");
		lbStake.setText(msg.getString("label.stakeholder") + ":");
		lbContact.setText(msg.getString("label.stakeholder.contact") + ":");
		lbImage.setText(msg.getString("label.picture") + ":");
		btnImage.setText(msg.getString("button.choose.image"));
		tfStake.setPromptText(msg.getString("msg.stakeholder_example"));
		tfTotal.setPromptText(msg.getString("msg.auto_calculated") + ":");

		// Live at Event tab
		tabAtEvent.setText(msg.getString("label.livestock_event"));
		lbAdd.setText(msg.getString("msg.add") + ":");
		btnAdd.setText(msg.getString("button.add"));
		tfPrice.setPromptText(msg.getString("msg.price"));
		tfLComment.setPromptText(msg.getString("label.comment"));

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

	public void initData(Event e) {
		this.event = e;

		if (e.getRanch() != null) {
			ranch = e.getRanch();
			tfFarm.setText(e.getRanch().getName());
		}
		cbET.setValue(e.getEventType());

		java.sql.Date d = (java.sql.Date) e.getDate_event();
		if (d != null) {
			dpDate.setValue(d.toLocalDate());
		}

		tfComment.setText(e.getComments());
		tfTotal.setText(e.getTotal_amount() + "");
		tfStake.setText(e.getStakeholder());
		tfContact.setText(e.getStakeholder_contact());

		byte[] img = e.getImg();
		if (img != null) {
			image = img;
			try {
				ivImage.setImage(ImageManager.byteArraytoFXImage(image));
			} catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		ResourceBundle msg = local.getMessages();
		lbLabel.setText(msg.getString("label.event.details") + ":");
		update = true;

		// fill the table with the livestock that attended said event
		ObservableList<LivestockEvent> list = FXCollections.observableArrayList();
		ArrayList<LivestockEvent> data = cle.getLivestockAtEvent(e);
		data.stream().forEach((obj) -> {
			list.add(obj);
		});
		tvTable.setItems(list);
	}

	@FXML
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@SuppressWarnings("deprecation")
	@FXML
	void handleSave(ActionEvent e) {
		if (event == null) {
			System.out.println("event =" + event);
			event = new Event();
			event.setRanch(ranch);
		}

		String fecha = dpDate.getEditor().getText();
		event.setDate_event((fecha.isEmpty() ? null : new java.util.Date(fecha)));

		event.setEventType(cbET.getValue());
		event.setComments(tfComment.getText());
		event.setTotal_amount(Double.parseDouble(tfTotal.getText()));
		event.setStakeholder(tfStake.getText());
		event.setStakeholder_contact(tfContact.getText());

		if (image != null) {
			event.setImg(image);
		}
		ObservableList<LivestockEvent> items = tvTable.getItems();
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setEvent(event);
		}

		System.out.println("update =" + update);
		if (update) {
			ce.update(event);
		} else {
			ce.saveNew(event);
		}
		// also save the livestock to event
		for (int i = 0; i < items.size(); i++) {
			cle.update(items.get(i));
		}

		((Node) (e.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");

		// Set extension filter
		List<String> l = new ArrayList<String>();
		l.add("*.png");
		l.add("*.jpg");
		l.add("*.gif");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (png, jpg, gif)", l);
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				byte[] data = com.marmar.farmapp.util.ImageManager.readBytesFromFile(file.getCanonicalPath());
				image = data;
				ivImage.setImage(ImageManager.byteArraytoFXImage(data));
			} catch (IOException | java.lang.IllegalArgumentException e) {
				System.out.println("Error en cargar imagen " + e);
			}

		}
	}

	@FXML
	void handleAdd(ActionEvent event) {
		// get the data
		Livestock livestock = cbLivestock.getValue();

		String p = tfPrice.getText();
		if (p == null || p.isEmpty()) {
			p = "0.0";
		}
		Double price = Double.parseDouble(p);
		String comment = tfLComment.getText();

		// create the object
		LivestockEvent le = new LivestockEvent();
		le.setLivestock(livestock);
		le.setPrice(price);
		le.setComment(comment);

		// set it to the table
		ObservableList<LivestockEvent> items = tvTable.getItems();
		items.add(le);
		tvTable.setItems(items);

		// clear the text fields
		tfPrice.setText("");
		tfLComment.setText("");
	}

	@FXML
	private Tab tabAtEvent;

	@FXML
	private Tab tabDetails;

}
