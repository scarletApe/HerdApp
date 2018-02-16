package com.marmar.farmapp.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.marmar.farmapp.animations.FadeInRightTransition;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Ranch;
import com.marmar.farmapp.model.User;
import com.marmar.farmapp.util.ImageManager;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable, Internationable {
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
	private AnchorPane paneDeathGallery;
	private AnchorPane paneWelcome;
	private Localizer local;
	private PanesData foo = null;

	private AnchorPane currentPane;
	private Parent self;

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

		final TreeItem<String> treeRoot = new TreeItem<String>("SmallFarm App",
				new ImageView(new Image(rute + "icon_cowhead.png", w + 5, h + 5, true, true, true)));
		treeRoot.getChildren().addAll(Arrays.asList(new TreeItem<String>("1. " + msg.getString("item.livestock"), // "Livestock",
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
				new TreeItem<String>("c. " + msg.getString("item.livestock_inventory")/* "Live stock Inventory" */),
				new TreeItem<String>("d. " + msg.getString("item.livestock_gallery")/* "Live stock Gallery" */),
				new TreeItem<String>("i. " + msg.getString("item.livestock_gallery_dead")/* "Death Gallery" */)));
		treeRoot.getChildren().get(1).getChildren()
				.addAll(Arrays.asList(
						new TreeItem<String>("e. " + msg.getString("item.pens_groups")/* Holding Areas" */),
						new TreeItem<String>("f. " + msg.getString("item.manage.holding")/* "Manage Holdings" */)));
		treeRoot.getChildren().get(2).getChildren()
				.addAll(Arrays.asList(
						new TreeItem<String>("g. " + msg.getString("item.view.events")/* "View Events" */),
						new TreeItem<String>("h. " + msg.getString("item.manage.event.types")/* "Manage Event Types" */)
						));

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

			// set the title text
			lbTitle.setText("SmallFarm App v1.8 - " + r.getName());

			// set the labels
			setLabels();
		});

	}

	public void initData(User u, PanesData panes, Parent self) {
		this.self = self;

		// set the welcome text
		lbWelcome.setText(local.getMessages().getString("msg.welcome") + " " + u.getName());

		// set the current date
		Calendar cal = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, ResourceManager.localizer.getCurrentLocale());
		lbLogIn.setText(df.format(cal.getTime()));

		// set the user image
		byte[] img = u.getImg();
		if (img != null) {
			try {
				ivUser.setImage(ImageManager.byteArraytoFXImage(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			ivUser.setImage(new javafx.scene.image.Image("/com/marmar/farmapp/images/empty.png"));
		}

		// set the current language flag
		String lang = ResourceManager.localizer.getLanguage();
		ivFlag.setImage(getFlagImage(lang));
		System.out.println(lang);

		if (panes == null) {
			// ********************************************************
			System.out.println("Setting up Loading Service");

			LoadService service = new LoadService();

			service.setOnSucceeded((WorkerStateEvent t) -> {
				PanesData o = service.getValue();

				// set the panes
				paneAnimals = o.getPaneAnimals();
				paneRaces = o.getPaneRaces();
				paneUsers = o.getPaneUsers();
				paneEventTypes = o.getPaneEventTypes();
				paneEvents = o.getPaneEvents();
				paneRanch = o.getPaneRanch();
				paneLivestock = o.getPaneLivestock();
				panePens = o.getPanePens();
				panePenManager = o.getPanePenManager();
				paneCharts = o.getPaneCharts();
				paneWelcome = o.getPaneWelcome();
				// paneReports = foo.getPaneReports();
//				paneGallery = o.getPaneGallery();
				// TODO 

				this.foo = service.getValue();

				// show the livestock pane
				handleListMenu("1");

				pbBar.setVisible(false);
			});

			service.setOnFailed(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent t) {
					Throwable ouch = service.getException();
					System.out.println(ouch.getClass().getName() + " -> " + ouch.getMessage());
				}
			});
			pbBar.progressProperty().bind(service.progressProperty());
			service.start();
		} else {
			this.foo = panes;

			// set the panes
			paneAnimals = panes.getPaneAnimals();
			paneRaces = panes.getPaneRaces();
			paneUsers = panes.getPaneUsers();
			paneEventTypes = panes.getPaneEventTypes();
			paneEvents = panes.getPaneEvents();
			paneRanch = panes.getPaneRanch();
			paneLivestock = panes.getPaneLivestock();
			panePens = panes.getPanePens();
			panePenManager = panes.getPanePenManager();
			paneCharts = panes.getPaneCharts();
			paneWelcome = panes.getPaneWelcome();
			// paneReports = foo.getPaneReports();
			//TODO
//			paneGallery = panes.getPaneGallery();

			// set the language
			ArrayList<Internationable> p = panes.getInters();
			for (int i = 0; i < p.size(); i++) {
				if(p!=null) {
					p.get(i).setLabels();
				}
			}

			// hide the progress bar
			pbBar.setVisible(false);

			// show the livestock pane
			handleListMenu("1");
		}
	}

	private Image getFlagImage(String lang) {
		Image image = null;
		String r = "/com/marmar/farmapp/images/";
		switch (lang) {
		case "es":
			image = (new Image(r + "flag_mx.gif"));
			break;
		case "en":
			image = (new Image(r + "flag_us.gif"));
			break;
		}
		return image;
	}

	@FXML
	void handleLogOut(ActionEvent event) throws IOException {
		// hide this current window (if this is whant you want
		((Node) (event.getSource())).getScene().getWindow().hide();

		// esto muestra la ventana
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/LogIn.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("SmallFarm App v1.8");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		LogInController controller = loader.<LogInController>getController();
		stage.show();
		controller.initData(foo);
	}

	private void handleListMenu(String text) {
		switch (text.charAt(0)) {
		case 'S':
			// welcome pane
//			if (paneWelcome == null) {
//				try {
//					paneWelcome = loadPane("/com/marmar/farmapp/view/Welcome.fxml");
//				} catch (Exception e) {
//					System.err.println("Error pane " + e);
//					e.printStackTrace();
//				}
//			}
			setPane(paneWelcome);
			break;
		case '1':
		case 'c': {
			// paneLivestock
			setPane(paneLivestock);
		}
			break;
		case 'a': {
			// pane Animals Types
			setPane(paneAnimals);
		}
			break;
		case 'b': {
			// pane Races or Breeds
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
		case 'i': {
			// pane death gallery
			if (paneDeathGallery == null) {
				try {
					paneDeathGallery = loadPane("/com/marmar/farmapp/view/LivestockGalleryDead.fxml");
				} catch (Exception e) {
					System.err.println("Error pane " + e);
					e.printStackTrace();
				}
			}
			setPane(paneDeathGallery);
		}
			break;
		case '2':
		case 'f': {
			// manage holding areas
			setPane(panePenManager);
		}
			break;
		case 'e': {
			// pane Holding Areas
			setPane(panePens);
		}
			break;
		case '3':
		case 'g': {
			// pane Events
			setPane(paneEvents);
		}
			break;
		case 'h': {
			// pane EventTypes
			setPane(paneEventTypes);
		}
			break;
		case '4': {
			// pane Charts
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
			setPane(paneUsers);
		}
			break;
		case '7': {
			// pane Ranch or farm
			setPane(paneRanch);
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
		// set the style to the list view
		p.getStylesheets().clear();
		p.getStylesheets().add(ResourceManager.currentCSS);
		topPane.getChildren().setAll(p);
		new FadeInRightTransition(topPane).play();

		currentPane = p;
	}

	@FXML
	private ProgressBar pbBar;

	@FXML
	private MenuItem miAnimal;

	@FXML
	private MenuItem miLivestock;

	@FXML
	private Label lbWelcome;

	@FXML
	private Menu menuFile;

	@FXML
	private Menu menuRegister;

	@FXML
	private ImageView ivUser;

	@FXML
	private Label lbLogIn;

	@FXML
	private MenuItem miLight;

	@FXML
	private MenuItem miHold;

	@FXML
	private MenuItem miDark;

	@FXML
	private Label lbLogOut;

	@FXML
	private Label lbCopyright;

	@FXML
	private Menu menuView;

	@FXML
	private MenuItem miAbout;

	@FXML
	private MenuItem miEvent;

	@FXML
	private MenuItem miClose;

	@FXML
	private MenuItem miBreed;

	@FXML
	private ImageView ivFlag;

	@FXML
	private Menu menuEdit;

	@FXML
	private MenuItem miET;

	@FXML
	private Menu menuHelp;

	@FXML
	private MenuItem miDelete;

	@FXML
	private Hyperlink hlLogOut;

	@FXML
	void handleClose(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void handleLight(ActionEvent event) {
		ResourceManager.setLightTheme();

		self.getStylesheets().clear();
		self.getStylesheets().add(ResourceManager.currentCSS);

		currentPane.getStylesheets().clear();
		currentPane.getStylesheets().add(ResourceManager.currentCSS);
	}

	@FXML
	void handleDark(ActionEvent event) {
		ResourceManager.setDarkTheme();

		self.getStylesheets().clear();
		self.getStylesheets().add(ResourceManager.currentCSS);

		currentPane.getStylesheets().clear();
		currentPane.getStylesheets().add(ResourceManager.currentCSS);
	}

	@FXML
	void handleAnimal(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/AnimalEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Event Type Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleBreed(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/RaceEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Breed Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleLivestock(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/LivestockEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Livestock Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleET(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/EventTypeEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Event Type Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleEvent(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/EventEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Event Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleHold(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marmar/farmapp/view/PenEdit.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("Holding Area Window");
		Parent root = (Parent) loader.load();
		root.getStylesheets().add(ResourceManager.currentCSS);
		stage.setScene(new Scene(root));
		stage.show();
	}

	@FXML
	void handleAbout(ActionEvent event) {

	}

	@Override
	public void setLabels() {
		ResourceBundle msg = local.getMessages();

		menuFile.setText(msg.getString("menu.file"));
		menuView.setText(msg.getString("menu.view"));
		menuRegister.setText(msg.getString("menu.register"));
		menuHelp.setText(msg.getString("menu.help"));

		miAnimal.setText(msg.getString("menu.new.animal"));
		miBreed.setText(msg.getString("menu.new.breed"));
		miLivestock.setText(msg.getString("menu.new.livestock"));
		miET.setText(msg.getString("menu.new.eventtype"));
		miEvent.setText(msg.getString("menu.new.event"));
		miHold.setText(msg.getString("menu.new.hold"));

		hlLogOut.setText(msg.getString("msg.logout"));
		lbCopyright.setText(msg.getString("msg.copyright") + " 2017");

	}

}
