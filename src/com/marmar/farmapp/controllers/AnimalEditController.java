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
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AnimalEditController implements Initializable {

	@FXML
	private Label lbAUAdultMale;

	@FXML
	private Label lbDescription;

	@FXML
	private Label lbLabel;

	@FXML
	private TextField tfName;

	@FXML
	private Label lbAUInfant;

	@FXML
	private TextField tfDescription;

	@FXML
	private TextField tfAdult;

	@FXML
	private JFXButton btnImage;

	@FXML
	private ImageView ivImage;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private Label lbAUJuvenile;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label lbName;

	@FXML
	private Label lbAUAdultFemale;

	@FXML
	private TextField tfAUInfant;

	@FXML
	private Label lbImage;

	@FXML
	private Label lbJuvenile;

	@FXML
	private TextField tfJuvenile;

	@FXML
	private TextField tfAUAdultFemale;

	@FXML
	private Label lbAdult;

	@FXML
	private TextField tfAUAdultMale;

	@FXML
	private TextField tfAUJuvenile;

	private byte[] image;
	private boolean update = false;
	private Animal animal;
	private AnimalDAO ca;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		ca = ctx.getBean(AnimalDAO.class);
		
		local = ResourceManager.localizer;
		setLabels();
	}
	
	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbName.setText(msg.getString("label.name") + "*:");
		lbDescription.setText(msg.getString("label.description") + ":");
		lbLabel.setText(msg.getString("label.create.animal_type") + ":");
		lbImage.setText(msg.getString("label.picture") + ":");
		
		lbJuvenile.setText(msg.getString("label.months_to_juvenile") + ":");
		lbAdult.setText(msg.getString("label.months_to_adult") + ":");
		lbAUInfant.setText(msg.getString("label.au_infant") + ":");
		lbAUJuvenile.setText(msg.getString("label.au_juvenile") + ":");
		lbAUAdultFemale.setText(msg.getString("label.au_adult_fem") + ":");
		lbAUAdultMale.setText(msg.getString("label.au_adult_male") + ":");

		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));
		btnImage.setText(msg.getString("button.choose.image"));
	}

	public void initData(Animal a) {
		this.animal = a;
		tfName.setText(a.getName());
		tfDescription.setText(a.getDescription());

		byte[] img = a.getImg();
		if (img != null) {
			image = img;
			try {
				ivImage.setImage(ImageManager.byteArraytoFXImage(image));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		tfJuvenile.setText(a.getTo_juvenile()+"");
		tfAdult.setText(a.getTo_adult()+"");
		
		tfAUInfant.setText(a.getAu_infant()+"");
		tfAUJuvenile.setText(a.getAu_juvenile()+"");
		tfAUAdultFemale.setText(a.getAu_adult_female()+"");
		tfAUAdultMale.setText(a.getAu_adult_male()+"");
		
//		lbLabel.setText("Edit Animal Type:");
		ResourceBundle msg = local.getMessages();
		lbLabel.setText(msg.getString("label.detail.animal_type") + ":");
		
		update = true;
	}

	@FXML
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleSave(ActionEvent event) {
		if (animal == null) {
			System.out.println("animal =" + animal);
			animal = new Animal();
		}
		animal.setName(tfName.getText());
		animal.setDescription(tfDescription.getText());

		if (image != null) {
			animal.setImg(image);
		}

		animal.setTo_juvenile(getInteger(tfJuvenile.getText()));
		animal.setTo_adult(getInteger(tfAdult.getText()));
		
		animal.setAu_infant(getDouble(tfAUInfant.getText()));
		animal.setAu_juvenile(getDouble(tfAUJuvenile.getText()));
		animal.setAu_adult_female(getDouble(tfAUAdultFemale.getText()));
		animal.setAu_adult_male(getDouble(tfAUAdultMale.getText()));
		
		System.out.println("update =" + update);
		if (update) {
			ca.update(animal);
		} else {
			ca.saveNew(animal);
		}
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	private int getInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			System.out.println("Error this is not an int: " + s);
			return 0;
		}
	}

	private double getDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			System.out.println("Error this is not a double: " + s);
			return 0.0;
		}
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
