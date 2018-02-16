package com.marmar.farmapp.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LoadService extends Service<PanesData> {

	@Override
	protected Task<PanesData> createTask() {
		return new Task<PanesData>() {

			@Override
			protected PanesData call() throws IOException, MalformedURLException {

				System.out.println("Running Service");
				PanesData foo = new PanesData();
				ArrayList<Internationable> inters = new ArrayList<>(11);

				AnchorPane paneAnimals;
				AnchorPane paneRaces;
				AnchorPane paneUsers;
				AnchorPane paneEventTypes;
				AnchorPane paneEvents;
				AnchorPane paneRanch;
				AnchorPane paneLivestock;
				AnchorPane panePens;
				AnchorPane panePenManager;
				AnchorPane paneCharts;
				AnchorPane paneWelcome;
				// AnchorPane paneGallery;

				Duo duo;

				// paneLivestock
				duo = loadPane("/com/marmar/farmapp/view/Livestock.fxml");
				paneLivestock = duo.pane;
				inters.add(duo.inter);
				updateProgress(1, 12);

				// pane Animals Types
				duo = loadPane("/com/marmar/farmapp/view/Animal.fxml");
				paneAnimals = duo.pane;
				inters.add(duo.inter);
				updateProgress(2, 12);

				// pane Races or Breeds
				duo = loadPane("/com/marmar/farmapp/view/Race.fxml");
				paneRaces = duo.pane;
				inters.add(duo.inter);
				updateProgress(3, 12);

				// pane gallery
				// duo = loadPane("/com/marmar/farmapp/view/LivestockGallery.fxml");
				// paneGallery = duo.pane;
				// inters.add(duo.inter);
				// TODO
				updateProgress(4, 12);

				// manage holding areas
				duo = loadPane("/com/marmar/farmapp/view/PenManager.fxml");
				panePenManager = duo.pane;
				inters.add(duo.inter);
				updateProgress(5, 12);

				// pane Holding Areas
				duo = loadPane("/com/marmar/farmapp/view/Pens.fxml");
				panePens = duo.pane;
				inters.add(duo.inter);
				updateProgress(6, 12);

				// pane Events
				duo = loadPane("/com/marmar/farmapp/view/Event.fxml");
				paneEvents = duo.pane;
				inters.add(duo.inter);
				updateProgress(7, 12);

				// pane EventTypes
				duo = loadPane("/com/marmar/farmapp/view/EventTypes.fxml");
				paneEventTypes = duo.pane;
				inters.add(duo.inter);
				updateProgress(8, 12);

				// pane Charts
				duo = loadPane("/com/marmar/farmapp/view/Charts.fxml");
				paneCharts = duo.pane;
				inters.add(duo.inter);
				updateProgress(9, 12);
				
				// pane Welcome
				duo = loadPane("/com/marmar/farmapp/view/Welcome.fxml");
				paneWelcome = duo.pane;
				inters.add(duo.inter);
				updateProgress(9, 12);

				// pane Reports
				// paneReports = loadPane("/com/marmar/farmapp/view/Reports.fxml");
				updateProgress(10, 12);

				// pane Users
				duo = loadPane("/com/marmar/farmapp/view/User.fxml");
				paneUsers = duo.pane;
				inters.add(duo.inter);
				updateProgress(11, 12);

				// pane Ranch or farm
				duo = loadPane("/com/marmar/farmapp/view/Ranch.fxml");
				paneRanch = duo.pane;
				inters.add(duo.inter);
				updateProgress(12, 12);

				foo.setPaneAnimals(paneAnimals);
				foo.setPaneRaces(paneRaces);
				foo.setPaneUsers(paneUsers);
				foo.setPaneEventTypes(paneEventTypes);
				foo.setPaneEvents(paneEvents);
				foo.setPaneRanch(paneRanch);
				foo.setPaneLivestock(paneLivestock);
				foo.setPanePens(panePens);
				foo.setPanePenManager(panePenManager);
				foo.setPaneCharts(paneCharts);
				foo.setPaneWelcome(paneWelcome);
				// foo.setPaneReports(paneReports);
				// foo.setPaneGallery(paneGallery); TODO
				foo.setInters(inters);

				return foo;
			}

			private Duo loadPane(String res) throws IOException {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(res));
				Duo duo = new Duo();
				duo.pane = (AnchorPane) loader.load();
				duo.inter = loader.<Internationable>getController();
				return duo;
			}

		};
	}

}

class PanesData {
	AnchorPane paneAnimals;
	AnchorPane paneRaces;
	AnchorPane paneUsers;
	AnchorPane paneEventTypes;
	AnchorPane paneEvents;
	AnchorPane paneRanch;
	AnchorPane paneLivestock;
	AnchorPane panePens;
	AnchorPane panePenManager;
	AnchorPane paneCharts;
	AnchorPane paneReports;
	AnchorPane paneGallery;
	AnchorPane paneWelcome;

	ArrayList<Internationable> inters;

	public PanesData() {

	}

	public AnchorPane getPaneAnimals() {
		return paneAnimals;
	}

	public void setPaneAnimals(AnchorPane paneAnimals) {
		this.paneAnimals = paneAnimals;
	}

	public AnchorPane getPaneRaces() {
		return paneRaces;
	}

	public void setPaneRaces(AnchorPane paneRaces) {
		this.paneRaces = paneRaces;
	}

	public AnchorPane getPaneUsers() {
		return paneUsers;
	}

	public void setPaneUsers(AnchorPane paneUsers) {
		this.paneUsers = paneUsers;
	}

	public AnchorPane getPaneEventTypes() {
		return paneEventTypes;
	}

	public void setPaneEventTypes(AnchorPane paneEventTypes) {
		this.paneEventTypes = paneEventTypes;
	}

	public AnchorPane getPaneEvents() {
		return paneEvents;
	}

	public void setPaneEvents(AnchorPane paneEvents) {
		this.paneEvents = paneEvents;
	}

	public AnchorPane getPaneRanch() {
		return paneRanch;
	}

	public void setPaneRanch(AnchorPane paneRanch) {
		this.paneRanch = paneRanch;
	}

	public AnchorPane getPaneLivestock() {
		return paneLivestock;
	}

	public void setPaneLivestock(AnchorPane paneLivestock) {
		this.paneLivestock = paneLivestock;
	}

	public AnchorPane getPanePens() {
		return panePens;
	}

	public void setPanePens(AnchorPane panePens) {
		this.panePens = panePens;
	}

	public AnchorPane getPanePenManager() {
		return panePenManager;
	}

	public void setPanePenManager(AnchorPane panePenManager) {
		this.panePenManager = panePenManager;
	}

	public AnchorPane getPaneCharts() {
		return paneCharts;
	}

	public void setPaneCharts(AnchorPane paneCharts) {
		this.paneCharts = paneCharts;
	}

	public AnchorPane getPaneReports() {
		return paneReports;
	}

	public void setPaneReports(AnchorPane paneReports) {
		this.paneReports = paneReports;
	}

	public AnchorPane getPaneGallery() {
		return paneGallery;
	}

	public void setPaneGallery(AnchorPane paneGallery) {
		this.paneGallery = paneGallery;
	}

	public ArrayList<Internationable> getInters() {
		return inters;
	}

	public void setInters(ArrayList<Internationable> inters) {
		this.inters = inters;
	}

	public AnchorPane getPaneWelcome() {
		return paneWelcome;
	}

	public void setPaneWelcome(AnchorPane paneWelcome) {
		this.paneWelcome = paneWelcome;
	}

}

interface Internationable {
	public void setLabels();
}

class Duo {
	protected AnchorPane pane;
	protected Internationable inter;
}