package com.marmar.farmapp.util.writers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringReader;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HTMLWriter {

	public boolean writePDF(String content, File file, boolean lanscape) {
		try {
			// step 1
			Document document = new Document();
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
			// step 3
			if (lanscape) {
				document.setPageSize(new Rectangle(842, 595));
			}
			document.open();
			document.addAuthor("JMMC");
			document.addCreator("Marmar Limited");
			document.addSubject("Thanks for your support");
			document.addCreationDate();

			// step 4
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(content));
			// step 5
			document.close();
			System.out.println("PDF Created!");

		} catch (Exception e) {
			System.out.println("Error in writting file: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean writeHTML(String content, File file) {
		// escribirlo a un archivo
		FileWriter fichero = null;
		PrintWriter pw;
		try {
			fichero = new FileWriter(file, false);
			pw = new PrintWriter(fichero);

			pw.print(content);
		} catch (Exception e) {
			System.out.println("Error escribiendo archivo: " + e.getMessage());
			return false;
		} finally {
			try {
				if (null != fichero) {
					fichero.close();
				}
			} catch (Exception e2) {
				// e2.printStackTrace();
			}
		}
		return true;
	}

}
