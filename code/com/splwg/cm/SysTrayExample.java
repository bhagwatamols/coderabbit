package com.splwg.cm;



import javax.swing.*;

import java.awt.*;

import java.awt.event.*;
public class SysTrayExample extends JFrame {



 // Class level variable to support our tray icon

 private TrayIcon trayIcon;

  

 // Main.. which creates an instance of our class, sets its caption, size, and shows it.

 public static void main(String args[]) {

     SysTrayExample f = new SysTrayExample();

     f.setTitle("System Tray Example");

     f.setSize(300,300);

     f.setDefaultCloseOperation(EXIT_ON_CLOSE);

     f.setVisible(true);

 }

  

 // Constructor. When we create the class in main, this function is called first thing.

 public SysTrayExample() {

     // Check to make sure the system does have a system tray we can access

     if (SystemTray.isSupported()) {



         // Fabulous, it supports a system tray. Set a variable to it so we can manipulate the tray using the variable of type

         // SystemTray. We will set the TrayIcon up and then add it to the tray using this variable.

          

         SystemTray tray = SystemTray.getSystemTray();

          

         // Load an image from our c:\ drive and we are going to use this in the trayIcon

         Image image = Toolkit.getDefaultToolkit().getImage("c:\\xscope.gif");



          

         // Here are our listeners. One listens for the popup menu "Close" command and terminates the program.

         // This will be attached to the popup menu item "close"

         ActionListener exitListener = new ActionListener() {

             public void actionPerformed(ActionEvent e) {

                 System.exit(0);

             }

         };

          

         // This message appears when an event has been detected.

         ActionListener actionListener = new ActionListener() {

             public void actionPerformed(ActionEvent e) {

                 trayIcon.displayMessage("Event Performed", "An Action Event Has Been Performed!",

                     TrayIcon.MessageType.INFO);

             }

         };

                  

         // Next we setup our popup menu. We then add one item to the menu called "close" to close our program.

         PopupMenu popup = new PopupMenu();

         MenuItem defaultItem = new MenuItem("Close");

          

         // Add our ActionListener called "exitlistener" to the popup menu item.

         defaultItem.addActionListener(exitListener);

          

         // Add the menu item finally to the popup menu.

         popup.add(defaultItem);



         // Now configure our tray icon and attach our actionListener to it to listen for actionPeformed events.

         trayIcon = new TrayIcon(image, "Here is our tray tool tip", popup);   

         trayIcon.setImageAutoSize(true);

         trayIcon.addActionListener(actionListener);



         // Lastly, add the icon to the tray variable we defined above. 

         // Since this can throw an error, lets trap the error and display a message if the add failed.

         try {

             tray.add(trayIcon);

         } catch (AWTException e) {

             System.err.println("Problem with adding icon to tray.");

         }



     } else {

         // We reach this error if the system simply won't let us access the tray or doesn't have one.

         System.err.println("This system does not support a System Tray being accessed by Java");

     }

 }

}
