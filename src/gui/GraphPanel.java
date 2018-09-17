package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
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
            g.setColor(intToColor(coloring.get(v)));
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
        this.colorMap.put(0, hColor(0));
        this.colorMap.put(1, hColor(120));
        this.colorMap.put(2, hColor(240));
        this.colorMap.put(3, hColor(60));
        this.colorMap.put(4, hColor(180));
        this.colorMap.put(5, hColor(300));
    }

    private Color intToColor(int x){
        if(colorMap.containsKey(x)){
            return colorMap.get(x);
        }

        int[] hArray= new int[colorMap.size()];
        int index= 0;
        for(Color c : colorMap.values()){
            hArray[index++]= colorH(c);
        }
        Arrays.sort(hArray);

        //the largest arc distance in degrees
        int maxDist= 0;
        //index where the largest arc starts
        int arcStart= 0;
        for(int i=0; i < hArray.length-1; i++){
            int dist= hArray[i+1]-hArray[i];
            if(dist > maxDist){
                maxDist= dist;
                arcStart= i;
            }
        }
        int dist= 360 - hArray[hArray.length-1];
        if(dist > maxDist){
            maxDist= dist;
            arcStart= hArray.length-1;
        }

        Color newColor= hColor(hArray[arcStart]+maxDist/2);

        colorMap.put(x,newColor);
        return newColor;
    }

    /**
     * find the hue angle of a color
     *
     * @param c the color
     * @return the hue in range [0,360)
     */
    private int colorH(Color c){
        float hVal= Color.RGBtoHSB(c.getRed(),c.getBlue(),c.getGreen(),null)[0];
        return (int)(hVal*360);
    }

    /**
     *
     * @param h the hue angle in range [0,360)
     * @return the color with h and full s and v
     */
    private Color hColor(int h){
        return Color.getHSBColor(1-(float)h/360,1,1);
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
