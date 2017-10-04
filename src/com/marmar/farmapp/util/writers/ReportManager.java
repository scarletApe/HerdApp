/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marmar.farmapp.util.writers;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author manuelmartinez
 */
public class ReportManager {

	private HTMLContentMaker mkr = new HTMLContentMaker();
	private HTMLWriter writer = new HTMLWriter();

	public void writeInitialReport(File file) {
		String content = mkr.getInitialReport();
		System.out.println("file =" + file.toString());
		System.out.print(content);
		writer.writePDF(content, file, false);
	}

	public void writeReportFromWriteList(ArrayList<Writable> list, 
			String nombre, File file, boolean landscape) {
		String content = mkr.getReportFromListOfWritables(list, nombre);
		System.out.println("file =" + file.toString());
		System.out.print(content);
		writer.writePDF(content, file, landscape);
	}

	//
	// public String getReporteBovino(Bovino b) {
	// StringBuilder sb = new StringBuilder();
	// addHeader(sb);
	//
	// sb.append("<h>Detalle del Bovino</h> <hr>");
	//
	// //add content style="background-color:#4CAF50;color:white;"
	// // id="lbb"
	// sb.append("<table class=\"tabler\" border=1 px>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"lbb\" width='20%'>Id:</th>");
	// sb.append("<td width='30%'>").append(b.getId_bovino()).append("</td>");
	// String s = (b.getNum_arete() == null) ? "" : b.getNum_arete();
	// sb.append("<th id=\"lbb\" width='20%'>Arete:</th>");
	// sb.append("<td width='30%'>").append(s).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"lbb\">Sexo:</th>");
	// sb.append("<td>").append(b.getSexo()).append("</td>");
	// sb.append("<th id=\"lbb\">Color:</th>");
	// sb.append("<td>").append(b.getPelaje()).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// s = (b.getApodo() == null) ? "" : b.getApodo();
	// sb.append("<th id=\"lbb\">Apodo:</th>");
	// sb.append("<td>").append(s).append("</td>");
	// s = (b.getMelona() == null) ? "" : b.getMelona();
	// sb.append("<th id=\"lbb\">Melona:</th>");
	// sb.append("<td>").append(s).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"lbb\">Raza:</th>");
	// sb.append("<td>").append(b.getRaza().getNombre()).append("</td>");
	// sb.append("<th id=\"lbb\">Comentario:</th>");
	// s = (b.getComentario() == null) ? "" : b.getComentario();
	// sb.append("<td>").append(s).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"lbb\">Fecha Nacimiento:</th>");
	// sb.append("<td>").append(b.getFecha_nacimiento().toString()).append("</td>");
	// sb.append("<th id=\"lbb\">Fecha Muerte:</th>");
	// s = (b.getFecha_muerte() == null) ? "" : b.getFecha_muerte().toString();
	// sb.append("<td>").append(s).append("</td>");
	//
	// sb.append("</tr>");
	//
	// if (b.getVenta() != null) {
	// addDetalleVenta(sb, b.getVenta());
	// }
	//
	// sb.append("</table>");
	//
	// if (b.getVacunas() != null && !b.getVacunas().isEmpty()) {
	// addDetalleVacunaciones(sb, b.getVacunas());
	// }
	//
	// addFooter(sb);
	//
	// return sb.toString();
	// }
	//
	// private void addDetalleVenta(StringBuilder sb, Venta v) {
	// sb.append("<tr>");
	// sb.append("<th id=\"lgb\">Id Venta:</th>");
	// sb.append("<td>").append(v.getId_venta()).append("</td>");
	// sb.append("<th id=\"lgb\">Comprador:</th>");
	// sb.append("<td>").append(v.getComprador()).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"lgb\">Fecha de Venta:</th>");
	// sb.append("<td>").append(v.getFecha_venta().toString()).append("</td>");
	// sb.append("<th id=\"lgb\">Monto:</thth>");
	// sb.append("<td>").append(v.getMonto()).append("</td>");
	// sb.append("</tr>");
	// }
	//
	// private void addDetalleVacunaciones(StringBuilder sb,
	// Iterable<Vacunacion> vacunas) {
	// sb.append("<h>Detalle de las Vacunas del Bovino</h> <hr>");
	//
	// sb.append("<table class=\"tabler\" border=1 px>");
	//
	//// sb.append("<tr id=\"lgb\">");
	//// sb.append("<th colspan=\"5\">Vacunas</th>");
	//// sb.append("</tr>");
	// sb.append("<tr id=\"lbb\">");
	// sb.append("<th>Id</th>");
	// sb.append("<th>Veterinario</th>");
	// sb.append("<th>Vacuna</th>");
	// sb.append("<th>Fecha</th>");
	// sb.append("<th>Comentario</th>");
	// sb.append("</tr>");
	//
	// for (Vacunacion v : vacunas) {
	// sb.append("<tr>");
	// sb.append("<td>").append(v.getId_vacunacion()).append("</td>");
	// sb.append("<td>").append(v.getMedico()).append("</td>");
	// sb.append("<td>").append(v.getTipo()).append("</td>");
	// sb.append("<td>").append(v.getFecha_vacunacion().toString()).append("</td>");
	// sb.append("<td>").append(v.getFormula()).append("</td>");
	// sb.append("</tr>");
	// }
	// sb.append("</table>");
	// }
	//
	// private void addListBovinos(StringBuilder sb, ArrayList<Bovino> bovs) {
	//
	// sb.append("<table class=\"tabler\" border=1 px>");
	//
	// sb.append("<tr id=\"lbb\">");
	// sb.append("<th>Id</th>");
	// sb.append("<th>Arete</th>");
	// sb.append("<th>Sexo</th>");
	// sb.append("<th>Color</th>");
	// sb.append("<th>Melona</th>");
	//
	// sb.append("<th>Apodo</th>");
	// sb.append("<th>Fecha Nac.</th>");
	// sb.append("<th>Edad</th>");
	// sb.append("<th>Raza</th>");
	// sb.append("</tr>");
	//
	// if (bovs == null) {
	// System.out.println("bovinos es null");
	// return;
	// }
	// for (Bovino b : bovs) {
	// sb.append("<tr>");
	// sb.append("<td>").append(b.getId_bovino()).append("</td>");
	// sb.append("<td>").append((b.getNum_arete() == null) ? "" :
	// b.getNum_arete()).append("</td>");
	// sb.append("<td>").append(b.getSexo()).append("</td>");
	// sb.append("<td>").append(b.getPelaje()).append("</td>");
	// sb.append("<td>").append((b.getMelona() == null) ? "" :
	// b.getMelona()).append("</td>");
	//
	// sb.append("<td>").append((b.getApodo() == null) ? "" :
	// b.getApodo()).append("</td>");
	// sb.append("<td>").append(b.getFecha_nacimiento().toString()).append("</td>");
	// sb.append("<td>").append(b.getEdad()).append("</td>");
	// sb.append("<td>").append(b.getRaza().getNombre()).append("</td>");
	// sb.append("</tr>");
	// }
	//
	// sb.append("</table>");
	// }
	//
	// public String getReportVenta(Venta v) {
	// StringBuilder sb = new StringBuilder();
	// addHeader(sb);
	//
	// sb.append("<h>Detalle de la Venta</h> <hr>");
	//
	// sb.append("<table class=\"tabler\" border=1 px>");
	// addDetalleVenta(sb, v);
	// sb.append("</table>");
	//
	// sb.append("<h>Bovinos de la Venta</h> <hr>");
	// ArrayList<Bovino> bovs = new ConectorVenta().getBovinosdeVenta(v);
	// addListBovinos(sb, bovs);
	//
	// addFooter(sb);
	// return sb.toString();
	// }
	//
	// public String getReportVacunacion(Vacunacion v) {
	// StringBuilder sb = new StringBuilder();
	// addHeader(sb);
	//
	// sb.append("<h>Detalle de la Vacunación</h> <hr>");
	//
	// sb.append("<table class=\"tabler\" border=1 px>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"grey\">Id</th>");
	// sb.append("<td>").append(v.getId_vacunacion()).append("</td>");
	// sb.append("<th id=\"grey\">Veterinario</th>");
	// sb.append("<td>").append(v.getMedico()).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// sb.append("<th id=\"grey\">Vacuna</th>");
	// sb.append("<td>").append(v.getTipo()).append("</td>");
	// sb.append("<th id=\"grey\">Fecha</th>");
	// sb.append("<td>").append(v.getFecha_vacunacion().toString()).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("<tr>");
	// sb.append("<th colspan=\"1\" id=\"grey\">Comentario</th>");
	// sb.append("<td colspan=\"3\">").append(v.getFormula()).append("</td>");
	// sb.append("</tr>");
	//
	// sb.append("</table>");
	//
	// sb.append("<h>Bovinos de la Vacunación</h> <hr>");
	// ArrayList<Bovino> bovs = new
	// ConectorVacunacion().getBovinosdeVacunacion(v);
	// addListBovinos(sb, bovs);
	//
	// addFooter(sb);
	// return sb.toString();
	// }
}

// sb.append(".flat-table { margin-bottom: 20px;
// border-collapse:collapse;font-family: 'Lato', Calibri, Arial, sans-serif;");
// sb.append("border: none;border-radius: 3px;-webkit-border-radius:
// 3px;-moz-border-radius: 3px; width=100%}");
// sb.append(".flat-table th, .flat-table td {box-shadow: inset 0 -1 px
// rgba(0,0,0,0.25), inset 0 1 px rgba(0,0,0,0.25);}");
// sb.append(".flat-table th {font-weight: normal;-webkit-font-smoothing:
// antialiased;");
// sb.append("padding: 1em;color: rgba(0,0,0,0.45);text-shadow: 0 0 1 px
// rgba(0,0,0,0.1);font-size: 1.2em;}");
// sb.append(".flat-table td {color: #f7f7f7;padding: 0.5em 0.8em 0.5em
// 1.0em;text-shadow: 0 0 1 px rgba(255,255,255,0.1);font-size: 1.2em;}");
// sb.append(".flat-table tr { -webkit-transition: background 0.3s, box-shadow
// 0.3s;");
// sb.append("-moz-transition: background 0.3s, box-shadow 0.3s; transition:
// background 0.3s, box-shadow 0.3s;}");
// sb.append(".flat-table-1 {background: #336ca6;}");
// sb.append(".flat-table-1 tr:hover {background: rgba(0,0,0,0.19);}");
// sb.append(".flat-table-2 tr:hover {background: rgba(0,0,0,0.1);}");
// sb.append(".flat-table-2 { background: #f06060;}");
// sb.append(".flat-table-3 { background: #52be7f;}");
// sb.append(".flat-table-3 tr:hover { background: rgba(0,0,0,0.1);}");
