package com.marmar.farmapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LivestockGalleryDeadController implements Initializable, Internationable {

	@FXML
	private ComboBox<String> cbAge;

	@FXML
	private Label lbFilter;

	@FXML
	private ComboBox<String> cbGender;

	@FXML
	private Label lbTotal;

	@FXML
	private ComboBox<String> cbEarTag;

	@FXML
	private Label lbLabel;

	@FXML
	private JFXButton btnPDF;

	@FXML
	private Label lbActive;

	@FXML
	private ComboBox<String> cbActive;

	@FXML
	private ImageView ivImage;

	@FXML
	private JFXButton btnRefresh;

	@FXML
	private Label lbEarTag;

	@FXML
	private Label lbAge;

	@FXML
	private ComboBox<Animal> cbAnimal;

	@FXML
	private Label lbGender;

	@FXML
	private TextField tfMonths;

	@FXML
	private Label lbAnimal;

	@FXML
	private TilePane tpTilePane;

	@FXML
	private Label lbMonths;
	// private AnimalDAO ca;
	private LivestockDAO cl;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		// ca = ctx.getBean(AnimalDAO.class);
		cl = ctx.getBean(LivestockDAO.class);

		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_cowhead.png"));

		local = ResourceManager.localizer;

		setLabels();
		// fillCombos();
		fillGallery(true);

		// hide the buttons
		btnPDF.setVisible(false);
	}

	public void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbActive.setText(msg.getString("label.active") + ":");
		lbAge.setText(msg.getString("label.age") + ":");
		lbAnimal.setText(msg.getString("label.animal.type") + ":");
		lbEarTag.setText(msg.getString("label.ear.tag") + ":");
		lbFilter.setText(msg.getString("label.apply.filters") + ":");
		lbGender.setText(msg.getString("label.gender") + ":");
		lbLabel.setText(msg.getString("label.livestock.gallery_dead") + ":");
		lbMonths.setText(msg.getString("label.months") + ":");
		lbTotal.setText(msg.getString("label.number.livestock.shown") + ":");

		btnPDF.setText(msg.getString("button.pdf"));
		btnRefresh.setText(msg.getString("button.refresh"));

		// fill the combo boxes
		// fillCombos();
	}

	// private void fillCombos() {
	//
	// ResourceBundle msg = local.getMessages();
	//
	// // llenar el combobox de animales
	// ObservableList<Animal> animalData = FXCollections.observableArrayList();
	// ArrayList<Animal> allAni = ca.getAll();
	// allAni.stream().forEach((alraza1) -> {
	// animalData.add(alraza1);
	// });
	// cbAnimal.setItems(animalData);
	// // cbAnimal.getSelectionModel().selectedItemProperty()
	// // .addListener((ObservableValue<? extends Animal> o, Animal ov, Animal nv)
	// -> {
	// // fillGallery(true);
	// // });
	//
	// // set the tf to zero
	// tfMonths.setText("0");
	//
	// // llenar el combobox de sexos
	// ObservableList<String> sexos =
	// FXCollections.observableArrayList(msg.getString("filter.both"),
	// msg.getString("filter.male"), msg.getString("filter.female"));
	// cbGender.setItems(sexos);
	// cbGender.setValue(sexos.get(0));
	// // cbGender.getSelectionModel().selectedItemProperty()
	// // .addListener((ObservableValue<? extends String> o, String ov, String nv)
	// -> {
	// // fillGallery(true);
	// // });
	//
	// // llenar el combobox de actividad
	// ObservableList<String> actividad =
	// FXCollections.observableArrayList(msg.getString("filter.active.all"),
	// msg.getString("filter.active.only"), msg.getString("filter.archived.only"));
	// cbActive.setItems(actividad);
	// cbActive.setValue(actividad.get(2));
	// cbActive.getSelectionModel().selectedItemProperty()
	// .addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
	// fillGallery(true);
	// });
	//
	// // llenar el combobox de aretados
	// ObservableList<String> aretados =
	// FXCollections.observableArrayList(msg.getString("filter.both"),
	// msg.getString("filter.registered.only"),
	// msg.getString("filter.notregistered.only"));
	// cbEarTag.setItems(aretados);
	// cbEarTag.setValue(aretados.get(0));
	// // cbEarTag.getSelectionModel().selectedItemProperty()
	// // .addListener((ObservableValue<? extends String> o, String ov, String nv)
	// -> {
	// // fillGallery(true);
	// // });
	//
	// // llenar el combobox de edades
	// ObservableList<String> edades =
	// FXCollections.observableArrayList(msg.getString("filter.allages"),
	// msg.getString("filter.greater"), msg.getString("filter.lesser"));
	// cbAge.setItems(edades);
	// cbAge.setValue(edades.get(0));
	// // cbAge.getSelectionModel().selectedItemProperty()
	// // .addListener((ObservableValue<? extends String> o, String ov, String nv)
	// -> {
	// // fillGallery(true);
	// // });
	// }

	private void fillGallery(boolean refresh) {

		// get all the data
		ArrayList<Livestock> livestock = cl.getAllDeadLivestock();

		tpTilePane.getChildren().clear();
		tpTilePane.setPadding(new Insets(15, 15, 15, 15));
		tpTilePane.setHgap(15);

		for (int i = 0; i < livestock.size(); i++) {
			final ImageView imageView = createImageView(livestock.get(i));
			tpTilePane.getChildren().add(imageView);
		}
		ResourceBundle msg = local.getMessages();
		lbTotal.setText(msg.getString("label.number.livestock.shown") + ": " + livestock.size());

	}

	@FXML
	void handleRefresh(ActionEvent event) {
		fillGallery(true);
	}

	@FXML
	void handlePDF(ActionEvent event) {

	}

	private ImageView createImageView(final Livestock live) {
		// prepair the image
		Image image = null;
		byte[] img = live.getImg_dead();
		if (img != null) {
			try {
				image = ImageManager.byteArraytoFXImage(img);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			image = new Image("/com/marmar/farmapp/images/empty.png");
		}

		// create the image view
		ImageView imageView = null;
		imageView = new ImageView(image);
		imageView.setFitWidth(250);
		imageView.setFitHeight(188);
		imageView.setOnMouseClicked((MouseEvent mouseEvent) -> {
			if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
				if (mouseEvent.getClickCount() == 2) {
					try {
						FXMLLoader loader = new FXMLLoader(
								getClass().getResource("/com/marmar/farmapp/view/LivestockEdit.fxml"));
						Stage stage = new Stage(StageStyle.DECORATED);
						stage.setTitle("Livestock Window");
						Parent root = (Parent) loader.load();
						root.getStylesheets().add(ResourceManager.currentCSS);
						stage.setScene(new Scene(root));
						LivestockEditController controller = loader.<LivestockEditController>getController();
						stage.show();
						controller.initData(live);
					} catch (Exception e) {
						System.out.println("Error en abrir ventana detalle: " + e.getMessage());
						e.printStackTrace();
					}
				}
			}
		});

		return imageView;
	}
}
