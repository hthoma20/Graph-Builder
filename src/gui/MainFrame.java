package gui;

import graph.Graph;

import javax.swing.*;

public class MainFrame {
    JFrame frame;

    public static void main(String[] args){
        MainFrame frame= new MainFrame();
        frame.showWindow();
    }

    public MainFrame(){
        this.frame= new JFrame("Graph Builder");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphPanel gPanel= new GraphPanel(new Graph());
        GraphController controller= new GraphController(gPanel);

        frame.add(gPanel);
        frame.addKeyListener(controller);
    }

    public void showWindow(){
        frame.setVisible(true);
    }

    public void hideWindow(){
        frame.setVisible(false);
    }
}
