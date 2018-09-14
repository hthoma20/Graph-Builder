package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ControlPanel extends JPanel {
    private JButton selectedMode;

    private Map<GraphController.EditMode,JButton> modeButtonMap;

    public ControlPanel(){
        setupModeButtons();
    }

    private void setupModeButtons(){
        //create buttons and add buttons to mode map
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
    }

    private void createModeButtonMap(){
        this.modeButtonMap= new LinkedHashMap<>(4);
        modeButtonMap.put(GraphController.EditMode.VERTEX, new JButton("add (V)ertex"));
        modeButtonMap.put(GraphController.EditMode.EDGE, new JButton("add (E)dge"));
        modeButtonMap.put(GraphController.EditMode.DRAG, new JButton("(D)rag vertex"));
        modeButtonMap.put(GraphController.EditMode.REMOVE, new JButton("(R)emove element"));
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
        if(selectedMode != null){
            selectedMode.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }
    }
}
