package com.splwg.cm.domain;
import java.io.IOException;
import java.nio.file.*;

public class FileCopyExample {

    public static void main(String[] args) {
        // Provide the source and destination file paths
        String sourceFilePath = "C://spl//DEV5100//f//x.txt";
        String destinationFilePath = "C://spl//DEV5100//f//";

        // Copy the file
        try {
        	for(int i=0;i<100000;i++)
        	{
            copyFile(sourceFilePath, destinationFilePath+i+".txt");
        	}
            System.out.println("File copied successfully.");
        } catch (IOException e) {
            System.err.println("Error copying the file: " + e.getMessage());
        }
    }

    private static void copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
        // Using java.nio.file.Files.copy method to copy the file
        Path sourcePath = Paths.get(sourceFilePath);
        Path destinationPath = Paths.get(destinationFilePath);

        // StandardCopyOption.REPLACE_EXISTING is used to overwrite the destination file if it exists
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
