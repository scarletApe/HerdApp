package com.marmar.farmapp;



import com.marmar.farmapp.controllers.SplashController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author manuelmartinez
 */
public class FarmApp extends Application {

	// public static Localizer LOCALIZER;

	@Override
	public void start(Stage stage) throws Exception {
		
		setUserAgentStylesheet(javafx.application.Application.STYLESHEET_MODENA);

		// esto muestra la ventana
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/Splash.fxml"));
		stage.setTitle("HerdApp v1.6");
		Parent root = (Parent) loader.load();
//		root.getStylesheets().add(ResourceManager.metroCSS);
		stage.setScene(new Scene(root));
		SplashController controller = loader.<SplashController> getController();
		stage.show();
		controller.initData(stage);
		
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
