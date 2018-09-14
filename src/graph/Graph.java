package graph;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<Vertex> vertexSet= new HashSet<>();
    private Set<Edge> edgeSet= new HashSet<>();

    public Graph(){

    }

    public boolean addVertex(Vertex v){
        return vertexSet.add(v);
    }

    public boolean addEdge(Edge e){
        //check if the edge already exists
        for(Edge edge : edgeSet){
            if(e.equals(edge)){
                return false;
            }
        }

        return edgeSet.add(e);
    }

    public Set<Vertex> getVertexSet(){
        return vertexSet;
    }

    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }
}