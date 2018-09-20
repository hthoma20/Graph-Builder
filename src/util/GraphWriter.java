package util;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class GraphWriter {
    private File file;

    public GraphWriter(String path){
       this(new File(path));
    }

    public GraphWriter(File file){
        this.file= file;
    }

    public boolean writeGraph(Graph g){
        PrintStream fos= null;
        try {
            fos = new PrintStream(file);
        }
        catch (FileNotFoundException e) {
            return false;
        }

        Vertex[] vertexes= g.getVertexSet().toArray(new Vertex[0]);
        Edge[] edges= g.getEdgeSet().toArray(new Edge[0]);

        for(int i=0;i<vertexes.length;i++){
            fos.println(i+":"+vertexes[i].getX()+","+vertexes[i].getY());
        }

        for(int i=0;i<edges.length;i++){
            int v1= indexOf(edges[i].getV1(),vertexes);
            int v2= indexOf(edges[i].getV2(),vertexes);
            fos.println(v1+","+v2);
        }

        fos.close();
        return true;
    }

    private int indexOf(Vertex v, Vertex[] vertexes){
        for(int i=0;i<vertexes.length;i++){
            if(vertexes[i] == v){
                return i;
            }
        }

        return -1;
    }
}
