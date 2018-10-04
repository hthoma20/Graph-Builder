package gui;

import graph.Graph;
import graph.GraphFactory;
import util.GraphReader;
import util.GraphWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GraphBuilderFrame implements ActionListener {
    private JFrame frame;
    private Graph graph;

    private GraphPanel graphPanel;
    private ControlPanel controlPanel;

    private GraphController controller;

    public static void main(String[] args){
        GraphBuilderFrame frame= new GraphBuilderFrame();
        frame.setVisible(true);
        //new BuilderWindow();
    }

    public GraphBuilderFrame(Graph graph){
        this.graph= graph;

        this.frame= new JFrame("Graph Builder");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        this.graphPanel= new GraphPanel(graph);
        this.controlPanel= new ControlPanel();

        controller= new GraphController(graphPanel, controlPanel);

        frame.addKeyListener(controller);

        createPane(frame.getContentPane(), createButtons());
    }

    public GraphBuilderFrame(){
        this(new Graph());
    }

    private void createPane(Container pane, Container buttonContainer){
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
        pane.add(graphPanel,c);

        //add control panel
        c.gridy= 1;
        c.weighty= 0;
        pane.add(controlPanel,c);

        //add copy button
        c.gridy= 2;
        c.weightx= 1;
        pane.add(buttonContainer,c);
    }

    private Container createButtons(){
        JPanel panel= new JPanel(new GridBagLayout());

        //and add the buttons to the panel
        GridBagConstraints c= new GridBagConstraints();
        c.insets= new Insets(0,3,0,3);
        c.gridx= 0;

        JButton duplicateButton= new JButton("Duplicate");
        duplicateButton.setActionCommand("duplicateButton");
        duplicateButton.addActionListener(this);
        duplicateButton.setFocusable(false);

        panel.add(duplicateButton,c);

        JButton newWindowButton= new JButton("New");
        newWindowButton.setActionCommand("newWindowButton");
        newWindowButton.addActionListener(this);
        newWindowButton.setFocusable(false);

        c.gridx= 1;
        panel.add(newWindowButton);

        JButton clearButton= new JButton("Clear");
        clearButton.setActionCommand("clearButton");
        clearButton.addActionListener(this);
        clearButton.setFocusable(false);

        c.gridx= 2;
        panel.add(clearButton,c);

        JButton saveButton= new JButton("Save");
        saveButton.setActionCommand("saveButton");
        saveButton.addActionListener(this);
        saveButton.setFocusable(false);

        c.gridx= 3;
        panel.add(saveButton,c);

        JButton loadButton= new JButton("Load");
        loadButton.setActionCommand("loadButton");
        loadButton.addActionListener(this);
        loadButton.setFocusable(false);

        c.gridx= 4;
        panel.add(loadButton,c);

        JButton chromaticButton= new JButton("Chromatic Number");
        chromaticButton.setActionCommand("chromaticButton");
        chromaticButton.addActionListener(this);
        chromaticButton.setFocusable(false);

        c.gridx= 5;
        panel.add(chromaticButton,c);

        return panel;
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command= e.getActionCommand();

        GraphBuilderFrame newFrame= null;
        if(command.equals("duplicateButton")){
            newFrame= new GraphBuilderFrame(graph.copy());
        }
        else if(command.equals("newWindowButton")){
            newFrame= new GraphBuilderFrame();
        }
        else if(command.equals("clearButton")){
            this.graph= new Graph();
            controller.setGraph(graph);
        }
        else if(command.equals("saveButton")){
            saveGraph();
        }
        else if(command.equals("loadButton")){
            loadGraph();
        }
        else if(command.equals("chromaticButton")){
            System.out.println(graph.chromaticNumber());
        }
        else{
            System.err.println("Unknown action fired");
            return;
        }

        if(newFrame != null){
            Point location= frame.getLocation();
            int screenWidth= Toolkit.getDefaultToolkit().getScreenSize().width;
            location.x+= frame.getWidth();
            if(location.x + 100 > screenWidth){
                location.x= screenWidth- 400;
            }
            newFrame.frame.setLocation(location);
            newFrame.setVisible(true);
        }
    }

    private void saveGraph(){
        File file= chooseFile();

        if(file == null) {
            return;
        }
        GraphWriter writer= new GraphWriter(file);
        System.out.println(writer.writeGraph(graph));
        System.out.println("Written");
    }

    private void loadGraph(){
        File file= chooseFile();
        if(file == null){
            return;
        }

        GraphReader reader= new GraphReader(file);
        Graph newGraph= reader.readGraph();

        if(newGraph == null){
            return;
        }

        this.graph= newGraph;
        controller.setGraph(graph);
    }

    private File chooseFile(){
        JFileChooser fileChooser= new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Graph Files", "gph");
        fileChooser.setFileFilter(filter);

        fileChooser.showOpenDialog(null);
        File file= fileChooser.getSelectedFile();

        if(file == null){
            return null;
        }
        if(!file.getPath().endsWith(".gph")){
            file= new File(file.getPath()+".gph");
        }

        return file;
    }
}
