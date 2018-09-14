package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControlPanel extends JPanel {
    private JButton selectedMode;

    private JButton vertexModeButton;
    private JButton edgeModeButton;
    private JButton dragModeButton;

    private HashMap<GraphController.EditMode,JButton> modeButtonMap;

    public ControlPanel(){
        setupModeButtons();
        createModeButtonMap();
    }

    private void setupModeButtons(){
        //create buttons
        this.vertexModeButton= new JButton("add (V)ertex");
        this.edgeModeButton= new JButton("add (E)dge");
        this.dragModeButton= new JButton("(D)rag vertex");

        //add buttons to mode map
        createModeButtonMap();

        //set action commands
        //disallow focus for buttons
        //and add the buttons to the panel
        for(GraphController.EditMode mode : modeButtonMap.keySet()){
            JButton modeButton= modeButtonMap.get(mode);
            modeButton.setActionCommand(mode.toString());
            modeButton.setFocusable(false);
            this.add(modeButton);
        }

        //select vertex mode by default
        selectedMode= vertexModeButton;
        borderSelectedMode();
    }

    private void createModeButtonMap(){
        this.modeButtonMap= new HashMap<>(3);
        modeButtonMap.put(GraphController.EditMode.DRAG,dragModeButton);
        modeButtonMap.put(GraphController.EditMode.VERTEX,vertexModeButton);
        modeButtonMap.put(GraphController.EditMode.EDGE,edgeModeButton);
    }

    public void setActionListener(ActionListener listener){
        for(JButton b : modeButtonMap.values()){
            b.addActionListener(listener);
        }
    }

    public void setSelectedMode(GraphController.EditMode mode){
        selectedMode= modeButtonMap.get(mode);

        borderSelectedMode();
    }

    private void borderSelectedMode(){
        for(JButton b : modeButtonMap.values()){
            if(b != selectedMode){
                b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        }
        selectedMode.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }
}
