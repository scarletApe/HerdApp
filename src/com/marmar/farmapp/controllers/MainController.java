package com.marmar.farmapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.marmar.farmapp.animations.FadeInRightTransition;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Ranch;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {
	@FXML
	private AnchorPane paneData;

	@FXML
	private AnchorPane topPane;

	@FXML
	private ImageView ivLogo;

	@FXML
	private Label lbTitle;

	@FXML
	private TreeView<String> tvTree;

	private AnchorPane paneAnimals;
	private AnchorPane paneRaces;
	private AnchorPane paneUsers;
	private AnchorPane paneEventTypes;
	private AnchorPane paneEvents;
	private AnchorPane paneRanch;
	private AnchorPane paneLivestock;

	private AnchorPane panePens;
	private AnchorPane panePenManager;
	// private AnchorPane panePregos;
	private AnchorPane paneCharts;
	private AnchorPane paneReports;
	private AnchorPane paneGallery;
	private Localizer local;

	/**
	 * Initializes the controller class.
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		System.out.println("Hello initializing");

		// ***************************************************************
		// Tree view stuff

		local = ResourceManager.localizer;
		ResourceBundle msg = local.getMessages();
		String rute = "/com/marmar/farmapp/images/";
		double w = 30;
		double h = 30;

		final TreeItem<String> treeRoot = new TreeItem<String>("HerdApp",
				new ImageView(new Image(rute + "icon_cowhead.png", w + 5, h + 5, true, true, true)));
		treeRoot.getChildren()
				.addAll(Arrays.asList(
						new TreeItem<String>("1. " + msg.getString("item.livestock"), // "Livestock",
								new ImageView(new Image(rute + "icon_cow.png", w, h, true, true, true))),
						new TreeItem<String>("2. " + msg.getString("item.pens_groups"), // "Pens
								new ImageView(new Image(rute + "icon_pen.png", w, h, true, true, true))),
						new TreeItem<String>("3. " + msg.getString("item.events"), // "Events",
								new ImageView(new Image(rute + "icon_event.png", w, h, true, true, true))),
						new TreeItem<String>("4. " + msg.getString("item.charts"), // "Charts",
								new ImageView(new Image(rute + "icon_chart.png", w, h, true, true, true))),
						new TreeItem<String>("5. " + msg.getString("item.reports"), // "Reports",
								new ImageView(new Image(rute + "icon_report.png", w, h, true, true, true))),
						new TreeItem<String>("6. " + msg.getString("item.users"), // "Users",
								new ImageView(new Image(rute + "icon_user.png", w, h, true, true, true))),
						new TreeItem<String>("7. " + msg.getString("item.farm"), // "Farm",
								new ImageView(new Image(rute + "icon_farm.png", w, h, true, true, true)))));
		treeRoot.getChildren().get(0).getChildren().addAll(Arrays.asList(
				new TreeItem<String>("a. " + msg.getString("item.animal_types")/* "Animal Species" */),
				new TreeItem<String>("b. " + msg.getString("item.animal_breeds")/* "Animal Breeds" */),
				new TreeItem<String>("c. " + msg.getString("item.livestock_inventory")/* "Livestock Inventory" */),
				new TreeItem<String>("d. " + msg.getString("item.livestock_gallery")/* "Livestock Gallery" */)));
		treeRoot.getChildren().get(1).getChildren()
				.addAll(Arrays.asList(
						new TreeItem<String>("e. " + msg.getString(
								"item.pens_groups")/* Holding Areas" */),
						new TreeItem<String>("f. " + msg.getString("item.manage.holding")/* "Manage Holdings" */)));
		treeRoot.getChildren().get(2).getChildren()
				.addAll(Arrays.asList(
						new TreeItem<String>(
								"g. " + msg.getString("item.manage.event.types")/* "Manage Event Types" */),
						new TreeItem<String>("h. " + msg.getString("item.view.events")/* "View Events" */
						)));

		tvTree.setShowRoot(true);
		tvTree.setRoot(treeRoot);
		treeRoot.setExpanded(true);
		tvTree.getStylesheets().add(ResourceManager.listCSS);

		tvTree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {

			@Override
			public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> old_val,
					TreeItem<String> new_val) {
				TreeItem<String> selectedItem = new_val;
				System.out.println("Selected Text : " + selectedItem.getValue());
				handleListMenu(selectedItem.getValue());
			}

		});

		// ***************************************************************

		// run later, load the logo
		Platform.runLater(() -> {

			ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
			RanchDAO cr = ctx.getBean(RanchDAO.class);

			Ranch r = cr.getAll().get(0);
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
			lbTitle.setText("HerdApp System v1.6 - " + r.getName());

			// show the livestock pane
			handleListMenu("1");
		});

	}

	private void handleListMenu(String text) {
		switch (text.charAt(0)) {
		case '1':
		case 'c': {
			// paneLivestock
			if (paneLivestock == null) {
				try {
					paneLivestock = loadPane("/com/marmar/farmapp/view/Livestock.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneLivestock);
		}
			break;
		case 'a': {
			// pane Animals Types
			if (paneAnimals == null) {
				try {
					paneAnimals = loadPane("/com/marmar/farmapp/view/Animal.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneAnimals);
		}
			break;
		case 'b': {
			// pane Races or Breeds
			if (paneRaces == null) {
				try {
					paneRaces = loadPane("/com/marmar/farmapp/view/Race.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneRaces);
		}
			break;
		case 'd': {
			// pane gallery
			if (paneGallery == null) {
				try {
					paneGallery = loadPane("/com/marmar/farmapp/view/LivestockGallery.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneGallery);
		}
			break;
		case '2':
		case 'f': {
			// manage holding areas
			if (panePenManager == null) {
				try {
					panePenManager = loadPane("/com/marmar/farmapp/view/PenManager.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(panePenManager);
		}
			break;
		case 'e': {
			// pane Holding Areas
			if (panePens == null) {
				try {
					panePens = loadPane("/com/marmar/farmapp/view/Pens.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(panePens);
		}
			break;
		case '3':
		case 'g': {
			// pane Events
			if (paneEvents == null) {
				try {
					paneEvents = loadPane("/com/marmar/farmapp/view/Event.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneEvents);
		}
			break;
		case 'h': {
			// pane EventTypes
			if (paneEventTypes == null) {
				try {
					paneEventTypes = loadPane("/com/marmar/farmapp/view/EventTypes.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneEventTypes);
		}
			break;
		case '4': {
			// pane Charts
			if (paneCharts == null) {
				try {
					paneCharts = loadPane("/com/marmar/farmapp/view/Charts.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneCharts);
		}
			break;
		case '5': {
			// pane Reports
			if (paneReports == null) {
				try {
					paneReports = loadPane("/com/marmar/farmapp/view/Reports.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneReports);
		}
			break;

		case '6': {
			// pane Users
			if (paneUsers == null) {
				try {
					paneUsers = loadPane("/com/marmar/farmapp/view/User.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneUsers);
		}
			break;
		case '7': {
			// pane Ranch or farm
			if (paneRanch == null) {
				try {
					paneRanch = loadPane("/com/marmar/farmapp/view/Ranch.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneRanch);
		}
			break;

		case 'x': {
			// pane Pregos
			// setPane(paneUsers);
			// if(panePenManager ==null){
			// try{
			// panePenManager =
			// loadPane("/com/marmar/farmapp/view/PenManager.fxml");
			// }catch(Exception e){
			// System.err.println("Error pane " + e);
			// e.printStackTrace();
			// }
			// }
			// setPane(panePenManager);
		}
			break;
		}
	}

	private AnchorPane loadPane(String res) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(res));
		return (AnchorPane) loader.load();
	}

	private void setPane(AnchorPane p) {
		p.setPrefSize(topPane.getWidth(), topPane.getHeight());
		topPane.getChildren().setAll(p);
		new FadeInRightTransition(topPane).play();
	}

}
