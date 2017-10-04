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
import com.marmar.farmapp.hibernate.UserDAO;
import com.marmar.farmapp.model.Ranch;
import com.marmar.farmapp.model.User;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class UserEditController implements Initializable {

	@FXML
	private Label lbPassword;

	@FXML
	private Label lbLabel;

	@FXML
	private TextField tfUsername;

	@FXML
	private TextField tfName;

	@FXML
	private JFXButton btnImage;

	@FXML
	private ImageView ivImage;

	@FXML
	private JFXButton btnCancel;

	@FXML
	private JFXButton btnSave;

	@FXML
	private Label lbName;

	@FXML
	private Label lbImage;

	@FXML
	private PasswordField pfPassword;

	@FXML
	private TextField tfFarm;

	@FXML
	private Label lbFarm;

	@FXML
	private Label lbUsername;

	private RanchDAO cr;
	private UserDAO cu;
	private Ranch ranch;
	private byte[] image;
	private User user;
	private boolean update = false;
	private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		cu = ctx.getBean(UserDAO.class);
		cr = ctx.getBean(RanchDAO.class);
		local = ResourceManager.localizer;

		ranch = cr.getAll().get(0);
		tfFarm.setText(ranch.getName());
		setLabels();
	}

	private void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbName.setText(msg.getString("label.name") + "*:");
		lbUsername.setText(msg.getString("label.username") + "*:");
		lbPassword.setText(msg.getString("label.password") + "*:");
		lbFarm.setText(msg.getString("item.farm") + ":");
		lbLabel.setText(msg.getString("label.create.user") + ":");
		lbImage.setText(msg.getString("label.picture") + ":");

		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));
		btnImage.setText(msg.getString("button.choose.image"));
	}

	public void initData(User u) {
		this.user = u;
		tfName.setText(u.getName());
		tfUsername.setText(u.getUsername());

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
		lbLabel.setText(msg.getString("label.detail.user") + ":");
		update = true;
	}

	@FXML
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleSave(ActionEvent event) {
		if (user == null) {
			System.out.println("user =" + user);
			user = new User();
		}
		user.setName(tfName.getText());
		user.setUsername(tfUsername.getText());
		user.setPassword(pfPassword.getText());
		user.setUsertype(1);
		user.setRanch(ranch);

		if (image != null) {
			user.setImg(image);
		}

		System.out.println("update =" + update);
		if (update) {
			cu.update(user);
		} else {
			cu.saveNew(user);
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
