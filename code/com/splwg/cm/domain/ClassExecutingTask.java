package com.splwg.cm.domain;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ClassExecutingTask {

	long delay = 10 * 1000; // delay in milliseconds
    LoopTask task = new LoopTask();
    Timer timer = new Timer("TaskName");

    public void start() {
        timer.cancel();
        timer = new Timer("TaskName");
        Date executionDate = new Date(); // no params = now
        timer.scheduleAtFixedRate(task, executionDate, delay);
    }

    private class LoopTask extends TimerTask {
        public void run() {
            System.out.println("This message will print every 10 seconds.");
            LoopTask l = new LoopTask();
            try {
				l.displayTray();
			} catch (MalformedURLException | AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        
        public void displayTray() throws AWTException, MalformedURLException {
    		// Obtain only one instance of the SystemTray object
    		SystemTray tray = SystemTray.getSystemTray();

    		// If the icon is a file
    		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
    		// Alternative (if the icon is on the classpath):
    		// Image image =
    		// Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

    		TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
    		// Let the system resize the image if needed
    		trayIcon.setImageAutoSize(true);
    		// Set tooltip text for the tray icon
    		trayIcon.setToolTip("System tray icon demo");
    		tray.add(trayIcon);

    		trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.INFO);
    	}
    }

    public static void main(String[] args) {
        ClassExecutingTask executingTask = new ClassExecutingTask();
        executingTask.start();
    }

    
}
