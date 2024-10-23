
package com.splwg.cm.domain;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.splwg.shared.environ.ApplicationProperties;

public class dOWNLOADfILE {
	
	

    public static void main(String[] args) {
    	System.out.println(ApplicationProperties.getProperty("spl.runtime.environ.SPLEBASE"));
    	
        String url = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        
        try {
            downloadUsingNIO(url,  ApplicationProperties.getProperty("spl.runtime.environ.SPLEBASE")+"\\ORMB_APP_SHORTCUTS.pdf");
            
            downloadUsingStream(url, ApplicationProperties.getProperty("spl.runtime.environ.SPLEBASE")+"\\ORMB_APP_SHORTCUTS.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
