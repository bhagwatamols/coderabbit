package com.splwg.cm.domain;

public class CreateZipFile1 {

	public static void main(String args[])
	{
		int BUFFER = 1024;
        
		java.util.zip.ZipOutputStream zos = null;
		java.io.BufferedInputStream bis = null;

		String srcFile =  "C:\\spl\\DEV5100\\Demo.txt" ;
		String zipFileName = "C:\\spl\\DEV5100\\outputsss.zip";

		try {
			java.io.FileOutputStream fos = new java.io.FileOutputStream(zipFileName);
			java.util.zip.ZipOutputStream zipOut = new java.util.zip.ZipOutputStream(fos);

			
				java.io.File fileToZip = new java.io.File(srcFile);
				java.io.FileInputStream fis = new java.io.FileInputStream(fileToZip);
				java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}

				fis.close();
			

			zipOut.close();
			fos.close();
			System.out.println("Successfully created ZIP file: " + zipFileName);

		} catch (Exception ioExp)
		{
			// System.out.println("Error while zipping " + ioExp.getMessage());
		}
	
	}

}
