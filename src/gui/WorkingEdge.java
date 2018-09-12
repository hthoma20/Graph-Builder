package gui;

import graph.Vertex;

import java.awt.*;

public class WorkingEdge {
    private Vertex v;
    private int x;
    private int y;

    public WorkingEdge(Vertex v, int x, int y){
        this.v= v;
        this.x= x;
        this.y= y;
    }

    public void moveTo(int x, int y){
        this.x= x;
        this.y= y;
    }

    public void paint(Graphics g){
        g.drawLine(v.getX(),v.getY(),x,y);
    }
}
