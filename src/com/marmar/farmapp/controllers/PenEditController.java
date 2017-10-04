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
import com.marmar.farmapp.hibernate.HoldingAreaDAO;
import com.marmar.farmapp.model.HoldingArea;
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

public class PenEditController implements Initializable {
	@FXML
	private Label lbDescription;

	@FXML
	private TextArea taDescription;

	@FXML
	private Label lbLabel;

	@FXML
	private TextField tfName;

	@FXML
	private Label lbArea;

	@FXML
	private Label lbCapacity;

	@FXML
	private JFXButton btnImage;

	@FXML
	private ImageView ivImage;

	@FXML
	private TextField tfArea;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label lbName;

	@FXML
	private TextField tfCapacity;

	@FXML
	private Label lbImage;

	@FXML
	private TextField tfFarm;

	@FXML
	private Label lbFarm;

	private byte[] image;
	private boolean update = false;
	private HoldingArea ha;
	private HoldingAreaDAO haDAO;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		haDAO = ctx.getBean(HoldingAreaDAO.class);

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

	public void initData(HoldingArea u) {
		this.ha = u;
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
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleSave(ActionEvent event) {
		if (ha == null) {
			System.out.println("HoldingArea =" + ha);
			ha = new HoldingArea();
		}
		ha.setName(tfName.getText());
		ha.setDescription(taDescription.getText());

		if (image != null) {
			ha.setImg(image);
		}

		System.out.println("update =" + update);
		if (update) {
			haDAO.update(ha);
		} else {
			haDAO.saveNew(ha);
		}
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

}
