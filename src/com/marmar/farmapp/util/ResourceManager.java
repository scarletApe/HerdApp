package com.marmar.farmapp.util;

import javafx.scene.image.Image;

public class ResourceManager {

	public static Image gato;

	public static String listCSS;
	public static String metroCSS;
	public static String darkCSS;
	
	public static Localizer localizer;

	public void init() {
		gato = new javafx.scene.image.Image("/com/marmar/farmapp/images/gato.png");

		listCSS = getClass().getResource("/com/marmar/farmapp/css/listView.css").toExternalForm();
		metroCSS = getClass().getResource("/com/marmar/farmapp/css/Metro-UI.css").toExternalForm();
		
		localizer = new Localizer("es");
	}

}
