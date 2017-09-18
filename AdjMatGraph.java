/*
 * AdjMatGraph.java
 * Emily Van Laarhoven
 * CS230 Assign 7 Task 1
 * Nov 7, 2016 11:59pm
 */

import java.util.*;
import java.io.*;

public class AdjMatGraph<T> implements Graph<T> {
  
  //constant values
  private final int NOT_FOUND = -1;
  private final int DEFAULT_CAPACITY=1;
  
  //instance variables
  private int n; //number of vertices
  private T[] vertices; //array of vertices
  private boolean [][] arcs; //two dimensional array of arcs between nodes
  
  //constructor - creates an empty graph with no vertices
  public AdjMatGraph () {
    n=0; 
    this.arcs = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    this.vertices = (T[])(new Object[DEFAULT_CAPACITY]);
  }    
  
  //second constructor - takes in file name from yEd graph file export
  public AdjMatGraph (String filename) {
    this(); //calls empty constructor to initialize instance variables
    try {
      Scanner reader = new Scanner(new File(filename));        
      String line = "";
      line=reader.nextLine().trim(); 
      while (!(line.equals("#"))) {
        T vertex = (T)line.split(" ")[1]; //name of vertex is second part of line, cast as generic type
        addVertex(vertex);
        line=reader.nextLine().trim(); 
      }
      while (reader.hasNext()) {
        line = reader.nextLine().trim();
        //use line #s of vertices list as indices (but subtract 1 to adjust)
        int v1 = Integer.parseInt(line.split(" ")[0])-1;
        int v2 = Integer.parseInt(line.split(" ")[1])-1;
        addArc(vertices[v1], vertices[v2]);
      }
      reader.close();
    } catch (FileNotFoundException ex) {
      System.out.println(ex);
    }
  }
  
  /** Returns true if this graph is empty, false otherwise. */
  public boolean isEmpty() {
    return n==0;
  }
  
  /** Returns the number of vertices in this graph. */
  public int n(){
    return n;
  }
  
  /** Returns the number of arcs in this graph. */
  public int m(){
    int count = 0;
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        if (arcs[i][j]==true) {
          count++;
        }
      }
    }
    return count;
  }
  
  /** Returns true iff a directed edge exists b/w given vertices */
  public boolean isArc(T vertex1,T vertex2){
    int indexV1 = 0; //assume both vertices exist
    int indexV2 = 0;  
    for (int i=0; i<n; i++) {
      if (vertices[i].equals(vertex1)){ 
        indexV1=i;
      }
    }
    for (int j=0; j<n; j++) {
      if (vertices[j].equals(vertex2)){
        indexV2=j;
      }
    }   
    return (arcs[indexV1][indexV2]==true); 
  }
  
  /** Returns true iff an edge exists between two given vertices
    * which means that two corresponding arcs exist in the graph */
  public boolean isEdge (T vertex1, T vertex2){
    int indexV1 = 0;
    int indexV2 = 0;  
    for (int i=0; i<n; i++) {
      if (vertices[i].equals(vertex1)){
        indexV1=i;
      }
    }
    for (int j=0; j<n; j++) {
      if (vertices[j].equals(vertex2)){ 
        indexV2=j;
      }
    }
    return (arcs[indexV2][indexV1]==true && arcs[indexV1][indexV2]==true);
  }
  
  /** Returns true IFF the graph is undirected, that is, for every
    * pair of nodes i,j for which there is an arc, the opposite arc
    * is also present in the graph.  */
  public boolean isUndirected(){
    for (int i=0; i<n; i++) {
      for (int j=0; j<n; j++) {
        if (arcs[i][j]!=arcs[j][i]) {
          return false;
        }
      }
    }
    return true;
  }
  
  /** 
   * doubles the capacity of vertices and arcs arrays
   */
  private void doubleSizeOfArrays(){
    T[] temp_verts = (T[]) new Object[vertices.length*2];
    for (int s=0; s<vertices.length; s++) {
      temp_verts[s] = vertices[s];
    }
    boolean [][] temp_arcs = new boolean[arcs.length*2][arcs.length*2];
    for (int i=0; i<arcs.length; i++) {
      for (int j=0; j<arcs.length; j++) {
        temp_arcs[i][j]=arcs[i][j];
      }
    }
    arcs = temp_arcs;
    vertices=temp_verts;
  }
  
  /** Adds a vertex to this graph, associating object with vertex.
    * If the vertex already exists, nothing is inserted. */
  public void addVertex (T vertex){
    for (int i=0; i<n; i++) {
      if (vertices[i].equals(vertex)) {
        return;
      }
    }

    if (vertices.length==n) { //check if space in vertices
      doubleSizeOfArrays();
    } 
    vertices[n]=vertex; 
    for (int j=0; j<n; j++) { //set all arcs to zero to that vertex 
      arcs[n][j]=false;
      arcs[j][n]=false;
    }
    n++; //increment # of vertices
  }
  
  /** Removes a single vertex with the given value from this graph.
    * If the vertex does not exist, it does not change the graph. */
  public void removeVertex (T vertex){
    int index = findIndex(vertex);
    if (index!=-1) { //if vertex is found (if not, do nothing)
      for (int j=index; j<n; j++) {
        vertices[j]=vertices[j+1]; //remove vertex from vertices array and condense array
      }
      for (int s=index; s<n; s++) {
        for (int t=index; t<n; t++) {
          arcs[s][t]=arcs[s+1][t+1]; //remove the column and row of arcs to and from that vertex
        }
      }
      n--;
    }
  }
  
  //helper method to find index of a given vertex in the vertices array
  private int findIndex(T vertex) {
    int index = -1; //vertex not found
    for(int i=0; i<n; i++) {
      if (vertices[i].equals(vertex)) {
        index=i;
      }
    }
    return index;
  }
  
  /** Inserts an arc between two vertices of this graph,
    * if the vertices exist. Else it does not change the graph. */
  public void addArc (T vertex1, T vertex2){
    if (findIndex(vertex1) != -1 && findIndex(vertex2) != -1) {
      arcs[findIndex(vertex1)][findIndex(vertex2)]=true;
    }
  } 
  
  /** Removes an arc between two vertices of this graph,
    * if the vertices exist. Else it does not change the graph. */
  public void removeArc (T vertex1, T vertex2){
    if (findIndex(vertex1)!=-1 && findIndex(vertex2)!=-1) {
      arcs[findIndex(vertex1)][findIndex(vertex2)]=false;
    }
  }
  
  /** Inserts an edge between two vertices of this graph,
    * if the vertices exist. Else does not change the graph. */
  public void addEdge (T vertex1, T vertex2){
    if (findIndex(vertex1)!=-1 && findIndex(vertex2)!=-1) {
      arcs[findIndex(vertex1)][findIndex(vertex2)]=true;
      arcs[findIndex(vertex2)][findIndex(vertex1)]=true;
    }
  }
  
  /** Removes an edge between two vertices of this graph,
    if the vertices exist, else does not change the graph. */
  public void removeEdge (T vertex1, T vertex2){
    if (findIndex(vertex1)!=-1 && findIndex(vertex2)!=-1) {
      arcs[findIndex(vertex1)][findIndex(vertex2)]=false;
      arcs[findIndex(vertex2)][findIndex(vertex1)]=false;
    }
  }
  
  /** Retrieve from a graph the vertices adjacent to vertex v.
    Assume that the vertex is in the graph */
  public LinkedList<T> getSuccessors(T vertex){
    LinkedList<T> successors = new LinkedList<T>();
    int index = findIndex(vertex);
    for (int i=0; i<n; i++) {
      if (arcs[index][i]==true) {
        successors.add(vertices[i]);
      }
    }
    return successors;
  } 
  
  /** Retrieve from a graph the vertices x preceding vertex v (x->v)
    and returns them onto a linked list */
  public LinkedList<T> getPredecessors(T vertex){
    LinkedList<T> predecessors = new LinkedList<T>();
    int index = findIndex(vertex);
    for (int i=0; i<n; i++) {
      if (arcs[i][index]==true) {
        predecessors.add(vertices[i]);
      }
    }
    return predecessors;
  }
  
  /** Returns a string representation of the adjacency matrix. */
  public String toString() {
    String s="Vertices: \n";
    for (int i=0; i<n; i++) {
      s+=vertices[i]+"\n";
    }
    return s; //not pretty, just lists vertices - for testing purposes
  }
  
  /** Saves the current graph into a .tgf file.
    If it cannot save the file, a message is printed. */
  public void saveTGF(String tgf_file_name) {
    try {
      PrintWriter writer = new PrintWriter (new File(tgf_file_name));
      writer.println(vertices.toString()); //all of these are just rough, for testing purposes
      writer.println("#");
      writer.println(arcs.toString()); //not really sure how to use the toString method of this class
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }
  
  //main method for testing
  public static void main(String[] args) {
    
    //testing reading in from file
    AdjMatGraph graph1 = new AdjMatGraph("test1.tgf");
    System.out.println(graph1); //works - prints ABCD, toString, and isUndirected
    System.out.println(graph1.isUndirected()); //false - works
    
    AdjMatGraph graph3 = new AdjMatGraph("test2.tgf");
    System.out.println(graph3.isUndirected()); //false - works
    System.out.println(graph3); //lists names - works
    graph3.removeVertex("phil");
    graph3.removeVertex("claire");
    graph3.removeVertex("lily");
    System.out.println(graph3); //lists names without phil claire lily - works
    
    //testing creating graph using empty parameter constructor
    AdjMatGraph<String> graph2 = new AdjMatGraph<String>();
    //create vertices
    String a = "A";
    String b = "B";
    String c = "C";
    //add vertices
    graph2.addVertex(a);
    graph2.addVertex(b);
    graph2.addVertex(c);
    System.out.println(graph2); //toString works - prints ABC
    //add some arcs and edges
    graph2.addArc(a,b);
    graph2.addArc(b,a);
    graph2.addArc(c,a);
    graph2.addArc(a,c);
    graph2.addEdge(b,c);
    System.out.println(graph2.getSuccessors(a));//works - prints [B,C]
    System.out.println(graph2.getPredecessors(b));//works - prints [A,C]
    //check predicates and removing arcs, edges, vertices
    System.out.println(graph2.isEdge(a,b)); //true - works
    System.out.println(graph2.isUndirected()); //true - works
    System.out.println(graph2.isEmpty());//false - works
    graph2.removeArc(b,a);
    System.out.println(graph2.isEdge(a,b)); //false -works
    System.out.println(graph2.isUndirected()); //false - works
    graph2.removeEdge(b,c);
    graph2.removeVertex(a);
    System.out.println(graph2.m()); //0 - works
    System.out.println(graph2.n()); //2 - works
    System.out.println(graph2); //BC -works
    
    
  }
  
}