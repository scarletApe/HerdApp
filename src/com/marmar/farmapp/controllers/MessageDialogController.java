package com.marmar.farmapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.util.ResourceManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Esta classe es una ventanita de dialogo. Solo muestra un texto, una imagen, y
 * un boton okay.
 * 
 * @author juanmartinez
 *
 */
public class MessageDialogController implements Initializable {

	@FXML
	private ImageView ivIcon;

	@FXML
	private Label labelMessage;

	@FXML
	private TextArea taMessage;

	@FXML
	private JFXButton btnOkay;

	private Stage stageSelf;
	// private Localizer local;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// local = ResourceManager.localizer;
	}

	@FXML
	void handleOkay(ActionEvent event) {
		if (stageSelf != null)
			stageSelf.close();
	}

	public void initData(Stage s, int messageType, String message) {
		// get the reference to this stage
		this.stageSelf = s;

		// message types
		// 1 = okay
		// 2 = error
		// 3 = info with java icon
		switch (messageType) {
		case 1:
		default:
			labelMessage.setText("Success!!");
			ivIcon.setImage(ResourceManager.successImg);
			break;
		case 2:
			labelMessage.setText("Oops, There was an error...");
			ivIcon.setImage(ResourceManager.warningImg);
			break;
		case 3:
			labelMessage.setText("Everything is fine.");
			ivIcon.setImage(ResourceManager.successImg);
			break;
		}

		// set the message on the text area
		taMessage.setText(message);
	}

}
