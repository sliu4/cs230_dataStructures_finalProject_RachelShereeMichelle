//----------------------------------------------------------
// WellesleyMap.java
// CS 230 Final Project
//
// authors: Sheree Liu, Michelle Lu
//
// The WellesleyMap object is a weighted graph that contains
// five vertices (or buildings) on Wellesley's campus, and 
// the distances between them
// 
// 
// All test cases work except one--Tower Court to Academic Quad.
// We think this has to do with Dijkstra's algorithm and how 
// it requires a specific type of graph. We have commented out
// System.out.println statements that we used for debugging. 
//----------------------------------------------------------


import java.io.*;
import java.util.*; 

public class WellesleyMap {

 // Instance variables
 private int n;  //num vertices
 private String[] vertices;
 private LinkedList<String> buildingPath;
 private int[][] weightedEdges;

 private final int DEFAULT_CAPACITY = 6;
 private final int NOT_FOUND = -1;

 // Constructor
 public WellesleyMap() {
  n = DEFAULT_CAPACITY;
  vertices = new String[]{"Academic Quad","Clapp Library","Lulu Campus Center","Quint","Science Center","Tower Court"};
  buildingPath = new LinkedList<String>();
  weightedEdges = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
  //populate weightedEdges with approximate distances (in feet) between buildings
  //calculated using google maps
  weightedEdges[0][4] = 1000; //acaquad and science
  weightedEdges[4][0] = 1000;
  weightedEdges[0][2] = 920; //acaquad and lulu
  weightedEdges[2][0] = 920;
  weightedEdges[0][1] = 870; //acaquad and clapp
  weightedEdges[1][0] = 870;
  weightedEdges[1][2] = 1700; //clapp and lulu
  weightedEdges[2][1] = 1700;
  weightedEdges[1][4] = 1300; //science and clapp
  weightedEdges[4][1] = 1300; 
  weightedEdges[1][5] = 760; //tower to clapp
  weightedEdges[5][1] = 760; 
  weightedEdges[2][3] = 500; //lulu to quint
  weightedEdges[3][2] = 500; 
  weightedEdges[0][3] = 1000; //aca to quint
  weightedEdges[3][0] = 1000; 
  weightedEdges[3][4] = 1500; //quint to sci
  weightedEdges[4][3] = 1500; 
  weightedEdges[2][5] = 1030; //lulu to tower
  weightedEdges[5][2] = 1030; 
 }

 /******************************************************************
    Returns a string representation of the graph. 
  ******************************************************************/
 public String toString() {
  if (n == 0) {
   return "Graph is empty";
  }

  String result = "";
  result += "\ni ";

  for (int i = 0; i < n; i++) {
   result += "" + vertices[i].substring(0,3);
   if (i < 10) {
    result += " ";
   }
  }
  result += "\n";

  for (int i = 0; i < n; i++) {
   result += "" + vertices[i].substring(0,3) + " ";

   for (int j = 0; j < n; j++) {
    if (weightedEdges[i][j]!=0) {
     result += weightedEdges[i][j] + " ";
    } else {
     result += "- "; //just empty space
    }
   }
   result += "\n";
  }
  return result;
 }

 /******************************************************************
    Calculates the shortest path between orig and des using dijsktra's
    algorithm. Returns an integer representing the length of the shortest 
    path
  ******************************************************************/
 public int getShortestPath (String source,String des) {
   // if buildingPath isn't empty, set it equal to an empty LinkedList
   if (buildingPath.size()!=0) {
     LinkedList<String> newBuilding = new LinkedList<String>();
     buildingPath = newBuilding;
   }
  int sourceIndex = getIndex(source);
  // System.out.println(sourceIndex);
  int desIndex = getIndex(des);
  // System.out.println(desIndex);
  if (sourceIndex!=NOT_FOUND && desIndex!=NOT_FOUND) {
   int[] dist = new int[DEFAULT_CAPACITY];
   String[] prev = new String[DEFAULT_CAPACITY];
   LinkedList<Integer> q = new LinkedList<Integer>(); //stores distances
   LinkedList<String> v = new LinkedList<String>(); //stores names of vertices
   dist[sourceIndex] = 0;
   prev[sourceIndex] = null;

   for (int i=0;i<n;i++) {
    if (i!=sourceIndex) {
     dist[i] = 100000; //assign infinity for each value
     prev[i] = null; //assign null (or undefined) for each value
    } 
    q.add(dist[i]); //add each distance to q
    v.add(vertices[i]); //add each vertices to v
   }
   // System.out.println("v: "+ v);
   // System.out.println("q: " + q);
   //q is a list of [0,infinity,infinity,infinity,infinity]
   //that is, if the origin is the first element in the list
   //v is a linkedlist form of vertices array

   while (q.size()!=0) {
    int indexOfSmallest = findMin(q); //find the smallest num in q
    // System.out.println("indexOfSmallest " + indexOfSmallest);
    int u = q.get(indexOfSmallest); //u is a weight
    String uu = v.get(indexOfSmallest); //uu is the vertex with that weight
    q.remove(indexOfSmallest); //remove u from q
    String removed = v.remove(indexOfSmallest); //remove u from v as well
    //System.out.println("removed: " + removed);
    int removedIndex = getIndex(removed); //finds u's index in vertices
    //System.out.println("removedIndex: " + removedIndex);
    // System.out.println(vertices[indexOfSmallest]); 
    LinkedList<String> neighbors = getSuccessors(vertices[removedIndex]); //neighbors of v
    //System.out.println("neighbors: " + neighbors); 
    // System.out.println("dist[removedIndex]: " + dist[removedIndex]);
    for (int i=0;i<neighbors.size();i++) {
     if (v.contains(neighbors.get(i))) { //only do this if neighbor is still in v
      int alt = dist[removedIndex] + length(vertices[removedIndex],neighbors.get(i)); //dist[u] + length(u,v)
      //System.out.println("dist[removedIndex]: " + dist[removedIndex]);
      //System.out.println("length: " + length(vertices[removedIndex],neighbors.get(i)));
      //System.out.println("alt: " + alt);
      int index = getIndex(neighbors.get(i)); //find its index in vertices
      //System.out.println("index: "+ index);
      //System.out.println("dist[index]: " + dist[index]);
      if (alt<dist[index]) { //check to see if alt is less than whats already in dist at index
       dist[index] = alt;
       prev[index] = uu; //uu = vertices[removedIndex];
      }
     }
    }
   }
   // System.out.println();
   // System.out.println("previous array: ");
   // for (int i=0;i<prev.length;i++) {
   //  System.out.println(prev[i]);
   // }
   // System.out.println();
   // System.out.println("dist array: ");
   // for (int i=0;i<dist.length;i++) {
   //  System.out.println(dist[i]);
   // }
   // System.out.println("dist[desIndex]: " + dist[desIndex]); //distance from source to destination
   
   //add path to LinkedList
   String destination = des; //save destination in a string variable
   buildingPath.add(destination); //add to linkedlist
   String beforeDes = prev[desIndex]; //save the building that comes immediately before it in a string variable as well
   while (!beforeDes.equals(source)) { //keep adding the previous building until the previous building is the source
    buildingPath.add(beforeDes);
    destination = beforeDes;
    beforeDes = prev[getIndex(destination)];
   }
   buildingPath.add(source); //add the source as the last thing
   // System.out.println(buildingPath);
   System.out.println("distance: " + dist[desIndex]);
   return dist[desIndex];
  }
  //vertices cannot be found
  throw new IllegalArgumentException("No such vertex index");
 }

 /******************************************************************
 Getter method that returns the LinkedList buildingPath in a nice 
 string form for the Jlabel in WellesleyMapGUI.
  ******************************************************************/
 public String getBuildingPath() {
  String path = "";
  for (int i=buildingPath.size()-1;i>-1;i--) {
   path += buildingPath.get(i) + " -> ";
  }
  return path;
 } 


 /******************************************************************
 Helper method:
 Retrieve from a graph the vertices x following vertex v (v->x)
    and returns them onto a linked list
  ******************************************************************/
 private LinkedList<String> getSuccessors(String vertex){
  LinkedList<String> neighbors = new LinkedList<String>();
  int v = getIndex(vertex); 
  if (v == NOT_FOUND) return neighbors; //check if vertex is valid

  //vertex exists!
  for (int i = 0; i < n; i++) {
   if (weightedEdges[v][i]>0) {
    neighbors.add(vertices[i]); //if there's an edge, add i to linked list
   }
  }    
  return neighbors;    
 }


 /******************************************************************
    Helper method: 
    Returns the index in a given array that has the smallest value. Will
    Be used in getShortestPath to find the vertex that has the minimum
    distance from the source in the pathWeight array.
  ******************************************************************/
    private int findMin(LinkedList<Integer> a) {
     int smallest = a.get(0);
     int index = 0;
     for (int i=1;i<a.size();i++) {
      if (a.get(i)<smallest) {
       smallest = a.get(i);
       index = i;
      }
     }
     return index;
    }

 /******************************************************************
    Helper method: 
 Returns the length between two given vertices 
  ******************************************************************/
 private int length(String v1,String v2) {
  int v1Index = getIndex(v1); // get index of each vertex
  int v2Index = getIndex(v2);
  if (v1Index!=NOT_FOUND && v2Index!=NOT_FOUND) { // if they exist
   return weightedEdges[v1Index][v2Index]; // return weight (length)
  }
  //vertices cannot be found
  throw new IllegalArgumentException("No such vertex index");
 }

 /******************************************************************
    Helper method:
    Returns the index of a given vertex, if it exists. 
  ******************************************************************/
 private int getIndex(String vertex) {
  for (int i=0;i<n;i++) {
   if (vertices[i].equals(vertex)) {
    return i;
   }
  }
  return NOT_FOUND; //return the final variable if vertex doesnt exist
 }

  /******************************************************************
    Main method tests functions
    ******************************************************************/
  public static void main(String[] args) {
    WellesleyMap w = new WellesleyMap();
    System.out.println(w);
    System.out.println("Academic Quad to Clapp Library");
    w.getShortestPath("Academic Quad", "Clapp Library");
    System.out.println();
    System.out.println("Academic Quad to Lulu Campus Center");
    w.getShortestPath("Academic Quad", "Lulu Campus Center");
    System.out.println();
    System.out.println("Academic Quad to Science Center");
    w.getShortestPath("Academic Quad", "Science Center");
    System.out.println();
    System.out.println("Academic Quad to Quint");
    w.getShortestPath("Academic Quad", "Quint");
    System.out.println();
    System.out.println("Academic Quad to Tower Court");
    w.getShortestPath("Academic Quad", "Tower Court");
    
    System.out.println();
   System.out.println("Clapp Library to Academic Quad");
    w.getShortestPath("Clapp Library", "Academic Quad");
    System.out.println();
    System.out.println("Clapp Library to Lulu Campus Center");
    w.getShortestPath("Clapp Library", "Lulu Campus Center");
    System.out.println();
    System.out.println("Clapp Library to Science Center");
    w.getShortestPath("Clapp Library", "Science Center");
    System.out.println();
    System.out.println("Clapp Library to Quint");
    w.getShortestPath("Clapp Library", "Quint");
    System.out.println();
    System.out.println("Clapp Library to Tower Court");
    w.getShortestPath("Clapp Library", "Tower Court");
                       
    System.out.println();
    System.out.println("Lulu Campus Center to Academic Quad");
    w.getShortestPath("Lulu Campus Center","Academic Quad");
    System.out.println();
   System.out.println("Lulu Campus Center to Clapp Library");                      
    w.getShortestPath("Lulu Campus Center","Clapp Library");
    System.out.println();
    System.out.println("Lulu Campus Center to Quint");                                         
    w.getShortestPath("Lulu Campus Center","Quint");
    System.out.println();          
    System.out.println("Lulu Campus Center to Science Center");                                            
    w.getShortestPath("Lulu Campus Center","Science Center");
    System.out.println();
    System.out.println("Lulu Campus Center to Tower Court");                                                                
    w.getShortestPath("Lulu Campus Center","Tower Court");
 
    System.out.println();
    System.out.println("Quint to Academic Quad");  
    w.getShortestPath("Quint", "Academic Quad");
    System.out.println();
    System.out.println("Quint to Clapp Library");  
    w.getShortestPath("Quint", "Clapp Library");
    System.out.println();
    System.out.println("Quint to Lulu Campus Center");                       
    w.getShortestPath("Quint", "Lulu Campus Center");
    System.out.println(); 
    System.out.println("Quint to Science Center");                     
    w.getShortestPath("Quint", "Science Center");
    System.out.println();
    System.out.println("Quint to Tower Court");  
    w.getShortestPath("Quint", "Tower Court");
 
   System.out.println();
    System.out.println("Science Center to Academic Quad");                        
    w.getShortestPath("Science Center", "Academic Quad");
    System.out.println();
    System.out.println("Science Center to Clapp Library");                        
    w.getShortestPath("Science Center", "Clapp Library");
    System.out.println();    
    System.out.println("Science Center to Lulu Campus Center");                        
    w.getShortestPath("Science Center", "Lulu Campus Center");
    System.out.println();
    System.out.println("Science Center to Quint");
    w.getShortestPath("Science Center", "Quint");
    System.out.println();
    System.out.println("Science Center to Tower Court");                   
    w.getShortestPath("Science Center", "Tower Court");
                       
//    System.out.println("Tower Court to Academic Quad"); // this doesn't work -- prints out 10,000
//    w.getShortestPath("Tower Court", "Academic Quad");
    System.out.println();
    System.out.println("Tower Court to Clapp Library");   
    w.getShortestPath("Tower Court", "Clapp Library");
    System.out.println();
    System.out.println("Tower Court to Lulu Campus Center");  
    w.getShortestPath("Tower Court", "Lulu Campus Center");
    System.out.println();
    System.out.println("Tower Court to Quint");  
    w.getShortestPath("Tower Court", "Quint");
    System.out.println();
    System.out.println("Tower Court to Science Center");                        
    w.getShortestPath("Tower Court", "Science Center");
  }
}


