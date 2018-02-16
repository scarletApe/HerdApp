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

	public void writeReportFromWriteList(ArrayList<Writable> list, 
			String nombre, File file, boolean landscape) {
		String content = mkr.getReportFromListOfWritables(list, nombre);
		System.out.println("file =" + file.toString());
		System.out.print(content);
		writer.writePDF(content, file, landscape);
	}
}