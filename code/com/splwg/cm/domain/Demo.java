package com.splwg.cm.domain;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.splwg.shared.common.Base64Encoder;

public class Demo {
	
	public static void main(String[] args) throws IOException {
		
		
		 File d = new File("D://amolres.docx");
		 InputStream targetStream = new FileInputStream(d);
		 byte b[] =getAttachmentData(targetStream);
		 
		 
		 System.out.println(toString(b));
//		   for(int i=0;i<b.length;i++)
//		   {
//		 System.out.println(b[i]);
//		   }
		
	}

	private static String toString(byte[] attachmentDataBytes) { return Base64Encoder.encode(attachmentDataBytes); }
	
	private static byte[] getAttachmentData(InputStream attachmentInputStream) throws IOException {
		
		long attachmentMaxByteSizeToAllow = 10000L;
		/* 325 */     int totalBytesRead = 0;
		/* 326 */     byte[] buffer = new byte[10240];
		/* 327 */     int bytesRead = -1;
		/* 328 */     ByteArrayOutputStream attachmentContents = new ByteArrayOutputStream();
		/* 329 */     BufferedInputStream inputFileStream = new BufferedInputStream(attachmentInputStream);
		/*     */     
		/* 331 */     while ((bytesRead = inputFileStream.read(buffer)) > 0) {
		/* 332 */       if (-1L != attachmentMaxByteSizeToAllow) {
		/* 333 */         totalBytesRead += bytesRead;
		/*     */         
		/* 335 */         if (totalBytesRead > attachmentMaxByteSizeToAllow) {
		/* 336 */           //logger.error("Attachment exceeds the max size configured " + attachmentMaxByteSizeToAllow);
		/* 337 */           //throw new AttachmentPayloadTooLargeException(null);
		/*     */         } 
		/*     */       } 
		/*     */       
		/* 341 */       attachmentContents.write(buffer, 0, bytesRead);
		/*     */     } 
		/* 343 */     return attachmentContents.toByteArray();
		
	
	
	}
	
}
