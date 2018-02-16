package com.marmar.farmapp.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

import com.jfoenix.controls.JFXButton;
import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.LivestockHoldingDAO;
import com.marmar.farmapp.model.HoldingArea;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.model.LivestockHolding;
import com.marmar.farmapp.util.Localizer;
import com.marmar.farmapp.util.ResourceManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PenAddRemoveController implements Initializable ,Internationable{

	@FXML
	private JFXButton btnCancel;

	@FXML
	private Label lbUnassigned;

	@FXML
	private ListView<Livestock> listHold;

	@FXML
	private JFXButton btnSave;

	@FXML
	private JFXButton btnRemoveAll;

	@FXML
	private JFXButton btnAdd;

	@FXML
	private Label lbLabel;

	@FXML
	private Label lbHold;

	@FXML
	private JFXButton btnRemove;

	@FXML
	private ListView<Livestock> listUnassigned;

	@FXML
	private JFXButton btnAddAll;

	private HoldingArea ha;
	private LivestockHoldingDAO lhDAO;
	private Localizer local;
	private ArrayList<LivestockHolding> livestockInHold;
	// private ArrayList<Livestock> unassignedLivestock;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		lhDAO = ctx.getBean(LivestockHoldingDAO.class);

		local = ResourceManager.localizer;
		setLabels();

		// fill the list with the unassigned animals
		ArrayList<Livestock> livestock = lhDAO.getUnassignedLivestock();
		ObservableList<Livestock> data = FXCollections.observableArrayList();
		for (int i = 0; i < livestock.size(); i++) {
			data.add(livestock.get(i));
		}
		listUnassigned.setItems(data);
		// unassignedLivestock = livestock;
	}

	public void setLabels() {
		ResourceBundle msg = local.getMessages();

		lbUnassigned.setText(msg.getString("label.unassigned") + ":");
//		lbHold.setText(msg.getString("label.description") + ":");
		lbLabel.setText(msg.getString("label.transfer") + ":");

		btnCancel.setText(msg.getString("button.cancel"));
		btnSave.setText(msg.getString("button.save"));

		btnAdd.setText(msg.getString("label.add.one"));
		btnAddAll.setText(msg.getString("label.add.all"));
		btnRemove.setText(msg.getString("label.remove.one"));
		btnRemoveAll.setText(msg.getString("label.remove.all"));
	}

	public void initData(HoldingArea u) {
		this.ha = u;

		ResourceBundle msg = local.getMessages();
		lbHold.setText(msg.getString("label.pen")+" " + u.getName()+":");

		// Fill the list with the animals in the hold
		ArrayList<LivestockHolding> livestock = lhDAO.getLivestockInHold(ha);
		ObservableList<Livestock> data = FXCollections.observableArrayList();
		for (int i = 0; i < livestock.size(); i++) {
			data.add(livestock.get(i).getLivestock());
		}
		listHold.setItems(data);
		livestockInHold = livestock;
	}

	@FXML
	void handleCancel(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	void handleSave(ActionEvent event) {
		// at the end of the day, well at the end of the click actually
		// the ones who are left on the unassigned list set them as unassigned**********
		ObservableList<Livestock> un = listUnassigned.getItems();

		// search if one was active and now is to be deactivated, do it
		for (int i = 0; i < un.size(); i++) {
			LivestockHolding lh = getFromHold(un.get(i), livestockInHold);
			if (lh != null) {
				lh.setActive(false);
				lh.setDate_removed(Calendar.getInstance().getTime());
				lhDAO.update(lh);
			}
		}

		// the ones on the right set them to the hold if they weren't already
		ObservableList<Livestock> held = listHold.getItems();

		// put the livestock in the hold if they were't already
		for (int i = 0; i < held.size(); i++) {
			LivestockHolding lh = getFromHold(held.get(i), livestockInHold);
			if (lh == null) {
				// this animal is not in the hold
				// create a new registry for it in this holding area
				lh = new LivestockHolding(ha, held.get(i), Calendar.getInstance().getTime(), true);
				lhDAO.saveNew(lh);
			} else {
				// already in, do nothing
				System.out.println("live already in hold, do nothing");
			}
		}
		//close the window
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	private LivestockHolding getFromHold(Livestock live, ArrayList<LivestockHolding> list) {
		for (int i = 0; i < list.size(); i++) {
			if (live.equals(list.get(i).getLivestock())) {
				return list.get(i);
			}
		}
		return null;
	}

	@FXML
	void handleAdd(ActionEvent event) {
		// move one from the left to the right
		Livestock selectedItem = listUnassigned.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			listUnassigned.getItems().remove(selectedItem);
			listHold.getItems().add(selectedItem);
		}
	}

	@FXML
	void handleAddAll(ActionEvent event) {
		// move all from the left to the right

		// get the unassigned animals
		ObservableList<Livestock> items = listUnassigned.getItems();

		// add them to the hold list
		listHold.getItems().addAll(items);

		// remove from the unassigned list
		listUnassigned.getItems().clear();
	}

	@FXML
	void handleRemove(ActionEvent event) {
		// move one from the right to the left
		Livestock selectedItem = listHold.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			listHold.getItems().remove(selectedItem);
			listUnassigned.getItems().add(selectedItem);
		}
	}

	@FXML
	void handleRemoveAll(ActionEvent event) {
		// move all from the right to the left

		// get the animals in the hold
		ObservableList<Livestock> items = listHold.getItems();

		// add them to the unassigned list
		listUnassigned.getItems().addAll(items);

		// remove from the hold list
		listHold.getItems().clear();
	}

}
