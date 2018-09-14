package gui;

import graph.Graph;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GraphBuilderFrame {
    private JFrame frame;
    private Graph graph;

    public static void main(String[] args){
        GraphBuilderFrame frame= new GraphBuilderFrame();
        frame.setVisible(true);
    }

    public GraphBuilderFrame(Graph graph){
        this.graph= graph;

        this.frame= new JFrame("Graph Builder");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GraphPanel gPanel= new GraphPanel(graph);
        ControlPanel cPanel= new ControlPanel();

        GraphController controller= new GraphController(gPanel, cPanel);

        frame.addKeyListener(controller);

        createPane(frame.getContentPane(),gPanel,cPanel);
    }

    private void createPane(Container pane, GraphPanel gPanel, ControlPanel cPanel){
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c= new GridBagConstraints();

        c.fill= GridBagConstraints.BOTH;

        //add graph panel
        c.gridx= 0;
        c.gridy= 0;
        c.gridwidth= 1;
        c.gridheight= 1;
        c.weighty= 1;
        c.weightx= 1;
        pane.add(gPanel,c);

        //add control panel
        c.gridy= 1;
        c.weighty= 0;
        pane.add(cPanel,c);
    }

    public GraphBuilderFrame(){
        this(new Graph());
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }
}
