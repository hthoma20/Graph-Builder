package graph;

import util.DegreeSequence;
import util.ObservableMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Set<Vertex> vertexSet= new HashSet<>();
    private Set<Edge> edgeSet= new HashSet<>();

    private Map<Vertex, Integer> coloring;
    private ObservableMap<Vertex, Integer> degrees;
    private DegreeSequence degreeSequence;

    public Graph(){
        this.degrees= new ObservableMap<>();
        this.coloring= createColoring();

        this.degreeSequence= new DegreeSequence(degrees);
    }

    public boolean addVertex(Vertex v){
        boolean added= vertexSet.add(v);
        if(!added) return false;

        //this vertex should not be adjacent to anything yet
        degrees.put(v,0);
        coloring.put(v,0);

        return true;
    }

    public boolean addEdge(Edge e){
        //check if the edge already exists
        for(Edge edge : edgeSet){
            if(e.equals(edge)){
                return false;
            }
        }

        boolean added= edgeSet.add(e);
        if(!added) return false;

        //we added a degree to each veretx
        this.degrees.put(e.getV1(),degrees.get(e.getV1())+1);
        this.degrees.put(e.getV2(),degrees.get(e.getV2())+1);

        this.coloring= createColoring();

        return true;
    }

    public boolean removeVertex(Vertex v){
        boolean removed= vertexSet.remove(v);

        if(!removed) return false;

        //remove all adjacent edges
        Set<Edge> adjacentEdges= new HashSet<>();
        for(Edge e : edgeSet){
            if(e.isAdjacent(v)){
                adjacentEdges.add(e);
            }
        }

        edgeSet.removeAll(adjacentEdges);
        this.degrees.remove(v);
        this.coloring= createColoring();

        return true;
    }

    public boolean removeEdge(Edge e){
        boolean removed= edgeSet.remove(e);

        if(!removed) return false;

        degrees.put(e.getV1(),degrees.get(e.getV1())-1);
        degrees.put(e.getV2(),degrees.get(e.getV2())-1);
        this.coloring= createColoring();

        return true;
    }

    /**
     * finds a vertex coloring of the graph, denoted with a map
     * from a vertex to an integer-encoded color
     * using integers [0,k-1] where k is the number of colors
     *
     * @return a map from vertex to color
     */
    public Map<Vertex,Integer> createColoring(){
        Map<Vertex, Integer> coloring= new HashMap<>();

        for(Vertex v : vertexSet){
            //find the smallest number availible
            //using an array of booleans to indicate whether the index has been seen
            boolean[] colorSeen= new boolean[vertexSet.size()];
            for(Vertex a : adjacencies(v)){
                Integer color= coloring.get(a);
                //if this vertex has no color yet, it does not affect min
                if(color != null) {
                    colorSeen[color] = true;
                }
            }

            int minColor= 0;
            while(colorSeen[minColor]) minColor++;

            coloring.put(v,minColor);
        }

        return coloring;
    }

    /**
     *
     * @param v the vertex to find adjacencies for
     * @return all vertexes adjacent to v
     */
    public Set<Vertex> adjacencies(Vertex v){
        Set<Vertex> adjacentVertexes= new HashSet<>();
        for(Edge e : edgeSet){
            Vertex adjacent= e.getAdjacent(v);
            if(adjacent != null){
                adjacentVertexes.add(adjacent);
            }
        }

        return adjacentVertexes;
    }

    public Set<Vertex> getVertexSet(){
        return vertexSet;
    }

    public Set<Edge> getEdgeSet() {
        return edgeSet;
    }

    public Map<Vertex,Integer> getColoring(){
        return coloring;
    }

    public DegreeSequence getDegreeSequence() {
        return degreeSequence;
    }
}
