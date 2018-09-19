package graph;

import util.LineSegment;

import java.awt.*;

public class Edge {
    private Vertex v1;
    private Vertex v2;

    public Edge(Vertex v1, Vertex v2){
        if(v1 == v2){
            throw new IllegalArgumentException("v1 cannot equal v2");
        }
        this.v1= v1;
        this.v2= v2;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Edge){
            return this.equals((Edge)o);
        }

        return false;
    }

    public boolean equals(Edge e){
        if(e.v1 == this.v1 && e.v2 == this.v2){
            return true;
        }
        if(e.v2 == this.v1 && e.v1 == this.v2){
            return true;
        }

        return false;
    }

    public boolean isAdjacent(Vertex v){
        return v == v1 || v == v2;
    }

    /**
     *
     * @param v the vertex to find its pair
     * @return the vertex that this edge links to v
     *          or null if v is not adjacent to this edge
     */
    public Vertex getAdjacent(Vertex v){
        if(v == this.v1) return v2;
        if(v == this.v2) return v1;
        return null;
    }

    public boolean equals(Vertex v1, Vertex v2){
        return this.equals(new Edge(v1,v2));
    }

    public Point getPoint1(){
        return new Point(v1.getX(),v1.getY());
    }

    public Point getPoint2(){
        return new Point(v2.getX(),v2.getY());
    }

    public LineSegment getLineSegment(){
        return new LineSegment(getPoint1(),getPoint2());
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }
}
