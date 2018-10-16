package util;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphReader {
    private File file;

    public GraphReader(String path){
        this(new File(path));
    }

    public GraphReader(File file){
        this.file= file;
    }

    public Graph readGraph(){
        Scanner scanner= null;
        try {
            scanner= new Scanner(file);
        }
        catch (FileNotFoundException e) {
            return null;
        }

        Graph graph= new Graph();

        ArrayList<Vertex> vertexes= new ArrayList<>();
        //read all vertexes
        String line= scanner.nextLine();
        while(line.contains(":")){
            int colon= line.indexOf(':');
            int comma= line.indexOf(',');

            if(comma == -1){
                return null;
            }

            int index, x, y;

            try{
                index= Integer.parseInt(line.substring(0,colon));
                x= Integer.parseInt(line.substring(colon+1,comma));
                y= Integer.parseInt(line.substring(comma+1));
            }
            catch(IndexOutOfBoundsException | NumberFormatException e){
                return null;
            }

            if(index != vertexes.size()){
                return null;
            }

            Vertex v= new Vertex(x,y);
            vertexes.add(v);
            graph.addVertex(v);

            //if there are no edges, we are done
            if(!scanner.hasNextLine()){
                return graph;
            }
            line= scanner.nextLine();
        }

        //read edges
        //break loop when end of file reached
        while(true){
            int comma= line.indexOf(',');
            if(comma == -1){
                return null;
            }

            int index1, index2;
            try{
                index1= Integer.parseInt(line.substring(0,comma));
                index2= Integer.parseInt(line.substring(comma+1));
            }
            catch(IndexOutOfBoundsException | NumberFormatException e){
                return null;
            }

            Vertex v1, v2;
            try{
                v1= vertexes.get(index1);
                v2= vertexes.get(index2);
            }
            catch(IndexOutOfBoundsException e){
                return null;
            }

            boolean validEdge= graph.addEdge(new Edge(v1,v2));
            if(!validEdge){
                return null;
            }

            if(!scanner.hasNextLine()){
                break;
            }

            line= scanner.nextLine();
        }

        return graph;
    }
}
