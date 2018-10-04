package graph;

import util.DegreeSequence;
import util.ObservableMap;
import util.Permutor;

import java.util.*;

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

    /**
     * @return a deep copy of this graph
     */
    public Graph copy(){
        Graph g= new Graph();

        //establish a mapping from this graphs vertex to
        //g's vertexes
        HashMap<Vertex, Vertex> vertexMap= new HashMap<>(this.vertexSet.size());

        //create copies of each vertex in this graph
        //add them to the mapping
        for(Vertex thisV : this.vertexSet){
            Vertex gV= thisV.copy();
            g.vertexSet.add(gV);
            vertexMap.put(thisV, gV);
        }

        //create copies of the edges
        //using the mapping to preseve adjacency
        for(Edge thisE : this.edgeSet){
            Edge gE= new Edge(vertexMap.get(thisE.getV1()),vertexMap.get(thisE.getV2()));
            g.edgeSet.add(gE);
        }

        //copy the coloring map using the vertex map
        for(Vertex thisV : this.coloring.keySet()){
            g.coloring.put(vertexMap.get(thisV),this.coloring.get(thisV));
        }

        //copy the degree map using the vertex map
        for(Vertex thisV : this.degrees.keySet()){
            g.degrees.put(vertexMap.get(thisV), this.degrees.get(thisV));
        }

        //create a degree sequence for g wit the degree map we just made
        g.degreeSequence= new DegreeSequence(g.degrees);

        return g;
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

        //check that each vertex is actually one of ours
        if(!vertexSet.contains(e.getV1()) || !vertexSet.contains(e.getV2())){
            return false;
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
        for(Edge e : adjacentEdges){
            removeEdge(e);
        }

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

    public void clear(){
        Vertex[] allVertexes= vertexSet.toArray(new Vertex[0]);

        for(Vertex v : allVertexes){
            removeVertex(v);
        }
    }

    /**
     * finds a vertex coloring of the graph, denoted with a map
     * from a vertex to an integer-encoded color
     * using integers [0,k-1] where k is the number of colors
     *
     * @return a map from vertex to color
     */
    private Map<Vertex,Integer> createColoring(List<Vertex> vertexList){
        Map<Vertex, Integer> coloring= new HashMap<>();

        for(Vertex v : vertexList){
            //find the smallest number availible
            //using an array of booleans to indicate whether the index has been seen
            boolean[] colorSeen= new boolean[vertexList.size()];
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

    public Map<Vertex, Integer> createColoring(){
        return createColoring( new ArrayList<>(vertexSet));
    }

    public int chromaticNumber(){
        if(vertexSet.size() <= 1){
            return vertexSet.size();
        }

        List<List<Vertex>> permutations= new Permutor<Vertex>(vertexSet).getPermutations();

        int min= vertexSet.size();
        for(List<Vertex> permutation : permutations){
            Map<Vertex,Integer> coloring= createColoring(permutation);
            int maxColor= 0;
            for(Integer color : coloring.values()){
                maxColor= Math.max(maxColor, color);
            }

            min= Math.min(min, maxColor);
        }

        //colors are indexed at 0, so if min color number is 3, coloring is 4
        return min+1;
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

    public int getDegree(Vertex v){
        return degrees.get(v);
    }
}
