package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.awt.event.*;

public class GraphController implements MouseListener, MouseMotionListener, KeyListener {
    private GraphPanel panel;
    private Graph graph;

    private Vertex selectedVertex= null;
    private WorkingEdge workingEdge= null;

    private EditMode mode;

    public GraphController(GraphPanel panel){
        this.panel= panel;
        this.graph= panel.getGraph();

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        this.mode= EditMode.VERTEX;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(mode){
            case DRAG:
                selectVertex(e.getX(),e.getY());
                break;
            case EDGE:
                selectVertex(e.getX(),e.getY());
                workingEdge= new WorkingEdge(selectedVertex,e.getX(),e.getY());
                panel.setWorkingEdge(workingEdge);
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
                workingEdge= null;
                panel.setWorkingEdge(workingEdge);
                panel.repaint();
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
                workingEdge.moveTo(e.getX(),e.getY());
                panel.repaint();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        switch(e.getKeyChar()){
            case 'v':
                mode= EditMode.VERTEX;
                break;
            case 'e':
                mode= EditMode.EDGE;
                break;
            case 'd':
                mode= EditMode.DRAG;
                break;
        }

        System.out.println(mode.toString());
    }

    private void moveSelectedVertex(int x, int y){
        if(selectedVertex == null) return;
        selectedVertex.moveTo(x,y);
        panel.repaint();
    }

    private void selectVertex(int x, int y){
        selectedVertex= vertexAt(x,y);
    }

    private void addVertex(int x, int y){
        graph.addVertex(new Vertex(x,y));
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
        DRAG;
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
