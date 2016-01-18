/****************************************************************************
  *  CreatorsTab.java
  *  CS 230 Final Project
  *
  *  author: Rachel Seo
  * 
  *  The last tab of WellesleyMapGUI that contains information on the
  *  creators of the program
  * 
  *****************************************************************************/

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class CreatorsTab extends JPanel {
  
  // Instance variables
  private JLabel empty1, heading, empty2, label1, label2, label3;
  
  // Constructor
  public CreatorsTab() {
    
    setLayout(new GridLayout(3,3)); // set it as Border Layout
    
    setBackground(new Color(188, 116, 184)); // set background color
    
    empty1 = new JLabel("");
    add(empty1);
    
    heading = new JLabel("<html>CREATORS</html>");
    heading.setFont(new Font("Georgia", Font.PLAIN, 25));
    heading.setHorizontalAlignment(JLabel.CENTER);
    add(heading);
    
    empty2 = new JLabel("");
    add(empty2);
    
    
    // add all three images
    try {
      BufferedImage rachel = ImageIO.read(new File("rachel.png"));
      JLabel picLabel1 = new JLabel(new ImageIcon(rachel));
      add(picLabel1);
      
      BufferedImage sheree = ImageIO.read(new File("sheree.png"));
      JLabel picLabel2 = new JLabel(new ImageIcon(sheree));
      add(picLabel2);
 
      BufferedImage michelle = ImageIO.read(new File("michelle.png"));
      JLabel picLabel3 = new JLabel(new ImageIcon(michelle));
      add(picLabel3);
      
    } catch (IOException ex) {
      System.out.println(ex);
    }
    
     //add a label below each photo
    label1 = new JLabel("<html>Rachel Seo</html>");
    label1.setFont(new Font("Georgia", Font.PLAIN, 20));
    label1.setHorizontalAlignment(JLabel.CENTER); // make center aligned
    add(label1); // add to panel
      
    label2 = new JLabel("<html>Sheree Liu</html>");
    label2.setFont(new Font("Georgia", Font.PLAIN, 20));
    label2.setHorizontalAlignment(JLabel.CENTER);
    add(label2); // add to panel
      
    label3 = new JLabel("<html>Michelle Lu</html>");
    label3.setFont(new Font("Georgia", Font.PLAIN, 20));
    label3.setHorizontalAlignment(JLabel.CENTER);
    add(label3); // add to panel
    
  }
}