package gui;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import util.LineSegment;

import javax.swing.*;
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

    private int vRad= 10;

    private EditMode mode;

    public GraphController(Graph graph){
        this.panel= new GraphPanel(this);
        this.controls= new ControlPanel();
        this.graph= graph;

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
        boolean ctrl= (e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK;

        switch(mode){
            case VERTEX:
                String label= "";
                if(ctrl){
                    label= getInput("Vertex label:");
                }
                addVertex(e.getX(),e.getY(), label);
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

        if(command.endsWith("CheckBox")){
            panel.repaint();
            return;
        }

        EditMode newMode= EditMode.stringToMode(command);
        if(newMode == null){
            System.err.println("Unknown action fired to GraphController.actionPerformed");
            return;
        }

        setMode(newMode);
    }

    private String getInput(String prompt){
        return JOptionPane.showInputDialog(panel, prompt);
    }

    private void setMode(EditMode mode){
        this.mode= mode;
        controls.setSelectedMode(mode);
    }

    private void moveWorkingEdge(int x, int y){
        if(selectedVertex == null) return;
        if(workingEdge == null){
            workingEdge= new WorkingEdge(selectedVertex,x,y);
        }
        else{
            workingEdge.moveTo(x,y);
        }

        panel.repaint();
    }

    private void removeWorkingEdge(){
        workingEdge= null;
        panel.repaint();
    }

    private void initRemoveSegment(Point p1){
        this.removeSegment= new LineSegment(p1,p1);
    }

    private void moveRemoveSegment(Point p2){
        if(removeSegment == null) return;

        removeSegment.setP2(p2);
        panel.repaint();
    }

    private void removeRemoveSegment(){
        removeSegment= null;
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

    private void addVertex(int x, int y, String label){
        graph.addVertex(new Vertex(x,y, label));
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
        if(v2 == selectedVertex) return;

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
            if(v.within(x,y,vRad)){
                return v;
            }
        }

        return null;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        panel.repaint();
    }

    /*
    *
    * Getters
    */

    public Graph getGraph(){
        return graph;
    }

    public WorkingEdge getWorkingEdge() {
        return workingEdge;
    }

    public LineSegment getRemoveSegment() {
        return removeSegment;
    }

    public int getVRad() {
        return vRad;
    }

    public GraphPanel getGraphPanel() {
        return panel;
    }

    public ControlPanel getControlPanel() {
        return controls;
    }

    /*
    *
    *
    * Panel queries
    *
     */

    public boolean showDegrees(){
        return controls.checkBoxChecked("degreeCheckBox");
    }

    public boolean showLabels(){
        return controls.checkBoxChecked("labelCheckBox");
    }

    public boolean doVertexColoring(){
        return controls.checkBoxChecked("colorCheckBox");
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
