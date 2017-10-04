package com.marmar.farmapp.util.writers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.model.Ranch;

public class HTMLContentMaker {

	private LivestockDAO crudLive;
	private RanchDAO crudRanch;
	private AnimalDAO crudAnimal;

	public HTMLContentMaker() {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		crudLive = ctx.getBean(LivestockDAO.class);
		crudAnimal = ctx.getBean(AnimalDAO.class);
		crudRanch = ctx.getBean(RanchDAO.class);
	}

	private void addHeader(StringBuilder sb) {

		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>Principal</title>\n");

		sb.append("<style type=\"text/css\">");

		sb.append("table {");
		sb.append("margin-top: 20px;");
		sb.append("margin-bottom: 20px;");
		sb.append("border-collapse: collapse;");
		sb.append("border-spacing: 0;");
		sb.append("border-radius: 3px;");
		sb.append("width: 100%; border: 1px;");
		sb.append("}");

		sb.append("table, table th, table td {");
		sb.append("text-align: left;");
		sb.append("padding: 8px;");
		sb.append("}");

		sb.append("table tr th{ background-color:#c8b493; color:white; text-align:center;} ");

		sb.append(".alt{background-color: #f2f2f2;} ");

		sb.append("h1 { color:black; } ");

		sb.append("#lgb { background-color:#8d978d; color:white; }");
		sb.append("#lbb { background-color:#c8b493; color:white; }");
		sb.append("#grey { background-color:#a9ac9f; color:white; }");
		sb.append("</style>");

		sb.append("</head>\n");
		sb.append("<body>\n");
	}

	private void addFooter(StringBuilder sb) {
		sb.append("<div class=\"footer\">\n");
		sb.append("<hr></hr> \n");
		sb.append("<center> <p>  Copyright 2017 Todos los Derechos Reservados </p> </center> \n");
		sb.append("</div> <!-- end .footer -->\n");

		sb.append("</body>\n");
		sb.append("</html>\n");
	}

	public String getInitialReport() {
		StringBuilder sb = new StringBuilder();
		addHeader(sb);

		// add head text
		sb.append("<h1>Sample PDF File</h1> \n");

		// body
		sb.append("<p> <span class=\"graytext\"> Texting creating a table:</span> </p> \n");

		// add content
		sb.append("<table>\n");
		for (int i = 0; i < 10; i++) {
			if (i == 0) {
				sb.append("<tr>\n");
				sb.append("<th>HOLA</th>");
				sb.append("<th>HOLA</th>");
				sb.append("</tr>\n");
				continue;
			}
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
				sb.append("<td>hola</td>");
				sb.append("<td>hola</td>");
				sb.append("</tr>\n");
				continue;
			}
			sb.append("<tr>");
			sb.append("<td>hola</td>");
			sb.append("<td>hola</td>");
			sb.append("</tr>\n");
		}
		sb.append("</table>\n");

		// add the footer
		addFooter(sb);

		return sb.toString();
	}

	public String getReportFromListOfWritables(ArrayList<Writable> list, String nombre) {
		StringBuilder sb = new StringBuilder();

		// add the header
		addHeader(sb);

		// add title text
		sb.append("<h1>" + nombre + " </h1> \n");

		// add the table *******************************************
		sb.append("<table>\n");

		// los nombres de las columnas
		sb.append("<tr>\n");
		Object[] names = list.get(0).getNames();
		for (Object obj : names) {
			if (obj instanceof String) {
				sb.append("<th>" + (String) obj + "</th>");
			}
		}
		sb.append("</tr>\n");

		// the table data
		// write the content of the list
		Writable writ;
		Object[] objArr;
		for (int i = 0; i < list.size(); i++) {

			writ = list.get(i);
			objArr = writ.getAsArray();
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
			} else {
				sb.append("<tr>");
			}

			for (Object obj : objArr) {
				if (obj instanceof Date) {
					sb.append("<td>" + (Date) obj + "</td>");
				} else if (obj instanceof Boolean) {
					sb.append("<td>" + (Boolean) obj + "</td>");
				} else if (obj instanceof String) {
					sb.append("<td>" + (String) obj + "</td>");
				} else if (obj instanceof Double) {
					sb.append("<td>" + (Double) obj + "</td>");
				} else if (obj instanceof Integer) {
					sb.append("<td>" + (Integer) obj + "</td>");
				} else {
					sb.append("<td></td>");
				}
			}
			sb.append("</tr>\n");
		}

		sb.append("</table>\n");// *********************************

		// sb.append("<p> <span class=\"graytext\"> Items in the table: " +
		// list.size() + "</span> </p> \n");

		sb.append("Items in the table: " + list.size() + "<br></br> \n");

		// add the footer
		addFooter(sb);

		return sb.toString();
	}

	public String getGeneralReport() {
		StringBuilder sb = new StringBuilder();

		// add the header
		addHeader(sb);

		// add title text
		sb.append("<h2> Reporte General  </h2> \n");
		sb.append("<hr></hr> \n");

		// farm details++++++++++++++++++++++++++++++++++++++++
		sb.append("<table>\n");
		Ranch r = crudRanch.getAll().get(0);

		sb.append("<tr> <th colspan=\"4\"> Información de la Granja </th></tr>\n");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Nombre de Granja: </td>");
		sb.append("<td>" + r.getName() + "</td>");
		sb.append("<td> RFC: </td>");
		sb.append("<td>" + r.getRfc() + "</td>");
		sb.append("</tr>\n");

		sb.append("<tr>");
		sb.append("<td> Email: </td>");
		sb.append("<td>" + r.getEmail() + "</td>");
		sb.append("<td> Teléfono: </td>");
		sb.append("<td>" + r.getPhone() + "</td>");
		sb.append("</tr>\n");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Domicilio: </td>");
		sb.append("<td colspan=\"3\">" + r.getAddress() + "</td>");
		sb.append("</tr>\n");

		sb.append("</table>\n");// +++++++++++++++++++++++++++++

		// get all the animal types
		List<Animal> animals = crudAnimal.getAll();
		for (int i = 0; i < animals.size(); i++) {
			addLivestockDetail(sb, animals.get(i));
		}

		// add the footer
		addFooter(sb);

		return sb.toString();
	}

	private void addLivestockDetail(StringBuilder sb, Animal a) {
		sb.append("<h3>" + a.getName() + " Información </h3>");
		sb.append("<hr></hr> \n");

		ArrayList<Livestock> bovinos = crudLive.getLivestockFiltred(0, "Active Only", "All", a, "All Ages", 0);
		HashMap<String, Integer> data = dataForChartEdadesySexo(a, bovinos);

		// livestock table
		sb.append("<table>\n");

		// header
		sb.append("<tr>");
		sb.append("<th> ID </th>");
		sb.append("<th> Arete </th>");
		sb.append("<th> Genero </th>");
		sb.append("<th> Nombre </th>");
		sb.append("<th> Color </th>");
		sb.append("<th> Raza y Especie </th>");
		sb.append("<th> Fecha N. </th>");
		sb.append("</tr>\n");

		Livestock li;
		for (int i = 0; i < bovinos.size(); i++) {
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
			} else {
				sb.append("<tr>");
			}
			li = bovinos.get(i);
			sb.append("<td> " + li.getId_livestock() + " </td>");
			sb.append("<td> " + li.getEar_ring() + " </td>");
			sb.append("<td> " + li.getSex() + " </td>");
			sb.append("<td> " + li.getName() + " </td>");
			sb.append("<td> " + li.getColor() + " </td>");
			sb.append("<td> " + li.getId_race_1().toString() + " </td>");
			sb.append("<td> " + li.getDate_birth() + " </td>");

			sb.append("</tr>");
		}

		sb.append("</table>\n");

		// births and deaths this year
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		HashMap<Integer, int[]> births = dataForChartNacimientos(a);
		HashMap<Integer, int[]> deaths = dataForChartMuertes(a);

		int[] bs = births.get(year);
		int[] dts = deaths.get(year);

		sb.append("<table>\n");
		sb.append("<tr> <th colspan=\"4\"> " + a.getName() + " Detalles del " + year + " </th></tr>\n");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Nacimientos este Año </td>");
		int n = (bs == null) ? 0 : getTotal(bs);
		sb.append("<td>" + n + "</td>");
		sb.append("<td> Muertes este Año </td>");
		n = (dts == null) ? 0 : getTotal(dts);
		sb.append("<td>" + n + "</td>");
		sb.append("</tr>\n");
		sb.append("</table>\n");

		// pop by age group
		sb.append("<table>\n");
		sb.append("<tr> <th colspan=\"4\"> " + a.getName() + " Población por Grupos de Edad </th></tr>\n");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Hembras Chicas </td>");
		sb.append("<td>" + data.get("Infant Females") + "</td>");
		sb.append("<td> Machos Chicos </td>");
		sb.append("<td>" + data.get("Infant Males") + "</td>");
		sb.append("</tr>\n");

		sb.append("<tr>");
		sb.append("<td> Hembras Juveniles </td>");
		sb.append("<td>" + data.get("Juvenile Females") + "</td>");
		sb.append("<td> Machos Juveniles </td>");
		sb.append("<td>" + data.get("Juvenile Males") + "</td>");
		sb.append("</tr>\n");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Hembras Adultas </td>");
		sb.append("<td>" + data.get("Adult Females") + "</td>");
		sb.append("<td> Machos Adultos </td>");
		sb.append("<td>" + data.get("Adult Males") + "</td>");
		sb.append("</tr>\n");

		sb.append("<tr>");
		sb.append("<td> Total " + a.getName() + " </td>");
		sb.append("<td>" + bovinos.size() + "</td>");
		sb.append("</tr>\n");

		sb.append("</table>\n");

	}

	private int getTotal(int[] arr) {
		int total = 0;
		for (int i = 0; i < arr.length; i++) {
			total = total + arr[i];
		}
		return total;
	}

	/**
	 * This method returns a hashmap of how many cows, bulls, novillos ...etc
	 * there is.
	 *
	 * @return
	 */
	private HashMap<String, Integer> dataForChartEdadesySexo(Animal a, ArrayList<Livestock> bovinos) {
		int adult_female = 0;
		int juvenile_female = 0;
		int infant_female = 0;

		int adult_male = 0;
		int juvenile_male = 0;
		int infant_male = 0;

		int months_to_adult = a.getTo_adult();
		int months_to_juvenile = a.getTo_juvenile();

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
		map.put("Adult Females", adult_female);
		map.put("Adult Males", adult_male);
		map.put("Juvenile Females", juvenile_female);
		map.put("Juvenile Males", juvenile_male);
		map.put("Infant Females", infant_female);
		map.put("Infant Males", infant_male);

		return map;
	}

	/**
	 * This method returns a hashmap where the key is for the year and the array
	 * is the months of the year and the number of calfs born per month.
	 *
	 * @return //
	 */
	private HashMap<Integer, int[]> dataForChartNacimientos(Animal ani) {
		HashMap<Integer, int[]> map = new HashMap<>();

		ArrayList<Livestock> bovinos = crudLive.getLivestockFiltred(0, "Active & Archived", "All", ani, "All Ages", 0);

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

	/**
	 * This method returns a hashmap where the key is for the year and the array
	 * is the months of the year and the number of deaths per month.
	 *
	 * @return
	 */
	private HashMap<Integer, int[]> dataForChartMuertes(Animal ani) {
		HashMap<Integer, int[]> map = new HashMap<>();

		ArrayList<Livestock> bovinos = crudLive.getLivestockFiltred(0, "Active & Archived", "All", ani, "All Ages", 0);

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
}
