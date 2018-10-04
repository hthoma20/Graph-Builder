package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GraphFactory {
    public static final int GRAPHRAD= 100;

    /**
     * creates the complete graph, K_n
     *
     * @param n number of vertexes in g
     * @return a graph with n-vertexes such that each vertex is adjacent to each
     *          other vertex
     */
    public static Graph complete(int n){
        if(n < 0){
            throw new IllegalArgumentException("n must be non-negative");
        }

        Graph graph= new Graph();

        ArrayList<Vertex> vertexList= new ArrayList<>(n);

        int center= GRAPHRAD+100;
        for(int i=0;i<n;i++){
            double angle= i*2*Math.PI/n;
            double x= center + Math.cos(angle)*GRAPHRAD;
            double y= center + Math.sin(angle)*GRAPHRAD;
            Vertex v= new Vertex((int)x,(int)y);
            vertexList.add(v);
            graph.addVertex(v);
        }

        for(int i=0;i<vertexList.size();i++){
            for(int j=i+1;j<vertexList.size();j++){
                graph.addEdge(new Edge(vertexList.get(i),vertexList.get(j)));
            }
        }

        return graph;
    }

    /**
     *
     * @param n number of vertexes in set 1
     * @param m number of vertexes in set 2
     * @return a graph with a vertex set V such that V= union(N,M)
     *          where N is a set of n vertexes, M is a set of m vertexes,
     *          and edge set E= {(a,b)| a is in N and b is in M}
     */
    public static Graph completeBipartite(int n, int m){
        if(n < 0){
            throw new IllegalArgumentException("n must be non-negative");
        }
        if(m < 0){
            throw new IllegalArgumentException("m must be non-negative");
        }

        Graph graph= new Graph();

        Set<Vertex> nSet= new HashSet<>(n);
        Set<Vertex> mSet= new HashSet<>(m);

        int nY= 100;
        int mY= nY+100;
        int startX= 50;
        int deltaX= 50;
        for(int i=0;i<n;i++){
            Vertex v= new Vertex(startX+i*deltaX,nY);
            nSet.add(v);
            graph.addVertex(v);
        }
        for(int i=0;i<m;i++){
            Vertex v= new Vertex(startX+i*deltaX,mY);
            mSet.add(v);
            graph.addVertex(v);
        }

        for(Vertex vn : nSet){
            for(Vertex vm : mSet){
                graph.addEdge(new Edge(vn,vm));
            }
        }

        return graph;
    }

    /**
     * creates a cycle of length n
     *
     * @param n number of vertexes and edges
     * @return a cycle with n vertexes
     */
    public static Graph cycle(int n){
        Graph graph= new Graph();

        ArrayList<Vertex> vertexList= new ArrayList<>(n);

        int center= GRAPHRAD+100;
        for(int i=0;i<n;i++){
            double angle= i*2*Math.PI/n;
            double x= center + Math.cos(angle)*GRAPHRAD;
            double y= center + Math.sin(angle)*GRAPHRAD;
            Vertex v= new Vertex((int)x,(int)y);
            vertexList.add(v);
            graph.addVertex(v);
        }

        for(int i=1;i<vertexList.size();i++){
            graph.addEdge(new Edge(vertexList.get(i),vertexList.get(i-1)));
        }
        graph.addEdge(new Edge(vertexList.get(0),vertexList.get(vertexList.size()-1)));

        return graph;
    }

    /**
     * creates a path of lenght n
     *
     * @param n number of edges in the path
     * @return a path of length n
     */
    public static Graph path(int n){
        //take a cycle and remove an edge

        Graph graph= cycle(n+1);

        graph.removeEdge(graph.getEdgeSet().toArray(new Edge[0])[0]);

        return graph;
    }

    /**
     * creates a "random" graph with n vertexes
     *
     * @param n number of vertexes
     * @param density the chance in range [0,1] that each potential edge is included
     * @return a graph with n vertexes such that each potential edge (v1,v2)
     *          has a 'density' chance of being included in the graph
     */
    public static Graph random(int n, double density){
        //create a K-n and potentially remove each edge
        Graph graph= complete(n);

        //figure out which edges to remove
        Set<Edge> removeEdges= new HashSet<>(n/2);
        for(Edge e : graph.getEdgeSet()){
            if(Math.random() >= density){
                removeEdges.add(e);
            }
        }

        //remove the edges
        for(Edge e : removeEdges){
            graph.removeEdge(e);
        }

        return graph;
    }
}
