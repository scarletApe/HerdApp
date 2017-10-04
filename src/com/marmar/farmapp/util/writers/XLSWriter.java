//<editor-fold defaultstate="collapsed" desc="licence">
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//</editor-fold>
package com.marmar.farmapp.util.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author juanmartinez
 */
public class XLSWriter {

	public void writeXLS(ArrayList<Writable> list, String directorio, String nombre) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(nombre);

		// write the column headers
		Row row = sheet.createRow(0);
		Writable writ = list.get(0);
		// los nombres de las columnas
		Object[] objArr = writ.getNames();
		int cellnum = 0;
		for (Object obj : objArr) {
			Cell cell = row.createCell(cellnum++);
			// cell.setCellStyle(CellStyle.ALIGN_FILL);
			if (obj instanceof String) {
				cell.setCellValue((String) obj);
			}
		}

		//write the content of the list
		for (int i = 0; i < list.size(); i++) {

			row = sheet.createRow(i + 1);
			writ = list.get(i);
			objArr = writ.getAsArray();
			cellnum = 0;

			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				// cell.setCellStyle(CellStyle.ALIGN_FILL);
				if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				} else if (obj instanceof Integer) {
					cell.setCellValue((Integer) obj);
				}
			}
		}

		try {
			FileOutputStream out = new FileOutputStream(new File(directorio));
			workbook.write(out);
			out.close();
			workbook.close();
			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			System.out.println("Error " + e);
		} catch (IOException e) {
			System.out.println("Error " + e);
		}
	}
}
