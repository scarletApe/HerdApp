package com.marmar.farmapp.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.hibernate.RaceDAO;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.model.LivestockEvent;
import com.marmar.farmapp.model.Race;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class LivestockEditController implements Initializable {

	@FXML
	private Label lbMother;

	@FXML
	private Label lbDescription;

	@FXML
	private Label lbBirth;

	@FXML
	private Label lbDeathImage;

	@FXML
	private Label lbColor;

	@FXML
	private Label lbFather;

	@FXML
	private JFXButton btnDeath;

	@FXML
	private ImageView ivFather;

	@FXML
	private TextField tfColor;

	@FXML
	private Label lbEarTag;

	@FXML
	private Label lbImage;

	@FXML
	private Label lbDeath;

	@FXML
	private ComboBox<Animal> cbAnimal;

	@FXML
	private Label lbGender;

	@FXML
	private Label lbStatus;

	@FXML
	private Label lbFarm;

	@FXML
	private Label lbAnimal;

	@FXML
	private ComboBox<String> cbGender;

	@FXML
	private ImageView ivMother;

	@FXML
	private Label lbID;

	@FXML
	private Label lbBreed2;

	@FXML
	private Label lbBreed1;

	@FXML
	private Label lbLabel;

	@FXML
	private TextField tfCause;

	@FXML
	private TextField tfName;

	@FXML
	private Label lbCause;

	@FXML
	private TextArea tfDescription;

	@FXML
	private ComboBox<Livestock> cbMother;

	@FXML
	private ComboBox<Livestock> cbFather;

	@FXML
	private JFXButton btnImage;

	@FXML
	private ImageView ivImage;

	@FXML
	private TableView<LivestockEvent> tvEvents;

	@FXML
	private ComboBox<Race> cbBreed1;

	@FXML
	private ImageView ivDeath;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private ComboBox<Race> cbBreed2;

	@FXML
	private TableView<Livestock> tvDescendants;

	@FXML
	private TextField tfStatus;

	@FXML
	private DatePicker dpBirth;

	@FXML
	private JFXButton btnSave;

	@FXML
	private DatePicker dpDeath;

	@FXML
	private Label lbName;

	@FXML
	private TextField tfFarm;

	@FXML
	private TextField tfEarTag;

	@FXML
	private TextField tfID;

	@FXML
	private CheckBox cbActive;

	@FXML
	private JFXButton btnBrand;

	@FXML
	private CheckBox cbBranded;

	@FXML
	private Label lbWeight;

	@FXML
	private TextField tfWeight;

	@FXML
	private Tab tabDetails;

	@FXML
	private Tab tabDeath;

	@FXML
	private Tab tabPedigree;

	@FXML
	private Tab tabOffspring;

	@FXML
	private Tab tabEvents;

	@FXML
	private ImageView ivBrand;

	private byte[] imgAlive;
	private byte[] imgDead;

	private Livestock livestock;
	private boolean update = false;

	private AnimalDAO ca;
	private RaceDAO cr;
	private RanchDAO crr;
	private LivestockDAO cl;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		ca = ctx.getBean(AnimalDAO.class);
		cl = ctx.getBean(LivestockDAO.class);
		cr = ctx.getBean(RaceDAO.class);
		crr = ctx.getBean(RanchDAO.class);

		local = ResourceManager.localizer;
		setLabels();

		// set the ranch
		tfFarm.setText(crr.getAll().get(0).getName());

		// llenar el combobox de sexos
		ObservableList<String> sexos = FXCollections.observableArrayList("Male", "Female");
		cbGender.setItems(sexos);
		cbGender.setValue(sexos.get(0));

		// llenar el combobox de animales
		ObservableList<Animal> animalData = FXCollections.observableArrayList();
		ArrayList<Animal> allAni = ca.getAll();
		allAni.stream().forEach((alraza1) -> {
			animalData.add(alraza1);
		});
		cbAnimal.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Animal> o, Animal ov, Animal nv) -> {
					fillBreedCombos(nv);
				});
		cbAnimal.setItems(animalData);

		// disable the race combo boxes
		cbBreed1.setDisable(true);
		cbBreed2.setDisable(true);
	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));

		// the tabs themselves
		tabDetails.setText(msg.getString("label.details"));
		tabPedigree.setText(msg.getString("item.pedigree"));
		tabOffspring.setText(msg.getString("label.offspring"));
		tabEvents.setText(msg.getString("item.events"));
		tabDeath.setText(msg.getString("label.death"));

		// Details Panel
		lbLabel.setText(msg.getString("label.create.livestock") + ":");

		lbID.setText(msg.getString("label.id") + ":");
		lbFarm.setText(msg.getString("item.farm") + ":");
		lbEarTag.setText(msg.getString("label.ear.tag") + ":");
		lbName.setText(msg.getString("label.name") + ":");
		lbColor.setText(msg.getString("label.color") + ":");
		lbWeight.setText(msg.getString("label.weight") + ":");
		lbDescription.setText(msg.getString("label.notes") + ":");
		lbStatus.setText(msg.getString("label.status") + ":");
		lbGender.setText(msg.getString("label.gender") + ":");
		lbBirth.setText(msg.getString("label.birth.date") + ":");
		lbAnimal.setText(msg.getString("label.animal.type") + "*:");
		lbBreed1.setText(msg.getString("label.breed") + " 1*:");
		lbBreed2.setText(msg.getString("label.breed") + " 2:");
		cbActive.setText(msg.getString("label.active"));
		cbBranded.setText(msg.getString("label.branded"));
		lbImage.setText(msg.getString("label.picture") + ":");
		btnImage.setText(msg.getString("button.choose.image"));
		btnBrand.setText(msg.getString("button.change.brand"));

		// Pedigree Tab
		lbMother.setText(msg.getString("label.mother") + ":");
		lbFather.setText(msg.getString("label.father") + ":");

		// Offspring Tab

		// Events Tab

		// Death Tab
		lbDeath.setText(msg.getString("label.date.death") + ":");
		lbCause.setText(msg.getString("label.cause") + ":");
		lbDeathImage.setText(msg.getString("label.death.image") + ":");
		btnDeath.setText(msg.getString("button.choose.image"));
	}

	private void fillBreedCombos(Animal a) {
		cbBreed1.setDisable(false);
		cbBreed2.setDisable(false);

		// llenar el combobox de breeds
		ObservableList<Race> data = FXCollections.observableArrayList();
		ArrayList<Race> list = cr.getRaces(a);
		list.stream().forEach((alraza1) -> {
			data.add(alraza1);
		});

		cbBreed1.setItems(data);
		cbBreed2.setItems(data);
	}

	public void initData(Livestock live) {
		this.livestock = live;

		tfID.setText(live.getId_livestock() + "");
		tfEarTag.setText(live.getEar_ring());
		tfName.setText(live.getName());
		tfColor.setText(live.getColor());
		tfDescription.setText(live.getDescription());
		tfStatus.setText(live.getStatus());
		tfCause.setText(live.getCause_death());
		tfWeight.setText(live.getWeight() + "");

		cbActive.setSelected(live.isActive());

		cbBranded.setSelected(live.isBranded());
		if (live.isBranded()) {
			System.out.println("branded geting farm brand");
			byte[] iron = live.getRanch().getIron();
			if (iron != null) {
				try {
					ivBrand.setImage(ImageManager.byteArraytoFXImage(iron));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// sex combobox
		System.out.println("live gender=" + live.getGender());
		cbGender.getSelectionModel().select(live.getGender());

		// date pickers
		Calendar c = Calendar.getInstance();
		Date d = live.getDate_birth();
		if (d != null) {
			c.setTime(d);
			dpBirth.getEditor().setText(
					(c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
		}

		d = live.getDate_death();
		if (d != null) {
			c.setTime(d);
			dpDeath.getEditor().setText(
					(c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
		}

		Race r = live.getId_race_1();
		// animal type
		Animal a = r.getAnimal();
		System.out.println(" live anim =" + a);
		cbAnimal.getSelectionModel().select(a);
		cbAnimal.setDisable(true);

		// breed one
		cbBreed1.getSelectionModel().select(r);

		// breed two
		r = live.getId_race_2();
		cbBreed2.getSelectionModel().select(r);

		imgAlive = setImage(live.getImg(), ivImage);
		imgDead = setImage(live.getImg_dead(), ivDeath);

		ResourceBundle msg = local.getMessages();
		lbLabel.setText(msg.getString("label.detail.livestock") + ":");
		update = true;

		// event table
		Image gato = ResourceManager.gato;
		tvEvents.getColumns().clear();
		createColumn(tvEvents, msg.getString("label.id"), "id_le", 50, gato);
		createColumn(tvEvents, msg.getString("label.event"), "event", 280, gato);
		createColumn(tvEvents, msg.getString("label.date.event"), "date", 160, gato);
		createColumn(tvEvents, msg.getString("label.price"), "price", 100, gato);
		createColumn(tvEvents, msg.getString("label.comment"), "comment", 250, gato);

		ArrayList<LivestockEvent> datos = cl.getLivestockAtEvents(live);
		tvEvents.getItems().addAll(datos);

		// fill the offspring table
		fillOffspring(live);

		// parents
		// tab************************************************************
		cbMother.setDisable(false);
		cbFather.setDisable(false);

		// fill the data for the females
		ArrayList<Livestock> females = cl.getLivestockGenderAnimal(2, a);
		// if (live.getGender() == 1) {
		// System.out.println("removed fem=" + females.remove(live));
		// }
		ObservableList<Livestock> data = FXCollections.observableArrayList();
		females.stream().forEach((alraza1) -> {
			data.add(alraza1);
		});
		cbMother.setItems(data);

		// fill the data for the males
		ArrayList<Livestock> males = cl.getLivestockGenderAnimal(1, a);
		// if (live.getGender() == 0) {
		// males.remove(live);
		// }
		ObservableList<Livestock> data2 = FXCollections.observableArrayList();
		males.stream().forEach((alraza1) -> {
			data2.add(alraza1);
		});
		cbFather.setItems(data2);

		// set the mother and father
		cbMother.getSelectionModel().select(live.getId_mother());
		cbFather.getSelectionModel().select(live.getId_father());

		Livestock mother = live.getId_mother();
		if (mother != null) {
			byte[] momImg = mother.getImg();
			if (momImg == null) {
				Race mr = mother.getId_race_1();
				ivMother.setImage(mr.getImage());
			} else {
				try {
					ivMother.setImage(ImageManager.byteArraytoFXImage(momImg));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Livestock father = live.getId_father();
		if (father != null) {
			byte[] popImg = father.getImg();
			if (popImg == null) {
				Race mr = father.getId_race_1();
				ivFather.setImage(mr.getImage());
			} else {
				try {
					ivFather.setImage(ImageManager.byteArraytoFXImage(popImg));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// **************************************************************************

	}

	private void fillOffspring(Livestock live) {
		// 0 = male; 1= female
		int gender = live.getGender();
		ArrayList<Livestock> kids = null;
		if (gender == 0) {
			// male
			kids = cl.getFathersOffspring(live);
		} else {
			// female
			kids = cl.getMothersOffspring(live);
		}
		ResourceBundle msg = local.getMessages();
		Image gato = ResourceManager.gato;
		tvDescendants.getColumns().clear();
		createColumn(tvDescendants, msg.getString("label.id"), "id_livestock", 50, gato);
		createColumn(tvDescendants, msg.getString("label.ear.tag"), "ear_ring", 100, gato);
		createColumn(tvDescendants, msg.getString("label.gender"), "sex", 100, gato);
		createColumn(tvDescendants, msg.getString("label.name"), "name", 100, gato);
		createColumn(tvDescendants, msg.getString("label.color"), "color", 100, gato);
		createColumn(tvDescendants, msg.getString("label.status"), "status", 100, gato);
		createColumn(tvDescendants, msg.getString("label.birth.date"), "date_birth", 110, gato);
		createColumn(tvDescendants, msg.getString("label.age"), "age", 50, gato);
		createColumn(tvDescendants, msg.getString("label.death_date"), "date_death", 110, gato);
		createColumn(tvDescendants, msg.getString("label.breed"), "id_race_1", 130, gato);
		createColumn(tvDescendants, msg.getString("label.breed") + " 2", "id_race_2", 130, gato);
		createColumn(tvDescendants, msg.getString("item.farm"), "ranch", 150, gato);
		createColumn(tvDescendants, msg.getString("label.active"), "active", 100, gato);
		createColumn(tvDescendants, msg.getString("label.branded"), "branded", 100, gato);
		tvDescendants.getItems().addAll(kids);
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

	@SuppressWarnings("deprecation")
	@FXML
	void handleSave(ActionEvent event) {
		if (livestock == null) {
			System.out.println("livestock =" + livestock);
			livestock = new Livestock();
		}

		livestock.setRanch(crr.getAll().get(0));

		// ear tag null check
		if (tfEarTag.getText() == null) {
			livestock.setEar_ring(null);
		} else {
			String ear = (tfEarTag.getText().isEmpty()) ? null : tfEarTag.getText();
			livestock.setEar_ring(ear);
		}

		livestock.setName(tfName.getText());
		livestock.setColor(tfColor.getText());
		livestock.setDescription(tfDescription.getText());
		livestock.setStatus(tfStatus.getText());

		// set if active or archived
		livestock.setActive(cbActive.isSelected());

		// set if branded
		livestock.setBranded(cbBranded.isSelected());

		// set gender
		int gender = cbGender.getSelectionModel().getSelectedIndex();
		System.out.println("gender num=" + gender);
		livestock.setGender(gender);

		// set birth date
		String fecha = dpBirth.getEditor().getText();
		livestock.setDate_birth((fecha.isEmpty() ? null : new java.util.Date(fecha)));

		// set death date
		fecha = dpDeath.getEditor().getText();
		livestock.setDate_death((fecha.isEmpty() ? null : new java.util.Date(fecha)));

		// set cause of death
		livestock.setCause_death(tfCause.getText());

		// set the weight
		String weight = tfWeight.getText();
		if (weight != null && !weight.isEmpty()) {
			double w = 0;
			try {
				w = Double.parseDouble(weight);
			} catch (Exception e) {
				System.out.println("error=" + e);
			}
			livestock.setWeight(w);
		}

		// set the races
		livestock.setId_race_1(cbBreed1.getSelectionModel().getSelectedItem());
		livestock.setId_race_2(cbBreed2.getSelectionModel().getSelectedItem());

		// set the parents
		livestock.setId_mother(cbMother.getSelectionModel().getSelectedItem());
		livestock.setId_father(cbFather.getSelectionModel().getSelectedItem());

		if (imgAlive != null) {
			livestock.setImg(imgAlive);
		}

		if (imgDead != null) {
			livestock.setImg_dead(imgDead);
		}

		System.out.println("update =" + update);
		if (update) {
			cl.update(livestock);
		} else {
			cl.saveNew(livestock);
		}
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleImage(ActionEvent event) throws IOException {
		byte[] data = handleImg(event);
		imgAlive = data;
		if (data != null) {
			ivImage.setImage(ImageManager.byteArraytoFXImage(data));
		}
	}

	@FXML
	void handleDeathImage(ActionEvent event) throws IOException {
		byte[] data = handleImg(event);
		imgDead = data;
		if (data != null) {
			ivDeath.setImage(ImageManager.byteArraytoFXImage(data));
		}
	}

	private byte[] handleImg(ActionEvent event) {
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
				return data;
			} catch (IOException | java.lang.IllegalArgumentException e) {
				System.out.println("Error en cargar imagen " + e);
			}
		}
		return null;
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

	@FXML
	void handleBrand(ActionEvent event) {

	}

}
