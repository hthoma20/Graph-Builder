package graph;

import java.awt.*;

public class Edge {
    private Vertex v1;
    private Vertex v2;

    public Edge(Vertex v1, Vertex v2){
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

    public boolean equals(Vertex v1, Vertex v2){
        return this.equals(new Edge(v1,v2));
    }

    public Point getPoint1(){
        return new Point(v1.getX(),v1.getY());
    }

    public Point getPoint2(){
        return new Point(v2.getX(),v2.getY());
    }
}
