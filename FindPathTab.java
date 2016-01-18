/****************************************************************************
  *  FindPathTab.java
  *  CS 230 Final Project
  *
  *  author: Rachel Seo
  * 
  *  The tab that the user interacts with. It displays the map of Wellesley,
  *  two drop-down menus, two buttons, and an instruction label at the bottom.
  *  When the user selects the origin point and the destination point and clicks 
  *  on the "Find the shortest path!" button, the panel at the bottom displays the
  *  direction as well as the distance between the two buildings. The user must
  *  click on the "Clear results!" button before selecting another point.
  * 
  *****************************************************************************/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class FindPathTab extends JPanel {
  
  // Instance variables
  private JPanel top; // where the map of wellesley will be added
  private JPanel middle; // where the user will be able to select the origin and destination
  private JPanel bottom; // where the direction will appear
  
  private JLabel direction, origin, destination;
  private JComboBox originMenu, destinationMenu; // drop-down menus
  private JButton submitButton, clearButton;
  
  private String buildings[]; // for keeping track of building names
  private WellesleyMap wMap;
  
  // Constructor
  public FindPathTab() {
    
    buildings = new String[]{"Academic Quad","Clapp Library","Lulu Campus Center","Quint","Science Center","Tower Court"};
    wMap = new WellesleyMap();
    
    // set FindPathTab as border layout
    BorderLayout bL = new BorderLayout();
    setLayout(bL);
    // set a vertical gap
    bL.setVgap(10);
    
    // create an array of Strings that will be added to the option of drop-down menu
    String[] options = {"...", "Academic Quad","Clapp Library","Lulu Campus Center","Quint","Science Center","Tower Court"};
    
    // initialize top, middle, bottom panels and set the background with appropriatre colors
    top = new JPanel();
    top.setBackground(new Color(207, 231, 154));
    
    middle = new JPanel();
    middle.setBackground(new Color(153, 167, 127));
    
    bottom = new JPanel();
    bottom.setBackground(new Color(207, 231, 154));
    
    // add map of wellesley
    try {
      BufferedImage myPic = ImageIO.read(new File("wellesley_campus.png"));
      JLabel picLabel = new JLabel(new ImageIcon(myPic));
      top.add(picLabel);
    } catch (IOException ex) {
      System.out.println(ex);
    }
    
    // add two drop-down menus
    origin = new JLabel("Enter origin: ");
    originMenu = new JComboBox(options);
    middle.add(origin);
    middle.add(originMenu);
    
    destination = new JLabel("Enter destination: ");
    destinationMenu = new JComboBox(options);
    middle.add(destination);
    middle.add(destinationMenu);
    
    // add two buttons and add an action listener to each button
    submitButton = new JButton("Find the shortest path!");
    middle.add(submitButton);
    ButtonListener listener = new ButtonListener();
    submitButton.addActionListener(listener);
    
    clearButton = new JButton("Clear results!");
    middle.add(clearButton);
    clearButton.addActionListener(listener);
    
    // set initial direction
    direction = new JLabel("Please choose the origin and destination from the drop-down menu");
    bottom.add(direction);
    
    // add each of the top, middle, bottom panel to the appropriate location on the BorderLayout
    add(top, BorderLayout.NORTH);
    add(middle, BorderLayout.CENTER);
    add(bottom, BorderLayout.SOUTH);
  }
  
  
  
  // Adds an action to the submitButton so that the action will be performed when the button is pressed
  private class ButtonListener implements ActionListener {
    
    int distance;
    String path;
    
    public void actionPerformed (ActionEvent event) {
      
      if (event.getSource() == submitButton) { // if the button clicked is the "Find the shortest path!" button
        
        try {
          // get what index the user selected in each of the drop-down menus
          int picked_origin = originMenu.getSelectedIndex();
          int picked_destin = destinationMenu.getSelectedIndex();
          
          // access buildings array with the selected index to get the actual name of the array
          String origin = buildings[picked_origin-1];
          String destin = buildings[picked_destin-1];
          
          // invoke getShortestPath method and assign the int value to variable "distance"
          distance = wMap.getShortestPath(origin, destin);
          // invoke getBuildingPath method and assign the String value to variable "path"
          path = wMap.getBuildingPath();
          path = path.substring(0,path.length()-3); // to exlude the arrow at the end of the String
          
          // if the user has chosen both the origin and destination
          if ((picked_origin != 0) && (picked_destin != 0)) {
            direction.setText(path + "\t: " + distance + " ft.");
          } else { // if the user has not picked both origin and destination, print out appropriate statement
            direction.setText("Please enter values for ALL fields.");
          }
        } catch (NullPointerException ex) { // if the user has picked the same building for origin and destination
          direction.setText("Please put in an origin and a destination that aren't the same.");
        }
      } else { // if the button clicked is the "Clear results!" button
        direction.setText("   "); // set instruction label as empty
      }
    }
  }
}


