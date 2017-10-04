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
import com.marmar.farmapp.hibernate.EventTypeDAO;
import com.marmar.farmapp.model.EventType;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class EventTypeEditController implements Initializable {

	@FXML
	private Label lbDescription;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private TextArea taDescription;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label lbName;

	@FXML
	private Label lbImage;

	@FXML
	private Label lbLabel;

	@FXML
	private TextField tfName;

	@FXML
	private JFXButton btnImage;

	@FXML
	private ImageView ivImage;

	private byte[] image;
	private boolean update = false;
	private EventType et;
	private EventTypeDAO cet;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		cet = ctx.getBean(EventTypeDAO.class);

		local = ResourceManager.localizer;
		setLabels();
	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbName.setText(msg.getString("label.name") + "*:");
		lbDescription.setText(msg.getString("label.description") + "*:");
		lbLabel.setText(msg.getString("label.create.eventtype") + ":");
		lbImage.setText(msg.getString("label.picture") + ":");

		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));
		btnImage.setText(msg.getString("button.choose.image"));
	}

	public void initData(EventType u) {
		this.et = u;
		tfName.setText(u.getName());
		taDescription.setText(u.getDescription());

		byte[] img = u.getImg();
		if (img != null) {
			image = img;
			try {
				ivImage.setImage(ImageManager.byteArraytoFXImage(image));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ResourceBundle msg = local.getMessages();
		lbLabel.setText(msg.getString("label.detail.eventtype") + ":");
		update = true;
	}

	@FXML
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleSave(ActionEvent event) {
		if (et == null) {
			System.out.println("eventtype =" + et);
			et = new EventType();
		}
		et.setName(tfName.getText());
		et.setDescription(taDescription.getText());

		if (image != null) {
			et.setImg(image);
		}

		System.out.println("update =" + update);
		if (update) {
			cet.update(et);
		} else {
			cet.saveNew(et);
		}
		((Node) (event.getSource())).getScene().getWindow().hide();
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

}
