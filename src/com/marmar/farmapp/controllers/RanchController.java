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
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Ranch;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class RanchController implements Initializable {

	@FXML
	private JFXButton btnLogo;

	@FXML
	private JFXButton btnIron;

	@FXML
	private TextArea taAddress;

	@FXML
	private TextField tfPhone;

	@FXML
	private Label lbLabel;

	@FXML
	private Label lbRFC;

	@FXML
	private TextField tfName;

	@FXML
	private TextField tfEmail;

	@FXML
	private Label lbPhone;

	@FXML
	private ImageView ivRanch;

	@FXML
	private Label lbIron;

	@FXML
	private TextField tfRFC;

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private JFXButton btnNew;

	@FXML
	private Label lbName;

	@FXML
	private Label lbLogo;

	@FXML
	private ImageView ivLogo;

	@FXML
	private ImageView ivIron;

	@FXML
	private Label lbAddress;

	@FXML
	private Label lbEmail;

	private RanchDAO cr;
	private Localizer local;
	private byte[] imageLogo;
	private byte[] imageIron;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		cr = ctx.getBean(RanchDAO.class);
		ivRanch.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_farm.png"));
		local = ResourceManager.localizer;
		
		//set the labels
		setLabels();
		
		handleRefresh(null);
	}
	
	private void setLabels(){
		ResourceBundle msg = local.getMessages();
		
		lbName.setText(msg.getString("label.farm.name") + "*:");
		lbRFC.setText(msg.getString("label.farm.rfc") + "*:");
		lbEmail.setText(msg.getString("label.farm.email") + ":");
		lbPhone.setText(msg.getString("label.farm.phone") + ":");
		lbAddress.setText(msg.getString("label.farm.address") + ":");
		lbLogo.setText(msg.getString("label.farm.logo") + ":");
		lbIron.setText(msg.getString("label.farm.iron") + ":");
		lbLabel.setText(msg.getString("label.farm.information") + ":");
		
		btnLogo.setText(msg.getString("button.choose.image") );
		btnIron.setText(msg.getString("button.choose.image"));
		btnRefresh.setText(msg.getString("button.refresh"));
		btnNew.setText(msg.getString("button.save"));
	}

	@FXML
	void handleNew(ActionEvent event) {
		String name = tfName.getText();
		String rfc = tfRFC.getText();
		String email = tfEmail.getText();
		String address = taAddress.getText();
		String phone = tfPhone.getText();

		Ranch r = cr.getAll().get(0);
		if (r == null) {
			// create a new one
			r = new Ranch(name, rfc, address, phone, email);
			r.setLogo(imageLogo);
			r.setIron(imageIron);

			cr.saveNew(r);
		} else {
			// update existing
			r.setName(name);
			r.setRfc(rfc);
			r.setEmail(email);
			r.setAddress(address);
			r.setPhone(phone);
			// r.setArea(a);

			r.setLogo(imageLogo);
			r.setIron(imageIron);

			cr.update(r);
		}
	}

	@FXML
	void handleRefresh(ActionEvent event) {
		Ranch r = cr.getAll().get(0);
		if (r != null) {
			tfName.setText(r.getName());
			tfRFC.setText(r.getRfc());
			tfEmail.setText(r.getEmail());
			taAddress.setText(r.getAddress());
			tfPhone.setText(r.getPhone());
			// tfArea.setText(r.getArea() + "");

			imageLogo = r.getLogo();
			ivLogo.setImage(prepImage(imageLogo));

			imageIron = r.getIron();
			ivIron.setImage(prepImage(imageIron));
		}

	}

	@FXML
	void handleImageLogo(ActionEvent event) {

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
				imageLogo = data;
				ivLogo.setImage(ImageManager.byteArraytoFXImage(data));
			} catch (IOException | java.lang.IllegalArgumentException e) {
				System.out.println("Error en cargar imagen " + e);
			}

		}
	}

	@FXML
	void handleImageIron(ActionEvent event) {

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
				imageIron = data;
				ivIron.setImage(ImageManager.byteArraytoFXImage(data));
			} catch (IOException | java.lang.IllegalArgumentException e) {
				System.out.println("Error en cargar imagen " + e);
			}

		}
	}

	private Image prepImage(byte[] foto) {

		if (foto == null || foto.length < 1) {
			return new Image("/com/marmar/farmapp/images/empty.png");
		}
		try {
			return ImageManager.byteArraytoFXImage(foto);
		} catch (IOException e) {
			System.out.println("Error en covertir imagen " + e);
			return new Image("/com/marmar/farmapp/images/empty.png");
		}
	}

}
