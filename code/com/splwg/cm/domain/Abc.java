package com.splwg.cm.domain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Abc {

	public static void main(String[] args) {

		org.w3c.dom.Element parms =null;
		
		Integer.parseInt("6L");
		
		// Source and destination directories
		String sourceDirectory = "D://a1//";
		String destinationDirectory = "D://a2//";

		// File name to be copied
		String fileName = "abx.txt";

		// Create source and destination paths
		Path sourcePath = Paths.get(sourceDirectory, fileName);
		Path destinationPath = Paths.get(destinationDirectory, fileName);

		try {
			// Copy the file
			Files.copy(sourcePath, destinationPath);

			System.out.println("File copied successfully from " + sourcePath + " to " + destinationPath);
		} catch (IOException e) {
			System.err.println("Error copying file: " + e.getMessage());
		}
	}
}
