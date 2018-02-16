package com.marmar.farmapp.util;

import javafx.scene.image.Image;

public class ResourceManager {

	public static Image gato;
	public static Image warningImg;
	public static Image successImg;

	public static String listCSS;
	public static String currentCSS;
	public static String metroCSS;
	public static String darkCSS;

	public static Localizer localizer;

	public void init() {
		gato = new javafx.scene.image.Image("/com/marmar/farmapp/images/gato.png");
		warningImg = new javafx.scene.image.Image("/com/marmar/farmapp/images/warning.png");
		successImg = new javafx.scene.image.Image("/com/marmar/farmapp/images/success.png");

		listCSS = getClass().getResource("/com/marmar/farmapp/css/listView.css").toExternalForm();
		metroCSS = getClass().getResource("/com/marmar/farmapp/css/Metro-UI.css").toExternalForm();
		darkCSS = getClass().getResource("/com/marmar/farmapp/css/Metro_dark.css").toExternalForm();

		localizer = new Localizer("es");
		setLightTheme();
	}

	public static void setDarkTheme() {
		currentCSS = darkCSS;
	}

	public static void setLightTheme() {
		currentCSS = metroCSS;
	}
}
