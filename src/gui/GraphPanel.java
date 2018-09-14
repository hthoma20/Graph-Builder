package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GraphPanel extends JPanel {
    private Graph graph;
    private int vRad;

    private WorkingEdge workingEdge= null;

    private Map<Integer,Color> colorMap;

    public GraphPanel(Graph graph){
        super();
        this.graph= graph;
        this.vRad= 10;

        setupColorMap();
    }

    @Override
    public void paint(Graphics g){
        //clear background
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());

        g.setColor(Color.BLACK);
        for(Edge e : graph.getEdgeSet()){
            drawLine(e.getPoint1(),e.getPoint2(),g);
        }

        if(workingEdge != null){
            workingEdge.paint(g);
        }

        Map<Vertex, Integer> coloring= graph.getColoring();

        for(Vertex v : graph.getVertexSet()){
            System.out.println("Coloring " + coloring.get(v) + " " + colorMap.get(coloring.get(v)));
            g.setColor(colorMap.get(coloring.get(v)));
            fillCircle(v.getX(),v.getY(),vRad,g);
        }

        g.setColor(Color.BLACK);
        paintInfo(g);
    }

    private void paintInfo(Graphics g){
        g.drawString("p= " + graph.getVertexSet().size(), 20, 20);
        g.drawString("q= " + graph.getEdgeSet().size(), 20, 38);
    }

    private void fillCircle(int x, int y, int rad, Graphics g){
        g.fillOval(x-rad,y-rad,2*rad,2*rad);
    }

    private void drawLine(Point p1, Point p2, Graphics g){
        g.drawLine(p1.x,p1.y,p2.x,p2.y);
    }

    private void setupColorMap(){
        this.colorMap= new HashMap<>();
        colorMap.put(0,new Color(255,0,0));
        colorMap.put(1,new Color(0,255,0));
        colorMap.put(2,new Color(0,0,255));
        colorMap.put(3,new Color(255,255,0));
        colorMap.put(4,new Color(255,0,255));
        colorMap.put(5,new Color(0,255,255));
    }

    public void setVRad(int vRad) {
        this.vRad = vRad;
    }

    public void setWorkingEdge(WorkingEdge workingEdge) {
        this.workingEdge = workingEdge;
    }

    public int getVRad() {
        return vRad;
    }

    public Graph getGraph() {
        return graph;
    }
}
