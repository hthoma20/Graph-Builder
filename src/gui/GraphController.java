package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import util.LineSegment;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphController implements MouseListener, MouseMotionListener, KeyListener, ActionListener {
    private GraphPanel panel;
    private ControlPanel controls;

    private Graph graph;

    private Vertex selectedVertex= null;
    private WorkingEdge workingEdge= null;
    private LineSegment removeSegment= null;

    private EditMode mode;

    public GraphController(GraphPanel panel, ControlPanel controls){
        this.panel= panel;
        this.controls= controls;
        this.graph= panel.getGraph();

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        controls.addActionListener(this);

        this.mode= EditMode.VERTEX;
        controls.setSelectedMode(mode);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(mode){
            case DRAG:
                selectVertex(e.getX(),e.getY());
                break;
            case EDGE:
                selectVertex(e.getX(),e.getY());
                moveWorkingEdge(e.getX(),e.getY());
                break;
            case REMOVE:
                initRemoveSegment(e.getPoint());
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(mode){
            case VERTEX:
                addVertex(e.getX(),e.getY());
                break;
            case DRAG:
                selectedVertex= null;
                break;
            case EDGE:
                addEdge(e.getX(),e.getY());
                removeWorkingEdge();
                break;
            case REMOVE:
                removeVertex(e.getX(),e.getY());
                removeEdge();
                removeRemoveSegment();
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (mode){
            case DRAG:
                moveSelectedVertex(e.getX(),e.getY());
                break;
            case EDGE:
                moveWorkingEdge(e.getX(),e.getY());
                break;
            case REMOVE:
                moveRemoveSegment(e.getPoint());
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyChar()){
            case 'v':
                setMode(EditMode.VERTEX);
                break;
            case 'e':
                setMode(EditMode.EDGE);
                break;
            case 'd':
                setMode(EditMode.DRAG);
                break;
            case 'r':
                setMode(EditMode.REMOVE);
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command= e.getActionCommand();
        if(command.equals("colorCheckBox")){
            panel.setDoColor(controls.colorCheckBoxChecked());
            return;
        }

        EditMode newMode= EditMode.stringToMode(command);
        if(newMode == null){
            System.err.println("Unknown action fired to GraphController.actionPerformed");
            return;
        }

        setMode(newMode);
    }

    private void setMode(EditMode mode){
        this.mode= mode;
        controls.setSelectedMode(mode);
    }

    private void moveWorkingEdge(int x, int y){
        if(selectedVertex == null) return;
        if(workingEdge == null){
            workingEdge= new WorkingEdge(selectedVertex,x,y);
            panel.setWorkingEdge(workingEdge);
        }
        else{
            workingEdge.moveTo(x,y);
        }

        panel.repaint();
    }

    private void removeWorkingEdge(){
        workingEdge= null;
        panel.setWorkingEdge(null);
        panel.repaint();
    }

    private void initRemoveSegment(Point p1){
        this.removeSegment= new LineSegment(p1,p1);
        panel.setRemoveSegment(removeSegment);
    }

    private void moveRemoveSegment(Point p2){
        if(removeSegment == null) return;

        removeSegment.setP2(p2);
        panel.repaint();
    }

    private void removeRemoveSegment(){
        removeSegment= null;
        panel.setRemoveSegment(null);
        panel.repaint();
    }

    private void moveSelectedVertex(int x, int y){
        if(selectedVertex == null) return;
        selectedVertex.moveTo(x,y);
        panel.repaint();
    }

    /**
     * remove the egde that intersects removeSegment
     */
    private void removeEdge(){
        if(removeSegment == null) return;

        ArrayList<Edge> intersecting= new ArrayList<>();

        for(Edge e : graph.getEdgeSet()){
            if(e.getLineSegment().intersects(removeSegment)){
                intersecting.add(e);
            }
        }

        for(Edge e : intersecting){
            graph.removeEdge(e);
        }

        panel.repaint();
    }

    private void selectVertex(int x, int y){
        selectedVertex= vertexAt(x,y);
    }

    private void addVertex(int x, int y){
        graph.addVertex(new Vertex(x,y));
        panel.repaint();
    }

    private void removeVertex(int x, int y){
        graph.removeVertex(vertexAt(x,y));
        panel.repaint();
    }

    /**
     * adds an edge from the seleced vertex to the vertex
     * at point (x,y)
     * @param x the x coord of a vertex
     * @param y the y coord of a vertex
     */
    private void addEdge(int x, int y){
        if(selectedVertex == null) return;
        Vertex v2= vertexAt(x,y);
        if(v2 == null) return;

        graph.addEdge(new Edge(selectedVertex,v2));
        panel.repaint();
    }

    /**
     *
     * @param x the coord to check
     * @param y the y coord to check
     * @return the vertex at the point (x,y)
     */
    private Vertex vertexAt(int x, int y){
        for(Vertex v : graph.getVertexSet()){
            if(v.within(x,y,panel.getVRad())){
                return v;
            }
        }

        return null;
    }

    public enum EditMode{
        VERTEX,
        EDGE,
        DRAG,
        REMOVE;

        public static EditMode stringToMode(String str){
            for(EditMode mode : EditMode.values()){
                if(mode.toString().equals(str)){
                    return mode;
                }
            }

            return null;
        }
    }

    /*
    UNUSED
     */
    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {    }

    @Override
    public void mouseMoved(MouseEvent e) {    }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void keyPressed(KeyEvent e) {    }

    @Override
    public void keyReleased(KeyEvent e) {    }
}
