/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marmar.farmapp.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.context.ApplicationContext;

import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Livestock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author manuelmartinez
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ChartManager {

	private LivestockDAO crud;
	private ResourceBundle msg;

	public ChartManager() {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		crud = ctx.getBean(LivestockDAO.class);
		Localizer local = ResourceManager.localizer;
		msg = local.getMessages();
	}

	public LineChart<String, Number> fillChartMuertes(Animal ani) {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(msg.getString("label.months"));
		final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle(ani.getName() + " " + msg.getString("label.chart.deaths.year"));

		// lineChart.setCreateSymbols(false);
		lineChart.setAlternativeRowFillVisible(false);

		HashMap<Integer, int[]> data = dataForChartMuertes(ani);
		Set<Integer> keySet = data.keySet();
		for (Integer key : keySet) {
			int[] get = data.get(key);

			XYChart.Series series = new XYChart.Series();
			series.setName("" + key);
			series.getData().add(new XYChart.Data("Ene", get[1]));
			series.getData().add(new XYChart.Data("Feb", get[2]));
			series.getData().add(new XYChart.Data("Mar", get[3]));
			series.getData().add(new XYChart.Data("Abr", get[4]));
			series.getData().add(new XYChart.Data("May", get[5]));
			series.getData().add(new XYChart.Data("Jun", get[6]));
			series.getData().add(new XYChart.Data("Jul", get[7]));
			series.getData().add(new XYChart.Data("Aug", get[8]));
			series.getData().add(new XYChart.Data("Sep", get[9]));
			series.getData().add(new XYChart.Data("Oct", get[10]));
			series.getData().add(new XYChart.Data("Nov", get[11]));
			series.getData().add(new XYChart.Data("Dec", get[12]));

			lineChart.getData().add(series);
		}
		return (lineChart);
	}

	/**
	 * This method returns a hashmap where the key is for the year and the array is
	 * the months of the year and the number of deaths per month.
	 *
	 * @return
	 */
	private HashMap<Integer, int[]> dataForChartMuertes(Animal ani) {
		HashMap<Integer, int[]> map = new HashMap<>();

		ArrayList<Livestock> bovinos = crud.getLivestockFiltred(0, 0, 0, ani, 0, 0,true);

		int year;
		int month;
		Calendar cal = Calendar.getInstance();
		for (Livestock bovino : bovinos) {
			Date fm = bovino.getDate_death();
			if (fm != null) {

				cal.setTime(fm);
				year = cal.get(Calendar.YEAR);
				month = cal.get(Calendar.MONTH) + 1;

				if (map.containsKey(year)) {
					int[] get = map.get(year);
					get[month] = get[month] + 1;
					map.put(year, get);
				} else {
					int[] a = new int[13];
					a[month] = 1;
					map.put(year, a);
				}
			}
		}

		return map;
	}

	public LineChart<String, Number> fillChartNacimientos(Animal ani) {
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel(msg.getString("label.months"));
		final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

		lineChart.setTitle(ani.getName() + " " + msg.getString("label.chart.births.year"));

		// lineChart.setCreateSymbols(false);
		lineChart.setAlternativeRowFillVisible(false);

		HashMap<Integer, int[]> data = dataForChartNacimientos(ani);
		Set<Integer> keySet = data.keySet();
		for (Integer key : keySet) {
			int[] get = data.get(key);

			XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
			series.setName("" + key);
			series.getData().add(new XYChart.Data<String, Number>("Ene", get[1]));
			series.getData().add(new XYChart.Data<String, Number>("Feb", get[2]));
			series.getData().add(new XYChart.Data<String, Number>("Mar", get[3]));
			series.getData().add(new XYChart.Data<String, Number>("Abr", get[4]));
			series.getData().add(new XYChart.Data<String, Number>("May", get[5]));
			series.getData().add(new XYChart.Data<String, Number>("Jun", get[6]));
			series.getData().add(new XYChart.Data<String, Number>("Jul", get[7]));
			series.getData().add(new XYChart.Data<String, Number>("Aug", get[8]));
			series.getData().add(new XYChart.Data<String, Number>("Sep", get[9]));
			series.getData().add(new XYChart.Data<String, Number>("Oct", get[10]));
			series.getData().add(new XYChart.Data<String, Number>("Nov", get[11]));
			series.getData().add(new XYChart.Data<String, Number>("Dec", get[12]));

			lineChart.getData().add(series);
		}
		return (lineChart);
	}

	/**
	 * This method returns a hashmap where the key is for the year and the array is
	 * the months of the year and the number of calfs born per month.
	 *
	 * @return //
	 */
	private HashMap<Integer, int[]> dataForChartNacimientos(Animal ani) {
		HashMap<Integer, int[]> map = new HashMap<>();

		ArrayList<Livestock> bovinos = crud.getLivestockFiltred(0, 0, 0, ani, 0, 0,true);

		int year;
		int month;
		Calendar cal = Calendar.getInstance();
		for (Livestock bovino : bovinos) {
			cal.setTime(bovino.getDate_birth());
			year = cal.get(Calendar.YEAR);
			month = cal.get(Calendar.MONTH) + 1;
			// System.out.println("year="+year+" month="+month);

			if (map.containsKey(year)) {
				int[] get = map.get(year);
				get[month] = get[month] + 1;
				map.put(year, get);
			} else {
				int[] a = new int[13];
				a[month] = 1;
				map.put(year, a);
			}
		}

		return map;
	}

	public BarChart<Number, String> fillChartRazas(Animal ani) {

		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		final BarChart<Number, String> chart = new BarChart<>(yAxis, xAxis);
		chart.setTitle(ani.getName() + " " + msg.getString("label.chart.breeds"));
		xAxis.setLabel(msg.getString("label.breed"));
		// xAxis.setTickLabelRotation(90);
		yAxis.setLabel(msg.getString("label.chart.heads") + " " + ani.getName());

		HashMap<String, Integer> data = dataForChartRaza(ani);
		Set<String> keySet = data.keySet();
		XYChart.Series<Number, String> series1 = new XYChart.Series<Number, String>();
		series1.setName(msg.getString("label.breed"));
		ObservableList olist = series1.getData();
		for (String key : keySet) {
			olist.add(new XYChart.Data<Number, String>(data.get(key), key));
		}
		chart.getData().addAll(series1);
		chart.setLegendVisible(false);
		// set it to pane
		return (chart);
	}

	/**
	 * This method return a hashmap with the key representing the bovine race and
	 * value the numer of animals that are that race.
	 *
	 * @return
	 */
	private HashMap<String, Integer> dataForChartRaza(Animal ani) {
		HashMap<String, Integer> map = new HashMap<>();
		ArrayList<Livestock> bovinos = crud.getLivestockFiltred(0, 1, 0, ani, 0, 0,true);

		String r;
		for (Livestock b : bovinos) {
			r = b.getId_race_1().getName();
			if (map.containsKey(r)) {
				Integer get = map.get(r);
				get = get + 1;
				map.put(r, get);
			} else {
				map.put(r, 1);
			}
		}

		return map;
	}

	public BarChart<Number, String> fillChartBarPopLivestock(Animal a) {
		final NumberAxis yAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		final BarChart<Number, String> chart = new BarChart<>(yAxis, xAxis);
		chart.setTitle(a.getName() + " " + msg.getString("label.chart.livestock.bar"));
		xAxis.setLabel(a.getName());
		// xAxis.setTickLabelRotation(90);
		yAxis.setLabel(msg.getString("label.chart.heads") + " " + a.getName());

		HashMap<String, Integer> data = dataForChartEdadesySexo(a);
		Set<String> keySet = data.keySet();
		XYChart.Series series1 = new XYChart.Series();
		ObservableList olist = series1.getData();
		for (String key : keySet) {
			olist.add(new XYChart.Data<>(data.get(key), key));
		}

		chart.getData().addAll(series1);
		chart.setLegendVisible(false);
		// set it to pane
		return (chart);
	}

	/**
	 * This method returns a hashmap of how many cows, bulls, novillos ...etc there
	 * is.
	 *
	 * @return
	 */
	private HashMap<String, Integer> dataForChartEdadesySexo(Animal a) {
		int adult_female = 0;
		int juvenile_female = 0;
		int infant_female = 0;

		int adult_male = 0;
		int juvenile_male = 0;
		int infant_male = 0;

		int months_to_adult = a.getTo_adult();
		int months_to_juvenile = a.getTo_juvenile();

		ArrayList<Livestock> bovinos = crud.getLivestockFiltred(0, 1, 0, a, 0, 0,true);
		int gender;
		int months;

		for (Livestock b : bovinos) {
			gender = b.getGender();
			months = b.getAge();
			if (gender == 1) {
				// it's female
				if (months >= months_to_adult) {
					adult_female++;
				}
				if (months >= months_to_juvenile && months < months_to_adult) {
					juvenile_female++;
				}
				if (months < months_to_juvenile) {
					infant_female++;
				}
			} else {
				// it's male
				if (months >= months_to_adult) {
					adult_male++;
				}
				if (months >= months_to_juvenile && months < months_to_adult) {
					juvenile_male++;
				}
				if (months < months_to_juvenile) {
					infant_male++;
				}
			}
		}
		HashMap<String, Integer> map = new HashMap<>();
		map.put(msg.getString("label.chart.female.adult"), adult_female);
		map.put(msg.getString("label.chart.male.adult"), adult_male);
		map.put(msg.getString("label.chart.female.juvenile"), juvenile_female);
		map.put(msg.getString("label.chart.male.juvenile"), juvenile_male);
		map.put(msg.getString("label.chart.female.infant"), infant_female);
		map.put(msg.getString("label.chart.male.infant"), infant_male);

		return map;
	}

	public PieChart fillChartPiePopLivestock(Animal a) {

		HashMap<String, Integer> data = dataForChartEdadesySexo(a);
		int total = 0;
		Set<String> keySet = data.keySet();
		for (String key : keySet) {
			total = total + data.get(key);
		}
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		System.out.println("total=" + total);
		int percent;
		double di;
		for (String key : keySet) {
			di = (double) data.get(key) / (double) total;
			percent = (int) (di * 100);
			pieChartData.add(new PieChart.Data(key, percent));
		}

		final PieChart chart = new PieChart(pieChartData);
		chart.setTitle(a.getName() + " " + msg.getString("label.chart.livestock.pie"));

		// set it to pane
		return (chart);
	}
}
