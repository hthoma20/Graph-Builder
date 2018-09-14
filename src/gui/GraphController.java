package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.awt.event.*;

public class GraphController implements MouseListener, MouseMotionListener, KeyListener, ActionListener {
    private GraphPanel panel;
    private ControlPanel controls;

    private Graph graph;

    private Vertex selectedVertex= null;
    private WorkingEdge workingEdge= null;

    private EditMode mode;

    public GraphController(GraphPanel panel, ControlPanel controls){
        this.panel= panel;
        this.controls= controls;
        this.graph= panel.getGraph();

        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        controls.setActionListener(this);

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
                moveWorkingEdge(e.getX(),e.getY());
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

        controls.setSelectedMode(this.mode);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EditMode newMode= EditMode.stringToMode(e.getActionCommand());

        if(newMode == null){
            System.err.println("Unknown action fired to GraphController.actionPerformed");
            return;
        }

        this.mode= newMode;
        controls.setSelectedMode(this.mode);
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