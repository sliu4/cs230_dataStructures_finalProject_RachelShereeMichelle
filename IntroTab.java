/****************************************************************************
  *  IntroTab.java
  *  CS 230 Final Project
  *
  *  author: Rachel Seo
  * 
  *  This tab appears when the program starts, and it contains instructions
  *  on how to use the Wellesley Map program
  * 
  *****************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class IntroTab extends JPanel {
  
  // Instance variable
  private JLabel label;
  
  // Constructor
  public IntroTab() {
    
    setLayout(new BorderLayout()); // set it as border layout
    // add text to label, using html code to break it up to different lines
    label = new JLabel("<html>Hello! This GUI was created by Rachel Seo, Sheree Liu and Michelle Lu.<br>Select the Find Path tab to choose an origin point and a destination point. <br>The program will then tell you the shortest path you can take!</html>");
    label.setFont(new Font("Georgia", Font.PLAIN, 20));
    label.setHorizontalAlignment(JLabel.CENTER); // make it center aligned
    add (label, BorderLayout.CENTER); // add to panel
    setBackground(new Color(153, 234, 255)); // set background color
  
  }

  
}