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
import com.marmar.farmapp.hibernate.RaceDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Race;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class RaceEditController implements Initializable {

	@FXML
	private Label lbDescription;

	@FXML
	private Label lbY;

	@FXML
	private ImageView ivAF;

	@FXML
	private Label lbLabel;

	@FXML
	private ImageView ivAM;

	@FXML
	private TextField tfName;

	@FXML
	private Label lbAF;

	@FXML
	private TextField tfDescription;

	@FXML
	private ImageView ivY;

	@FXML
	private TextField tfCode;

	@FXML
	private Label lbAM;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private Label lbCode;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnY;

	@FXML
	private JFXButton btnAM;

	@FXML
	private Label lbName;

	@FXML
	private ComboBox<Animal> cbAnimal;

	@FXML
	private JFXButton btnAF;

	@FXML
	private Label lbAnimal;

	private byte[] imgY;
	private byte[] imgAF;
	private byte[] imgAM;
	private Race breed;
	private boolean update = false;

	private AnimalDAO ca;
	private RaceDAO cr;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		ca = ctx.getBean(AnimalDAO.class);
		cr = ctx.getBean(RaceDAO.class);

		// llenar el combobox de animales
		ObservableList<Animal> animalData = FXCollections.observableArrayList();
		ArrayList<Animal> allAni = ca.getAll();
		allAni.stream().forEach((alraza1) -> {
			animalData.add(alraza1);
		});
		cbAnimal.setItems(animalData);
		
		local = ResourceManager.localizer;
		setLabels();
	}
	
	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbLabel.setText(msg.getString("label.create.breed") + "*:");
		lbName.setText(msg.getString("label.breed.name") + "*:");
		lbDescription.setText(msg.getString("label.description") + ":");
		lbAnimal.setText(msg.getString("label.animal.type") + ":");
		lbCode.setText(msg.getString("label.code") + ":");
		
		lbY.setText(msg.getString("label.image.young") + ":");
		lbAF.setText(msg.getString("label.image.adu_fem") + ":");
		lbAM.setText(msg.getString("label.image.adu_male") + ":");

		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));
		btnY.setText(msg.getString("button.choose.image"));
		btnAF.setText(msg.getString("button.choose.image"));
		btnAM.setText(msg.getString("button.choose.image"));
	}

	public void initData(Race r) {
		this.breed = r;

		tfName.setText(r.getName());
		tfDescription.setText(r.getDescription());
		tfCode.setText(r.getCode());

		imgY = setImage(r.getImg_young(), ivY);
		imgAF = setImage(r.getImg_female_adult(), ivAF);
		imgAM = setImage(r.getImg_male_adult(), ivAM);

//		lbLabel.setText("Edit Breed:");
		ResourceBundle msg = local.getMessages();
		lbLabel.setText(msg.getString("label.detail.breed") + ":");
		
		update = true;
		cbAnimal.setValue(r.getAnimal());
	}

	private byte[] setImage(byte[] img, ImageView iv) {
		if (img != null) {
			try {
				iv.setImage(ImageManager.byteArraytoFXImage(img));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return img;
		}
		return null;
	}

	@FXML
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleSave(ActionEvent event) {
		if (breed == null) {
			System.out.println("race =" + breed);
			breed = new Race();
		}
		breed.setName(tfName.getText());
		breed.setDescription(tfDescription.getText());
		breed.setCode(tfCode.getText());

		breed.setAnimal(cbAnimal.getSelectionModel().getSelectedItem());

		if (imgY != null) {
			breed.setImg_young(imgY);
		}

		if (imgAF != null) {
			breed.setImg_female_adult(imgAF);
		}

		if (imgAM != null) {
			breed.setImg_male_adult(imgAM);
		}

		System.out.println("update =" + update);
		if (update) {
			cr.update(breed);
		} else {
			cr.saveNew(breed);
		}
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleImageY(ActionEvent event) throws IOException {
		byte[] data = handleImage(event);
		imgY = data;
		if (data != null) {
			ivY.setImage(ImageManager.byteArraytoFXImage(data));
		}
	}

	@FXML
	void handleImageAF(ActionEvent event) throws IOException {
		byte[] data = handleImage(event);
		imgAF = data;
		if (data != null) {
			ivAF.setImage(ImageManager.byteArraytoFXImage(data));
		}
	}

	@FXML
	void handleImageAM(ActionEvent event) throws IOException {
		byte[] data = handleImage(event);
		imgAM = data;
		if (data != null) {
			ivAM.setImage(ImageManager.byteArraytoFXImage(data));
		}
	}

	private byte[] handleImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");

		// Set extension filter
		List<String> filters = new ArrayList<String>();
		filters.add("*.png");
		filters.add("*.jpg");
		filters.add("*.gif");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (png, jpg, gif)", filters);
		fileChooser.getExtensionFilters().add(extFilter);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(((Node) (event.getSource())).getScene().getWindow());
		if (file != null) {
			try {
				byte[] data = com.marmar.farmapp.util.ImageManager.readBytesFromFile(file.getCanonicalPath());
				// image = data;
				// ivImage.setImage(ImageManager.byteArraytoFXImage(data));
				return data;
			} catch (IOException | java.lang.IllegalArgumentException e) {
				System.out.println("Error en cargar imagen " + e);
			}
		}
		return null;
	}

}
