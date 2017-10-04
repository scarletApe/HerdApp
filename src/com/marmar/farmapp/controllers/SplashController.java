package com.marmar.farmapp.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.util.ResourceManager;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SplashController implements Initializable {

	@FXML
	private Label lblClose;

	@FXML
	private ImageView ivImage;

	private Stage stage;

	/**
	 * Initializes the controller class.
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ivImage.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/icon_cowhead.png"));
		longStart();
		lblClose.setOnMouseClicked((MouseEvent event) -> {
			Platform.exit();
			System.exit(0);
		});
		// TODO
	}

	public void initData(Stage stage) {
		this.stage = stage;
	}

	private void longStart() {
		Service<ApplicationContext> service = new Service<ApplicationContext>() {
			@Override
			protected Task<ApplicationContext> createTask() {
				return new Task<ApplicationContext>() {
					@Override
					protected ApplicationContext call() throws Exception {
						ApplicationContext appContex = Configuration.getInstance().getApplicationContext();
						int max = appContex.getBeanDefinitionCount();
						updateProgress(0, max);
						for (int k = 0; k < max; k++) {
							Thread.sleep(50);
							updateProgress(k + 1, max);
						}

						return appContex;
					}
				};
			}
		};
		service.start();
		service.setOnRunning((WorkerStateEvent event) -> {
		});
		service.setOnSucceeded((WorkerStateEvent event) -> {

			new ResourceManager().init();

			System.out.println("Fuck Splash");
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/Main.fxml"));
//				Stage stage = new Stage(StageStyle.DECORATED);
//				stage.setTitle("HeardApp v1.6");
//				Parent root;
//				root = (Parent) loader.load();
//				root.getStylesheets().add(ResourceManager.metroCSS);
//				stage.setMaximized(true);
//				stage.setScene(new Scene(root));
//				stage.show();
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/LogIn.fxml"));
				Stage stage = new Stage(StageStyle.DECORATED);
				stage.setTitle("HeardApp v1.6");
				Parent root;
				root = (Parent) loader.load();
				root.getStylesheets().add(ResourceManager.metroCSS);
				stage.setScene(new Scene(root));
				stage.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			stage.getScene().getWindow().hide();

		});
	}
}
