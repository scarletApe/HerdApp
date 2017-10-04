/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marmar.farmapp.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author juanmartinez
 */
public class ImageManager {

	public static byte[] readBytesFromFile(String filePath) throws IOException {
		File inputFile = new File(filePath);
		FileInputStream inputStream = new FileInputStream(inputFile);

		byte[] fileBytes = new byte[(int) inputFile.length()];
		inputStream.read(fileBytes);
		inputStream.close();

		return fileBytes;
	}

	public static void saveBytesToFile(String filePath, byte[] fileBytes) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filePath);
		outputStream.write(fileBytes);
		outputStream.close();
	}

	public static Image byteArraytoFXImage(byte[] array) throws IOException {
		InputStream in = new ByteArrayInputStream(array);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		WritableImage image = SwingFXUtils.toFXImage(bImageFromConvert, null);
		return image;
	}

	public static byte[] fXImagetoByteArray(Image img) throws IOException {
		BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);
		ByteArrayOutputStream s = new ByteArrayOutputStream();
		ImageIO.write(bImage, ".png", s);
		byte[] res = s.toByteArray();
		return res;
	}
}
