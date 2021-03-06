/****************************************************************************
  *  WellesleyMapGUI.java
  *  CS 230 Final Project
  *
  *  author: Rachel Seo
  * 
  *  Creates and displays the main program frame and holds all components 
  *  (all the tabs) together
  * 
  *****************************************************************************/

import javax.swing.*;
import java.awt.*;

public class WellesleyMapGUI {
  
   // Creates and displays the main program frame
  public static void main(String[] args) {

    JFrame frame = new JFrame("Map of Wellesley"); // title of window
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // create a new JTabbedPane and add all three tabs
    JTabbedPane tp = new JTabbedPane();
    tp.addTab("About", new IntroTab());
    tp.addTab("Find Your Way", new FindPathTab());
    tp.addTab("Creators", new CreatorsTab());

    frame.getContentPane().setPreferredSize(new Dimension(950,600)); // set size of content frame
    frame.getContentPane().add(tp); // add tabs to content pane
    frame.pack();
    frame.setVisible(true); 
    
  }
}