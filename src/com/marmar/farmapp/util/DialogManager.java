package com.marmar.farmapp.util;

import java.io.IOException;

import com.marmar.farmapp.controllers.MessageDialogController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogManager {

	public DialogManager() {
	}

	public void showDialog(int messageType, String message) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/MessageDialog.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			// stage.setTitle("Holding Area Window");
			Parent root = (Parent) loader.load();
			root.getStylesheets().add(ResourceManager.currentCSS);
			MessageDialogController controller = loader.<MessageDialogController>getController();
			stage.setScene(new Scene(root));
			stage.show();
			controller.initData(stage, messageType, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
