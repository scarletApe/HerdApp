package com.marmar.farmapp.util.writers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.marmar.farmapp.config.Configuration;
import com.marmar.farmapp.hibernate.AnimalDAO;
import com.marmar.farmapp.hibernate.LivestockDAO;
import com.marmar.farmapp.hibernate.LivestockEventDAO;
import com.marmar.farmapp.hibernate.RanchDAO;
import com.marmar.farmapp.model.Animal;
import com.marmar.farmapp.model.Event;
import com.marmar.farmapp.model.Livestock;
import com.marmar.farmapp.model.LivestockEvent;
import com.marmar.farmapp.model.Ranch;
import com.marmar.farmapp.util.ResourceManager;

public class HTMLContentMaker {

	private LivestockDAO crudLive;
	private RanchDAO crudRanch;
	private AnimalDAO crudAnimal;
	// private EventDAO crudEvent;
	private LivestockEventDAO crudLiveEvent;

	public HTMLContentMaker() {
		ApplicationContext ctx = Configuration.getInstance().getApplicationContext();
		crudLive = ctx.getBean(LivestockDAO.class);
		crudAnimal = ctx.getBean(AnimalDAO.class);
		crudRanch = ctx.getBean(RanchDAO.class);
		// crudEvent = ctx.getBean(EventDAO.class);
		crudLiveEvent = ctx.getBean(LivestockEventDAO.class);
	}

	private void addHeader(StringBuilder sb) {

		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>Principal</title>\n");

		sb.append("<style type=\"text/css\">");

		styleBrown(sb);

		sb.append(".alt{background-color: #f2f2f2;} ");

		sb.append("h1 { color:black; } ");

		sb.append("#lgb { background-color:#8d978d; color:white; }");
		sb.append("#lbb { background-color:#c8b493; color:white; }");
		sb.append("#grey { background-color:#a9ac9f; color:white; }");
		sb.append("</style>");

		sb.append("</head>\n");
		sb.append("<body>\n");
	}

	private void styleBrown(StringBuilder sb) {
		sb.append("table {");
		sb.append("margin-top: 20px;");
		sb.append("margin-bottom: 20px;");
		sb.append("border-collapse: collapse;");
		sb.append("border-spacing: 0;");
		sb.append("border-radius: 3px;");
		sb.append("width: 100%; border: 1px; font-size: 12px;");
		sb.append("}");

		sb.append("table, table th, table td {");
		sb.append("text-align: left;");
		sb.append("padding: 8px;");
		sb.append("}");

		sb.append("table tr th{ background-color:#5a6a6f; color:white; text-align:center;} ");

		sb.append(".alt{background-color: #f2f2f2;} ");
	}

	// private void styleGrayZebra(StringBuilder sb) {
	// sb.append("table { color: #333; font-family: Helvetica, Arial, sans-serif;
	// font-size:12px;");
	// sb.append(
	// "border-collapse: collapse; border-spacing: 0; width: 100%; margin-top: 10px;
	// margin-bottom: 10px; }");
	//
	// sb.append("td, th { border: 1px solid transparent; height: 30px; } ");
	//
	// sb.append("th { background: #7aaf60; font-weight: bold; }");
	//
	// sb.append("td { background: #FAFAFA; text-align: left; padding-left:10px;
	// }");
	//
	// sb.append("tr:nth-child(odd) td { background: #F1F1F1; } ");
	//
	// sb.append("tr:nth-child(even) td { background: #FEFEFE; } ");
	// }

	private void addFooter(StringBuilder sb) {
		sb.append("<div class=\"footer\">\n");
		sb.append("<hr></hr> \n");
		Calendar c = Calendar.getInstance();
		sb.append("<center> <p> Copyright © " + c.get(Calendar.YEAR)
				+ " Marmar Ltd. Todos los Derechos Reservados </p> </center> \n");
		sb.append("</div> <!-- end .footer -->\n");

		// String s = getClass().getResource("help.png").toExternalForm();

		// String s = "file:///Users/juanmartinez/Desktop/jersey.jpg";
		// System.out.println("image url = " + s);
		// sb.append("<center><img src=\"").append(s).append("\" alt=\"no
		// image\"/></center>");

		sb.append("</body>\n");
		sb.append("</html>\n");
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
		sb.append("<h2> Reporte General de la Granja </h2> \n");
//		sb.append("<p> Reporte General de la Granja </p> \n");
		sb.append("<hr></hr> \n");

		// add farm info
		addFarmInfo(sb);

		// get all the animal species detail
		List<Animal> animals = crudAnimal.getAll();
		for (int i = 0; i < animals.size(); i++) {
			addLivestockDetail(sb, animals.get(i));
		}

		// add the footer
		addFooter(sb);

		return sb.toString();
	}

	private void addFarmInfo(StringBuilder sb) {
		// farm details++++++++++++++++++++++++++++++++++++++++
		sb.append("<table>\n");
		Ranch r = crudRanch.getAll().get(0);

		sb.append("<tr> <th colspan=\"4\"> Información de la Granja </th></tr>\n");

		// images of the farm stuff
		/* TODO */
		sb.append("<tr>");
		sb.append("<td> Fierro: </td>");
		String s = "file:///Users/juanmartinez/Desktop/fierro_jesus.png";
		sb.append("<td>").append("<img src=\"").append(s).append("\" alt=\"no image\" height=\"70\"/>").append("</td>");
		sb.append("<td> Logotipo: </td>");
		s = "file:///Users/juanmartinez/Desktop/farm_logo.png";
		sb.append("<td>").append("<img src=\"").append(s).append("\" alt=\"no image\" height=\"70\"/>").append("</td>");

		sb.append("</tr>\n");

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
	}

	private void addLivestockDetail(StringBuilder sb, Animal a) {
		sb.append("<h3> Información del Ganado Tipo " + a.getName() + " </h3>");
		sb.append("<hr></hr> \n");

		ArrayList<Livestock> bovinos = crudLive.getLivestockFiltred(0, 1, 0, a, 0, 0,true);
		HashMap<String, Integer> data = dataForChartEdadesySexo(a, bovinos);

		// livestock table
		sb.append("<table>\n");

		// header
		sb.append("<tr>");
		sb.append("<th> No. </th>");
		sb.append("<th> Arete </th>");
		sb.append("<th> Genero </th>");
		sb.append("<th> Nombre </th>");
		sb.append("<th> Color </th>");
		sb.append("<th> Raza y Especie </th>");
		sb.append("<th> Fecha N. </th>");
		sb.append("<th> Edad </th>");
		sb.append("</tr>\n");

		Livestock li;
		for (int i = 0; i < bovinos.size(); i++) {
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
			} else {
				sb.append("<tr>");
			}
			li = bovinos.get(i);
			sb.append("<td> " + (i+1) + " </td>");
			sb.append("<td> " + li.getEar_ring() + " </td>");
			sb.append("<td> " + li.getSex() + " </td>");
			sb.append("<td> " + li.getName() + " </td>");
			sb.append("<td> " + li.getColor() + " </td>");
			sb.append("<td> " + li.getId_race_1().toString() + " </td>");
			sb.append("<td> " + li.getDate_birth() + " </td>");
			sb.append("<td> " + li.getAge() + " </td>");

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
		sb.append("<tr> <th colspan=\"4\"> "+a.getName()+" Nacimientos y Muertes del año " + year + " </th></tr>\n");

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
		sb.append("<tr> <th colspan=\"4\"> Población de " + a.getName() + " por Grupos de Edad </th></tr>\n");

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
	 * This method returns a hashmap of how many cows, bulls, novillos ...etc there
	 * is.
	 *
	 * @return
	 */
	private HashMap<String, Integer> dataForChartEdadesySexo(Animal a, ArrayList<Livestock> livestock) {
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

		for (Livestock b : livestock) {
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
	 * This method returns a hashmap where the key is for the year and the array is
	 * the months of the year and the number of calfs born per month.
	 *
	 * @return //
	 */
	private HashMap<Integer, int[]> dataForChartNacimientos(Animal ani) {
		HashMap<Integer, int[]> map = new HashMap<>();

		ArrayList<Livestock> bovinos = crudLive.getLivestockFiltred(0, 0, 0, ani, 0, 0,true);

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
	 * This method returns a hashmap where the key is for the year and the array is
	 * the months of the year and the number of deaths per month.
	 *
	 * @return
	 */
	private HashMap<Integer, int[]> dataForChartMuertes(Animal ani) {
		HashMap<Integer, int[]> map = new HashMap<>();

		ArrayList<Livestock> bovinos = crudLive.getLivestockFiltred(0, 0, 0, ani, 0, 0,true);

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

	public String getReportDeaths() {
		StringBuilder sb = new StringBuilder();
		addHeader(sb);

		// add head text
		sb.append("<h2>Reporte de Muertes</h2> \n");
		sb.append("<hr></hr> \n");

		// add farm info
		addFarmInfo(sb);

		// add content
		// get all the deaths by animal species
		List<Animal> animals = crudAnimal.getAll();
		for (int i = 0; i < animals.size(); i++) {
			addLivestockDeathDetail(sb, animals.get(i));
		}

		// add the footer
		addFooter(sb);

		return sb.toString();
	}

	private void addLivestockDeathDetail(StringBuilder sb, Animal a) {
		sb.append("<h3> Muertes de " + a.getName() + " </h3>");
		sb.append("<hr></hr> \n");

		ArrayList<Livestock> livestock = crudLive.getDeadLivestockOfAnimal(a);
		HashMap<String, Integer> data = dataForChartEdadesySexo(a, livestock);

		// livestock table
		sb.append("<table>\n");

		// header
		sb.append("<tr>");
		sb.append("<th> ID </th>");
		sb.append("<th> Arete </th>");
		sb.append("<th> Genero </th>");
		sb.append("<th> Nombre </th>");
		sb.append("<th> Raza </th>");
		sb.append("<th> Nacimiento </th>");
		sb.append("<th> F. Muerte </th>");
		sb.append("<th> Causa </th>");
		sb.append("</tr>\n");

		Livestock li;
		for (int i = 0; i < livestock.size(); i++) {
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
			} else {
				sb.append("<tr>");
			}
			li = livestock.get(i);
			sb.append("<td> " + li.getId_livestock() + " </td>");
			sb.append("<td> " + li.getEar_ring() + " </td>");
			sb.append("<td> " + li.getSex() + " </td>");
			sb.append("<td> " + li.getName() + " </td>");
			sb.append("<td> " + li.getId_race_1().getName() + " </td>");
			sb.append("<td> " + li.getDate_birth() + " </td>");
			sb.append("<td> " + li.getDate_death() + " </td>");
			sb.append("<td> " + li.getCause_death() + " </td>");

			sb.append("</tr>");
		}

		sb.append("</table>\n");

		// deaths this year
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		HashMap<Integer, int[]> deaths = dataForChartMuertes(a);

		int[] dts = deaths.get(year);

		sb.append("<table>\n");
		sb.append("<tr> <th colspan=\"2\"> Muertes de " + a.getName() + " en " + year + " </th></tr>\n");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Muertes este Año </td>");
		int n = (dts == null) ? 0 : getTotal(dts);
		sb.append("<td>" + n + "</td>");
		sb.append("</tr>\n");
		sb.append("</table>\n");

		// deaths by age group
		sb.append("<table>\n");
		sb.append("<tr> <th colspan=\"4\"> Muertes de " + a.getName() + " por Grupo de Edad </th></tr>\n");

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
		sb.append("<td>" + livestock.size() + "</td>");
		sb.append("</tr>\n");

		sb.append("</table>\n");

	}

	public String getReportIndividual(Livestock live) {
		StringBuilder sb = new StringBuilder();

		// add the header
		addHeader(sb);

		// add title text
		sb.append("<h2> Reporte del Animal \"" + live.getName() + "\"</h2> \n");
		sb.append("<hr></hr> \n");

		// add farm info
		addFarmInfo(sb);

		// get all the animal detail
		sb.append("<table>");

		sb.append("<tr> <th colspan=\"4\"> Detalle del Animal </th> </tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td width='20%'>Id:</td>");
		sb.append("<td width='30%'>").append(live.getId_livestock()).append("</td>");
		String s = (live.getEar_ring() == null) ? "" : live.getEar_ring();
		sb.append("<td width='20%'>Arete:</td>");
		sb.append("<td width='30%'>").append(s).append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		s = (live.getName() == null) ? "" : live.getName();
		sb.append("<td >Nombre:</td>");
		sb.append("<td>").append(s).append("</td>");
		sb.append("<td >Sexo:</td>");
		sb.append("<td>").append(live.getSex()).append("</td>");
		sb.append("</tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td >Color:</td>");
		sb.append("<td>").append(live.getColor()).append("</td>");
		s = live.getWeight() + "";
		sb.append("<td >Peso:</td>");
		sb.append("<td>").append(s).append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td >Especie:</td>");
		sb.append("<td>").append(live.getId_race_1().getAnimal().getName()).append("</td>");
		sb.append("<td >Estado:</td>");
		sb.append("<td>").append(live.getStatus()).append("</td>");
		sb.append("</tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Raza:</td>");
		sb.append("<td>").append(live.getId_race_1().getName()).append("</td>");
		sb.append("<td> Raza 2:</td>");
		s = (live.getId_race_2() == null) ? "" : live.getId_race_2().getName();
		sb.append("<td>").append(s).append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td >Fecha Nacimiento:</td>");
		sb.append("<td>").append(live.getDate_birth().toString()).append("</td>");
		sb.append("<td >Edad:</td>");
		sb.append("<td>").append(live.getAge()).append("</td>");
		sb.append("</tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td >Fecha Muerte:</td>");
		s = (live.getDate_death() == null) ? "" : live.getDate_death().toString();
		sb.append("<td>").append(s).append("</td>");
		sb.append("<td >Causa:</td>");
		s = (live.getCause_death() == null) ? "" : live.getCause_death().toString();
		sb.append("<td>").append(s).append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td >Activo(a):</td>");
		s = (live.isActive()) ? "Si" : "No";
		sb.append("<td>").append(s).append("</td>");
		sb.append("<td >Herrado(a):</td>");
		s = (live.isBranded()) ? "Si" : "No";
		sb.append("<td>").append(s).append("</td>");
		sb.append("</tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td >Nota(s):</td>");
		sb.append("<td colspan=\"3\">").append(live.getDescription()).append("</td>");
		sb.append("</tr>");

		sb.append("</table>");

		// pedigree table
		sb.append("<table>");
		sb.append("<tr> <th colspan=\"2\"> Pedigrí </th> </tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td> Madre:</td>");
		sb.append("<td>").append((live.getId_mother() == null) ? "Desconocida" : live.getId_mother().toString())
				.append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td> Padre:</td>");
		sb.append("<td>").append((live.getId_father() == null) ? "Desconocido" : live.getId_father().toString())
				.append("</td>");
		sb.append("</tr>");

		sb.append("</table>");

		// offspring table ******************************************
		ArrayList<Livestock> crias;
		if (live.getGender() == 0) {
			// male
			crias = crudLive.getFathersOffspring(live);
		} else {
			// female
			crias = crudLive.getMothersOffspring(live);
		}
		sb.append("<h3> Crias de Este Animal</h3> \n");
		sb.append("<hr></hr> \n");
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
		sb.append("<th> Fecha Nacimiento </th>");
		sb.append("</tr>\n");

		Livestock li;
		for (int i = 0; i < crias.size(); i++) {
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
			} else {
				sb.append("<tr>");
			}
			li = crias.get(i);
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

		// events table

		// add the footer
		addFooter(sb);

		return sb.toString();
	}

	public String getReportEvent(Event e) {
		StringBuilder sb = new StringBuilder();

		// add the header
		addHeader(sb);

		Calendar c = Calendar.getInstance();
		c.setTime(e.getDate_event());
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, ResourceManager.localizer.getCurrentLocale());
		String fecha = df.format(c.getTime());

		// add title text
		sb.append("<h2> Reporte del Evento \"" + e.getEventType().getName() + " en " + fecha + "\"</h2> \n");
		sb.append("<hr></hr> \n");

		// add farm info
		addFarmInfo(sb);

		// get all the event detail
		sb.append("<table>");

		sb.append("<tr> <th colspan=\"4\"> Detalle del Evento </th> </tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td width='20%'>Id:</td>");
		sb.append("<td width='30%'>").append(e.getId_event()).append("</td>");
		sb.append("<td width='20%'>Folio:</td>");
		sb.append("<td width='30%'>").append("0000").append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td >Tipo de Evento:</td>");
		sb.append("<td>").append(e.getEventType().getName()).append("</td>");
		sb.append("<td >Fecha del Evento:</td>");
		sb.append("<td>").append(fecha).append("</td>");
		sb.append("</tr>");

		sb.append("<tr class=\"alt\">");
		sb.append("<td >Interesado:</td>");
		sb.append("<td>").append(e.getStakeholder()).append("</td>");
		sb.append("<td >Contacto:</td>");
		sb.append("<td>").append(e.getStakeholder_contact()).append("</td>");
		sb.append("</tr>");

		sb.append("<tr>");
		sb.append("<td >Total:</td>");
		sb.append("<td>").append(e.getTotal_amount()).append("</td>");
		sb.append("<td >Comentario:</td>");
		sb.append("<td>").append(e.getComments()).append("</td>");
		sb.append("</tr>");

		sb.append("</table>");

		// table of live stock involved******************************************
		ArrayList<LivestockEvent> animales = crudLiveEvent.getLivestockAtEvent(e);

		sb.append("<h3> Animales en el Evento</h3> \n");
		sb.append("<hr></hr> \n");
		// livestock table
		sb.append("<table>\n");

		// header
		sb.append("<tr>");
		sb.append("<th width='50%'> Ganado </th>");
		sb.append("<th width='10%'> Precio </th>");
		sb.append("<th width='40%'> Comentario </th>");
		sb.append("</tr>\n");

		for (int i = 0; i < animales.size(); i++) {
			if ((i % 2) == 0) {
				sb.append("<tr class=\"alt\">");
			} else {
				sb.append("<tr>");
			}
			sb.append("<td> " + animales.get(i).getLivestock().toString() + " </td>");
			sb.append("<td> " + animales.get(i).getPrice() + " </td>");
			sb.append("<td> " + animales.get(i).getComment() + " </td>");
			sb.append("</tr>");
		}

		sb.append("</table>\n");

		// add the footer
		addFooter(sb);

		return sb.toString();
	}
}
