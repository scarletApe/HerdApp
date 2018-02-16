/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marmar.farmapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.animations.FadeInTransition;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.hibernate.UserDAO;
import com.marmar.farmapp.model.Ranch;
import com.marmar.farmapp.model.User;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author manuelmartinez
 */
public class LogInController implements Initializable {
	@FXML
	private Label lbContrasena;

	@FXML
	private ImageView ivLogo;

	@FXML
	private Label lbUsuario;

	@FXML
	private PasswordField pfContrasena;

	@FXML
	private Label lbCaption;

	@FXML
	private TextField tfNombreUsuario;

	@FXML
	private Label labelMensage;

	@FXML
	private ComboBox<String> cbIdiomas;
	@FXML
	private JFXButton btnEntrar;

	private RanchDAO crudRanch;
	private UserDAO crudUser;
	private Localizer local;
	private PanesData foo;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

//		System.out.println("Log in shown");

		// get the ranch conector from spring
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		crudRanch = ctx.getBean(RanchDAO.class);
		crudUser = ctx.getBean(UserDAO.class);
		local = ResourceManager.localizer;// Localizer

		// show the farm logo
		Ranch r = crudRanch.getAll().get(0);
		byte[] img = r.getLogo();
		if (img != null) {
			try {
				ivLogo.setImage(ImageManager.byteArraytoFXImage(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ivLogo.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/empty.png"));
		}
		// set the name of the ranch
		lbCaption.setText("SmallFarm App v1.8 - " + r.getName());

		// fill the combo boxes with the languages
		ObservableList<String> idiomas = FXCollections.observableArrayList("Español", "English");
		cbIdiomas.setItems(idiomas);
		cbIdiomas.setValue("Español");
		cbIdiomas.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> o, String ov, String nv) -> {
					String lan = null;
					if (nv.equalsIgnoreCase("Español")) {
						lan = "es";
					} else if (nv.equalsIgnoreCase("English")) {
						lan = "en";
					}
					local.changeLocale(lan);
					setLabels();
				});
		cbIdiomas.setCellFactory(listview -> new StringImageCell());
		cbIdiomas.setButtonCell(new StringImageCell());
//		System.out.println("Fuck Log in again");

		// transition in
		new FadeInTransition(lbContrasena).play();
		new FadeInTransition(ivLogo).play();
		new FadeInTransition(lbUsuario).play();
		new FadeInTransition(pfContrasena).play();
		new FadeInTransition(lbCaption).play();
		new FadeInTransition(tfNombreUsuario).play();
		new FadeInTransition(labelMensage).play();
		new FadeInTransition(btnEntrar).play();
	}

	public void initData(PanesData foo) {
		this.foo = foo;
	}

	// A Custom ListCell that displays an image and string
	static class StringImageCell extends ListCell<String> {

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

		private static ImageView getImageView(String imageName) {
			ImageView imageView = null;
			String r = "/com/marmar/farmapp/images/";
			switch (imageName) {
			case "Español":
				imageView = new ImageView(new Image(r + "flag_mx.gif"));
				break;
			case "English":
				imageView = new ImageView(new Image(r + "flag_us.gif"));
				break;
			default:
				imageName = null;
			}
			return imageView;
		}

	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbUsuario.setText(msg.getString("label.username") + ":");
		lbContrasena.setText(msg.getString("label.password") + ":");
		btnEntrar.setText(msg.getString("button.login"));
		labelMensage.setText("");
	}

	@FXML
	void handleEntrar(ActionEvent event) {
		String un = tfNombreUsuario.getText();
		String pass = pfContrasena.getText();
		ResourceBundle msg = local.getMessages();

		if (un.isEmpty() || pass.isEmpty()) {
			labelMensage.setText(msg.getString("msg.empty.field"));
			return;
		}
		User usuario = logIn(un, pass);
		if (usuario == null) {
			labelMensage.setText(msg.getString("msg.error"));
			return;
		}
		labelMensage.setText(msg.getString("msg.success"));

		// show the main window
		try {
			String nv = cbIdiomas.getValue();
			if (nv.equalsIgnoreCase("Español")) {
				local.changeLocale("es");
			} else if (nv.equalsIgnoreCase("English")) {
				local.changeLocale("en");
			}

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/Main.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setTitle("SmallFarm App v1.8");
			Parent root;
			root = (Parent) loader.load();
			root.getStylesheets().add(ResourceManager.currentCSS);
			stage.setMaximized(true);
			stage.setScene(new Scene(root));
			MainController controller = loader.<MainController>getController();
			stage.show();
			controller.initData(usuario, foo, root);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// hide this current window
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	private User logIn(String un, String pass) {
		// String s = encriptarContrasena(pass);
		return crudUser.get(un, pass);
	}

	// private String encriptarContrasena(String s) {
	// return DigestUtils.sha1Hex(s);
	// }
	//
	// private boolean compararContrasena(String plain, String encripted) {
	// String s = DigestUtils.sha1Hex(plain);
	//
	// return s.equalsIgnoreCase(encripted);
	// }
}
