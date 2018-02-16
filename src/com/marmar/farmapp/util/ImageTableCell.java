package com.marmar.farmapp.util;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<S> extends TableCell<S, Image> {
	final ImageView imageView = new ImageView();

	public ImageTableCell() {
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}

	@Override
	protected void updateItem(Image item, boolean empty) {
		super.updateItem(item, empty);

		if (empty || item == null) {
			imageView.setImage(null);
			setText(null);
			setGraphic(null);
		}

		imageView.setImage(item);
		imageView.setFitHeight(60);
		imageView.setFitWidth(70);
		setGraphic(imageView);
	}
}
